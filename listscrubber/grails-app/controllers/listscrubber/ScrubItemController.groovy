package listscrubber;

class ScrubItemController {

    def index = {
        def errors = [];
        def successes = [];
        if(params.fileType && !request.getFile("dirtyFile").empty && params.fileName)
        {   
            def valid = true;
            def dirtyFile = request.getFile("dirtyFile");
            def contents;
            
            if(!dirtyFile.empty)
            {
                contents = dirtyFile.inputStream.text;
            }
            
            def fileType = params.fileType;
            def fileName = params.fileName;
            
            println "Contents: " + contents
            println "File Type: " + fileType
            println "File Name: " + fileName
            successes.add('File upload successful.');              
        }
        else if(params.fileType || !request.getFile("dirtyFile").empty || params.fileName)
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
        ScrubbedFile f = new ScrubbedFile();
        f.timestamp = "now";
        f.label = "Hello";
        urlList.add(f);
        f.timestamp = "yesterday";
        f.label = "World";
        urlList.add(f);
        urlList;
    }
    
}

class ScrubbedFile
{
    String timestamp;
    String label;
}