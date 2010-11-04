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
