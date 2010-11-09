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

import java.sql.*;
import java.util.*;
import java.io.*;

public class ExternalScrubber {
	
	public static Connection conn = getNewConnection();
	
	public static void main(String[] args) throws Exception {	
		
		System.out.println("started process");
		
		while(true) {
			UploadedFile uf = getUploadedFile();
			
			
			if(uf != null) {
				System.out.println("picked up file: uf.fileName=" + uf.fileName + " ");
				List<String> dirtyStuff = getDirtyFile(uf.fileName);
				List<String> cleanStuff = processFile(dirtyStuff,uf.fileType);
				List<String> suppList = getSuppList(uf.fileType);
				List<String> scrubbedStuff = new ArrayList<String>();
				
				int onePct = cleanStuff.size()/100;
				int currPct = 0;
				
				setStatus(currPct + "%", uf.fileId);
				
				/*
				
				get an iterator for each array
			    compare values of the iterators
			    if equal, increment both
			    if not equal, put lesser value into array of unique values and increment only that iterator
			    repeat until end of an array is met
			    if any left in other array, those are unique
				
				
				*/
				
				System.out.println("onePct = " + onePct);
				int i=0;
				for(String s : cleanStuff) {
					boolean dirty = false;
					//System.out.println("i%onePct = " + (i%onePct));
					if(i>0 && i%onePct == 0) {
						currPct++;
						setStatus(currPct + "%", uf.fileId);
					}
					
					for(String semail : suppList) {
						if(semail.equals(s)) {
							dirty = true;
							break;
						}
					}
					
					if(!dirty) {
						scrubbedStuff.add(s);
					}
					
					i++;
					//Thread.sleep(2000);
				}
				
				Collection<String> uniqueScrubbed = new HashSet<String>(scrubbedStuff);
				
				saveNewFile(uniqueScrubbed,uf.fileName);
				setStatus("done", uf.fileId);
			} else {
				System.out.println("NO FILE FOUND");
			}
			
			Thread.sleep(10000);
		}
	}
	
	private static Connection getNewConnection() {
	
		Connection con1 = null;
		try {
			Class.forName("com.mysql.jdbc.Driver"); 
			con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/scrubbing","root","root");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return con1;
	}
	
	private static UploadedFile getUploadedFile() {
		System.out.println("getUploadedFile() started");
		String sql = "select * from files where status = 'queued' order by entry_time desc limit 1 ";
		UploadedFile uf = null;
		
		try {
			PreparedStatement prepStat = conn.prepareStatement(sql);
			ResultSet rs = prepStat.executeQuery();
			
			
			if(rs.next()) {
				uf = new UploadedFile();
				uf.fileId =  rs.getInt("id");
				uf.fileName = rs.getString("file_name");
				uf.fileType = rs.getString("fileType");
				uf.timestamp = rs.getDate("entry_time");;
				uf.status = rs.getString("status"); 
			}
		} catch(SQLException se) {
			se.printStackTrace();
		}
		
		System.out.println("getUploadedFile(): uf: ");
		return uf;		
	}
	
	private static List<String> getDirtyFile(String fileName) throws IOException {
		String fileStr = "C:/Users/rvilensky/Desktop/KobeMail Security/Grails-Demo/listscrubber/web-app/staging/" + fileName;
		File file = new File(fileStr);
		List<String> dirtyList = new ArrayList<String>();
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = null;
			
			while((line = reader.readLine()) != null) {
				dirtyList.add(line);
			}
			
		} catch(Exception e) {
			e.printStackTrace();		
		} finally {
			if(reader != null) {
				reader.close();
			}
		}
		
		System.out.println("dirtyList: " + dirtyList.size());
		
		return dirtyList;
	}
	
	private static List<String> processFile(List<String> dirtyStuff, String fileType) {
		List<String> cleanerStuff = new ArrayList<String>();
		if("csv".equals(fileType)) {
			for(String s : dirtyStuff) {
				String s2 = s.substring(0,s.length() - 1);
				//System.out.println("processing: " + s);
				cleanerStuff.add(s2);
			}
		} else {
			cleanerStuff = dirtyStuff;
		}
		
		System.out.println("cleanerStuff: " + cleanerStuff.size());
		
		return cleanerStuff;
	}
	
	private static List<String> getSuppList(String fileType) {
	
		String sql = "SELECT ";
		sql += ("md5".equals(fileType))?"md5 ":"email ";
		sql += " AS emailVal FROM supressed_email ";
		List<String> suppList = new ArrayList<String>();
		
		try {
			PreparedStatement prepStat = conn.prepareStatement(sql);
			ResultSet rs = prepStat.executeQuery();
			
			while(rs.next()) {
				suppList.add(rs.getString("emailVal"));
			}
		} catch(SQLException se) {
			se.printStackTrace();
		}
		
		return suppList;	
	}
	
	private static void setStatus(String newStatus, int fileId) {
		String sql = "UPDATE files SET status = ? WHERE id = ? ";
		
		try {
			PreparedStatement prepStat = conn.prepareStatement(sql);
			prepStat.setString(1,newStatus);
			prepStat.setInt(2, fileId);
			
			prepStat.executeUpdate();
		} catch(SQLException se) {
			se.printStackTrace();
		}
	}
	
	private static void saveNewFile(Collection<String> uniqueScrubbed, String fileName) throws IOException {
		String fileStr = "C:/Users/rvilensky/Desktop/KobeMail Security/Grails-Demo/listscrubber/web-app/ready/" + fileName;
		BufferedWriter writer = null;
		
		try {
			File file = new File(fileStr);
			
			if(!file.exists()) {
				file.createNewFile();
			}
		
			writer = new BufferedWriter(new FileWriter(fileStr));
		
			for(String s : uniqueScrubbed) {		
				writer.write(s);
				writer.newLine();
			}
		} finally {
			if(writer != null) {
				writer.close();
			}
		}
	}
}
