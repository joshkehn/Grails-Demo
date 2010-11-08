package listscrubber

class UploadedFile {

    int fileId
	String fileName
	String fileType
    Date timestamp
	String status

    static constraints = {
    }
	
	static mapping = {
		table 'files'
		id column: 'filed_id'
		
		columns {
			fileId      column: 'filed_id'
			fileName    column: 'file_name'
			fileType    column: 'fileType'
			timestamp   column: 'entry_time'
			status      column: 'status'
		}
	}
}
