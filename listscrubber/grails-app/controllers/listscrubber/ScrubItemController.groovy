package listscrubber

import com.listscrubber.*;

class ScrubItemController {

    def index = {
        def dirtyFile;
        if(params.filetype || request.getFile("dirtylist") || params.newFileName)
        {
            dirtyFile = request.getFile("dirtylist")
            
            def contents = dirtyFile.inputStream.text
            def fileType = params.filetype;
            def fileName = params.newFileName;
            
            println "Contents: " + contents
            println "File Type: " + fileType
            println "File Name: " + fileName
            
            
            [success:['File upload successful.']]
        }
    }
}
