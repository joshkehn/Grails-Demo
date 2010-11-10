package listscrubber;

import org.apache.commons.collections.CollectionUtils;

class ScrubItemController {
    
    def index = {
        def errors = [];
        def successes = [];
        
        println 'Checking for form submission.';
		
		println 'params: ' + params
        
        if(params.fileType && !request.getFile("dirtyFile").empty && !request.getFile("scrubFile").empty && params.fileName)
        {   
            println 'Form submission found.';
            def dirtyFile = request.getFile("dirtyFile");
            def scrubFile = request.getFile("scrubFile");
            
            def fileType = params.fileType;
            def fileName = params.fileName;
            
            def u = new UploadedFile(fileName: fileName, fileType: fileType, timestamp: new Date(), status: 'queued');
            u.save();
            
            FileHandler.saveStagedFile(dirtyFile.inputStream.text, fileName + ".dirty");
            FileHandler.saveStagedFile(scrubFile.inputStream.text, fileName + ".scrub");
            
            successes.add('File upload successful');
        }
        else if(params.fileType || params.dirtyFile || params.fileName)
        {
            println 'Invalid form submission found.';
            errors.add('One or more options were not filled out. Every field is required.');
        }
        else
        {
            println 'No form submission.';
        }
        
        [urlList: UploadedFile.listOrderByTimestamp(order:"desc"), errors: errors, successes: successes]
    }
    
    def fileList = {
        [urlList: UploadedFile.listOrderByTimestamp(order:"desc")]
    }
    
    def download = {
        def filename = params.id;
        def file = new File("web-app/ready/" + filename);
        response.setHeader("Content-disposition", "attachment; filename=${file.name}");
        response.contentType = "text/plain";
        response.outputStream << file.text;
        response.outputStream.flush();
    }
    
    def suppression = {
        if(params.suppressionFile && !request.getFile("suppressionFile").empty)
        {
            /**
            * Form submitted
            */
            def contents = new ArrayList(Arrays.asList(request.getFile("suppressionFile").inputStream.text.split())).unique();
            contents.each {
                try{
                    new SupressedEmail(email: it, md5: it.encodeAsMD5()).save();
                }catch(Exception e){}
            }
            
            [successes: ["File upload complete."]]
        }
        else
        {
            println "No form submission found.";
        }
    }
	
	def suppressionClear = {
        SupressedEmail.list()*.delete();
        redirect(action:suppression);
	}
	
	def suppressionView = {
	    [suppressionList: SupressedEmail.list()]
	}
}

class FileHandler
{
    String fileType;
    String fileName;
    String dirtyFile;
    static def fileSeparator = File.separatorChar;
    static constraints = {
        fileType(blank: false)
        fileName(blank: false)
        dirtyFile(blank: false)
    }

    static processFile(String contents, String fileType)
    {
        def clean = contents;
        if(fileType == "csv")
        {
            /**
            * Process
            */
            clean = clean.replace(',', '');
        }
        
        new ArrayList(Arrays.asList(clean.split())).unique();
    }
	
    static getReadyFilesUrls()
    {
        def urlList = [];       
        def f = new File('web-app' + fileSeparator + 'ready');
        if(f.exists())
        {
            f.eachFile() { file -> 
                if(!file.isDirectory())
                {
                    def s = new ScrubbedFile();
                    s.label = file.name;
                    s.timestamp = file.lastModified();
                    urlList.add(s);
                }
                else
                {
                    println file.name + " is directory";
                }
            }
        }
        else
        {
            println "File doesn't exist";
        }
        
        urlList.sort{ -it.timestamp }
    }
    
    static saveReadyFile(String contents, String name)
    {
        println "Saving to " + name
        new File('web-app' + fileSeparator + 'ready' + fileSeparator + '' + name).write(contents);
    }

    static saveStagedFile(String contents, String name)
    {
        println "Saving to " + name
        new File('web-app' + fileSeparator + 'staging' + fileSeparator + '' + name).write(contents);
    }
}

class Scrubber {
	/*
	scrub(FileContents, fileType)
	a)	Get scrub list: Array<ScrubItem> scrubList = get scrub list from DB
	b)	If fileType = md5
	c)		subtract scrubList from FileContents based on md5 field of scrubList
	d)	Else
	e)		subtract scrubList from FileContents based on email field of scrubList
	f)	Return the resulting Contents(Array<String>)
	
	Note: the scrub method assumes that the scrub list is small enough to keep in memory
	Once our list exceeds that point, we can modify the code to 
	incrementally retrieve pieces of the collection and subtract 
	them from the "dirty" list until ("dirty" list is empty OR no more result sets are there to be retrieved)
	
	questions:
	a. Does Groovy/Grails provide a mechanism for easy subtraction of sets? If not, use beanutils
	*/
	
	static scrub(ArrayList processedFile, String fileType) {
		def scrubList = SupressedEmail.list();
		def scrubEmail = new ArrayList<String>();
		def scrubMd5 = new ArrayList<String>();
		def result = null;
		
		for(SupressedEmail s : scrubList) {
			scrubEmail.add(s.getEmail());
			scrubMd5.add(s.getMd5());
		}
		
		if("md5".equals(fileType)) {
			result = CollectionUtils.subtract(processedFile,scrubMd5);
		} else {
			result = CollectionUtils.subtract(processedFile,scrubEmail);
		}
			
		return result;
	}
}

class ScrubbedFile
{
    def timestamp;
    def label;
}