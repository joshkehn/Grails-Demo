package listscrubber;

<<<<<<< HEAD
 class ScrubItemController {

=======
class ScrubItemController {
    
>>>>>>> b3bac845194abe6f02f1af0b7ddda9dcbbf33ccb
    def index = {
        def errors = [];
        def successes = [];
        
        if(params.fileType && !request.getFile("dirtyFile").empty && params.fileName)
        {   
            def valid = true;
<<<<<<< HEAD
            def dirtyFile = request.getFile("dirtylist");
            def contents = dirtyFile.inputStream.text;
=======
            def dirtyFile = request.getFile("dirtyFile");
            def contents;
>>>>>>> b3bac845194abe6f02f1af0b7ddda9dcbbf33ccb
            def fileType = params.fileType;
            def fileName = params.fileName;
            
            if(!dirtyFile.empty)
            {
                contents = dirtyFile.inputStream.text;
                
                /**
                * Processing code goes here
                */
                
                FileHandler.saveReadyFile(contents, fileName);
            }
                        
/*            println "Contents: " + contents*/
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
}

class FileHandler
{
    String fileType;
    String fileName;
    String dirtyFile;
    
    static constraints = {
        fileType(blank: false)
        fileName(blank: false)
        dirtyFile(blank: false)
    }
	
	static getReadyFilesUrls()
    {
        def urlList = [];
        def f = new File('web-app/ready');
        if(f.exists())
        {
            f.eachFile() { file -> 
                if(!file.isDirectory())
                {
                    def s = new ScrubbedFile();
                    s.label = file.name;
                    s.timestamp = "00:00";
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
        urlList
    }
    
    static saveReadyFile(String contents, String name)
    {
        println "Saving to " + name
        new File('web-app/ready/' + name).write(contents);        
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
	
	List<String> scrub(List<String> processedFile, String fileType) {
		List<SupressedEmail> scrubList = SupressedEmail.list();
		List<String> scrubEmail = new ArrayList<String>();
		List<String> scrubMd5 = new ArrayList<String>();
		List<String> result = null;
		
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