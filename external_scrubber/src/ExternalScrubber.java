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
			if(uf != null) {
				List<String> dirtyStuff = getDirtyFile(uf.fileName);
				List<String> cleanStuff = processFile(dirtyStuff,uf.fileType);
				List<String> suppList = getSuppList(conn,uf.fileType);
				
				int onePct = cleanStuff.size()/100;
				int currPct = 0;
				
				setStatus(""+currPct, uf.fileId);
				
				/*
				
				get an iterator for each array
			    compare values of the iterators
			    if equal, increment both
			    if not equal, put lesser value into array of unique values and increment only that iterator
			    repeat until end of an array is met
			    if any left in other array, those are unique
				
				
				*/
				
				for(int i=0; i<cleanStuff.size(); i++) {
					if(i>0 && i%onePct == 0) {
						currPct++;
						setStatus(""+currPct, uf.fileId);
					}
					
					for(String semail : suppList) {
						if(semail.equals(cleanStuff.get(i)) {
							cleanStuff.remove(i);
						}
					}
					
					Thread.sleep(2000);
				}
				
				saveNewFile(cleanStuff,uf.fileName);
				setStatus("done", uf.fileId);
			}
			
			Thread.sleep(10000);
		}
	}
	
	private static Connection getNewConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver"); 
			conn = DriverManager.getConnection("jdbc:mysql://10.1.1.121:3306/scrubbing","root","root");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static UploadedFile getUploadedFile() {
		String sql = "SELECT * FROM files WHERE  timestamp = (SELECT MIN(timestamp) FROM files) AND status = 'new' ";
		PreparedStatement prepStat = conn.prepareStatement(sql);
		ResultSet rs = prepStat.executeQuery();
		UploadedFile uf = null;
		
		if(rs.next()) {
			uf = new UploadedFile();
			uf.fileId =  rs.getInt("filed_id");
			uf.fileName = rs.getString("file_name");
			uf.fileType = rs.getString("fileType");
			uf.timestamp = rs.getDate("entry_time");;
			uf.status = rs.getString("status"); 
		}
		
		return uf;		
	}
	
	private static List<String> getDirtyFile(String fileName) {
		String fileStr = "C:/Users/rvilensky/Desktop/KobeMail Security/Grails-Demo/listscrubber/web-app/staging/" + fileName;
		File file = new File(fileStr);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		
		List<String> dirtyList = new ArrayList<String>();
		
		try {
			String line = null;
			
			while((line = reader.readLine()) != null) {
				dirtyList.add(line);
			}
			
		} finally {
			reader.close();
		}
		
		return dirtyList;
	}
	
	private static List<String> processFile(List<String> dirtyStuff, String fileType) {
		if("csv".equals(fileType)) {
			for(String s : dirtyStuff) {
				s.replaceAll(",","");
			}
		}
	}
	
	private static List<String> getSuppList(String fileType) {
	
		String sql = "SELECT ";
		sql += ("md5".equals(fileType))?"md5 ":"email ";
		sql += " AS emailVal FROM SupressedEmail ";
		
		PreparedStatement prepStat = conn.prepareStatement(sql);
		ResultSet rs = prepStat.executeQuery();
		
		List<String> suppList = new ArrayList<String>();
		
		while(rs.next()) {
			suppList.add(rs.getString("emailVal"));
		}
		
		return suppList;	
	}
	
	private static void setStatus(String newStatus, int fileId) {
		String sql = "UPDATE files SET status = ? WHERE filed_id = ? ";
		PreparedStatement prepStat = conn.prepareStatement(sql);
		prepStat.setString(1,newStatus);
		prepStat.setInt(2, fileId);
		
		prepStat.executeUpdate();
		
		conn.commit();
	}
	
	private static void saveNewFile(List<String> cleanStuff, String fileName) {
		String fileStr = "C:/Users/rvilensky/Desktop/KobeMail Security/Grails-Demo/listscrubber/web-app/ready/" + fileName;
		
		File file = new File(fileStr);
		
		if(!file.exists()) {
			file.createNewFile();
		}
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileStr));
		try {
			for(String s : cleanStuff) {		
				writer.write(s);
				writer.newLine();
			}
		} finally {
			writer.close();
		}
	}
}
