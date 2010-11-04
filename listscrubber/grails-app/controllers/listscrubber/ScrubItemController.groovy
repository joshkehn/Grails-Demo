package listscrubber

import com.listscrubber.*;

class ScrubItemController {

    def index = {
        redirect(action: "scrublist", params: [initview: true])
    }
    
    def scrublist = {
		
		
		//def fileProcessor = new FileProcessor();
		//def scrubber = new Scrubber();
		
		def dirtyFile
		def contents;
		if(!params.initview) {
			dirtyFile = request.getFile("dirtylist");
			contents = dirtyFile.inputStream.text
		}
				
		String fileType = params.filetype;
		String newFileName = params.newFileName;
		
		//String[] processedResults = fileProcessor(dirtyFile,fileType);
		//String[] scrubbedResults = scrubber.scrub(processedResults, fileType);
		//fileProcessor.saveReadyFile(scrubbedResults, newFileName);
		//
		//ArrayList<HashMap> urls = fileProcessor.getReadyFilesList();
		//
		//[readyList: urls]
		params: [initview: true];

			println 'contents: ' + contents;
			println 'file type: ' + fileType;
			println 'new file name: ' + newFileName;
		
       [messages:['File upload successful:', 'contents: ' + contents, 'file type: ' + fileType, 'new file name: ' + newFileName ], initview: false];
    }
}
