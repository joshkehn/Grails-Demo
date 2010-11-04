package listscrubber;

 class ScrubItemController {

     def index = {

		if(params.filetype && params.dirtylist && params.newFileName) {
		
			
			def valid = true;
			
			def dirtyFile = request.getFile("dirtyfile");
			
			def contents = dirtyFile.inputStream.text;
			
			def fileType = params.filetype;
			
			def fileName = params.newFileName;
			
			
			
			println "Contents: " + contents
			
			println "File Type: " + fileType
			
			println "File Name: " + fileName
		
			
			[success:['File upload successful.']]                
			
		}
			
		else if(params.filetype || params.dirtylist || params.newFileName) {
			
			[errors:['One or more options were not filled out. Every field is required.']];
			
		}
			
		else {
			
			/* Nothing */
			
		}
			
     }
 }