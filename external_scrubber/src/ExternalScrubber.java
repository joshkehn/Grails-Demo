/*
	Monitor db for new file
	When new file arrives:
		Get file name
		Pick up the File - 
		Convert the file into a collection
		Retrieve the suppression list
		Convert the supression list into email and md5 collections
		Clean the fileList
		onePercent = fileList/100
		set db to 0.
		foreach femail in fileList
			if iteration mod onePercent == 0 
				up db by one.
			foreach semail in scrubList
				if femail == semail
					remove femail from fileList
		Get newFileName from db
 		Create file with that name in downloads dir
 		Write fileList to that file.
		Update status in db with "done".
*/

package com.kobemail.listscrubber.external;

public class ExternalScrubber {
	
	public static Connection conn = getNewConnection();
	
	public static void main(String[] args) {	
		
		
		while(true) {
			UploadedFile uf = getUploadedFile(conn);
			if(uf.status.equals("new")) {
				List<String> dirtyStuff = getDirtyFile(uf.fileName);
				List<String> cleanStuff = processFile(dirtyStuff,uf.fileType);
				List<String> suppList = getSuppList(conn,uf.fileType);
				
				int onePct = cleanStuff.size()/100;
				int currPct = 0;
				
				setStatus(""+currPct);
				
				for(int i=0; i<cleanStuff.size(); i++) {
					if(i>0 && i%onePct == 0) {
						currPct++;
						setStatus(""+currPct);
					}
					
					for(String semail : suppList) {
						if(semail.equals(cleanStuff.get(i)) {
							cleanStuff.remove(i);
						}
					}
				}
				
				saveNewFile(cleanStuff,uf.newFileName);
				setStatus("done");
			}
		}
	}
	
	private static Connection getNewConnection() {
	}
	
	private static UploadedFile getUploadedFile() {
		
	}
	
	private static List<String> getDirtyFile(String fileName) {
	}
	
	private static List<String> processFile(List<String> dirtyStuff, String fileType) {
	}
	
	private static List<String> getSuppList(String fileType) {
	}
	
	private static void setStatus(String newStatus) {
	}
	
	private static void saveNewFile(List<String> cleanStuff, String newFileName) {
	}
}
