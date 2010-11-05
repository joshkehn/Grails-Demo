package listscrubber;

import org.apache.commons.collections.CollectionUtils;

class ScrubItemController {
    
    def index = {
        def errors = [];
        def successes = [];
        
        if(params.fileType && !request.getFile("dirtyFile").empty && params.fileName)
        {   
            def valid = true;
            def dirtyFile = request.getFile("dirtyFile");
            def contents;
            def fileType = params.fileType;
            def fileName = params.fileName;
            
            if(!dirtyFile.empty)
            {
                contents = dirtyFile.inputStream.text;				                
                List<String> processedFile = FileHandler.processFile(contents, fileType);				
				List<String> scrubbedFile = Scrubber.scrub(processedFile, fileType);				
				def result = scrubbedFile.join("\n")
                
                FileHandler.saveReadyFile(result, fileName);
            }
            println "File Type: " + fileType
            println "File Name: " + fileName
            successes.add('File upload successful.');              
        }
        else if(params.fileType || params.dirtyFile || params.fileName)
        {
            errors.add('One or more options were not filled out. Every field is required.');
        }
        
        [urlList: FileHandler.getReadyFilesUrls(), errors: errors, successes: successes]
    }
    
    def download = {
        def filename = params.id;
        def file = new File("web-app/ready/" + filename);
        response.setHeader("Content-disposition", "attachment; filename=${file.name}");
        response.contentType = "text/plain";
        response.outputStream << file.text;
        response.outputStream.flush();
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