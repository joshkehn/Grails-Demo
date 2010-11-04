package listscrubber;

 class ScrubItemController {

    def index = {
        
        if(params.fileType && params.dirtyList && params.fileName)
        {
            def valid = true;
            def dirtyFile = request.getFile("dirtylist");
            def contents = dirtyFile.inputStream.text;
            def fileType = params.fileType;
            def fileName = params.fileName;
            
            println "Contents: " + contents
            println "File Type: " + fileType
            println "File Name: " + fileName
            [successes:['File upload successful.']]                
        }
        else if(params.fileType || params.dirtyList || params.fileName)
        {
            [errors:['One or more options were not filled out. Every field is required.']];
        }
    }
    
/*    def scrubfile = {
        FileHandler cmd ->
            if(cmd.hasErrors())
            {
                redirect(action:'index', errors:['All fields are required.']);
            }
            else
            {
/*                [successes:['File uploaded successfully.']]
                redirect(action:'index', successes:['File uploaded successfully.']);
            }

    }*/
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
	
	List<String> scrub(List<String> processedFile, fileType) {
		List<SupressedEmail> scrubList = SupressedEmail.list();
		List<String> scrubEmail = new ArrayList<String>();
		List<String> scrubMd5 = new ArrayList<String>();
		
		for(SupressedEmail s : scrubList) {
			scrubEmail.add(s.getEmail());
			scrubMd5.add(s.getMd5());
		}
	}
}