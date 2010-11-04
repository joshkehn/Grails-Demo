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

class ScrubbedFile
{
    def timestamp;
    def label;
}