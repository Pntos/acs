package com.acs.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import org.springframework.web.multipart.MultipartFile;

public class FileUtil {
	/**
	 * <pre>
	 * 파일명 분리
	 * 형식: D:\data.file -> data.file
	 * </pre>
	 * @return 파일명
	 */
	public static String getFileName(String data) {
		if(data.length()!=0){
			int sepPos1 = data.lastIndexOf('\\');
			int sepPos2 = data.lastIndexOf('/');
			data = data.substring((sepPos1>sepPos2?sepPos1:sepPos2)+1,data.length());
		}
		return data;
	}
	
	/**
	 * <pre>
	 * 파일경로 분리
	 * 형식: D:\data.file -> D:\
	 * </pre>
	 * @return 파일경로
	 */
	public static String getFilePath(String data) {
		if(data.length()!=0){
			int sepPos1 = data.lastIndexOf('\\');
			int sepPos2 = data.lastIndexOf('/');
			data = data.substring(0, (sepPos1>sepPos2?sepPos1:sepPos2)+1);
		}
		return data;
	}
	
	/**
	 * <pre>
	 * 파일확장자 리턴
	 * 형식: test.jpg -> jpg
	 * </pre>
	 * @return 파일확장자
	 */
	public static String getFileExtension(String data) {
		if(data.length()!=0){
			data = data.substring(data.lastIndexOf('.')+1);
		}
		return data;

	}


	/* -------------------------------------------------------------------------
	 * purpose  : get free space from disk
	 * parameter: folder
	 * result   : 
	 * -----------------------------------------------------------------------*/
	public static long getDiskFreeSpace(String filePath) {

		File f = new File( filePath );

		long bytesFree = -1;
		
		if (System.getProperty("os.name").startsWith("Windows")) {
			try {
				// create a .bat file to run a directory command
				File script = new File(System.getProperty("java.io.tmpdir"), "script.bat");
				PrintWriter writer = new PrintWriter(new FileWriter(script, false));
				writer.println("dir \"" + f.getAbsolutePath() + "\"");
				writer.close();
				// get the output from running the .bat file
				System.out.println("script.getAbsolutePath() : "+script.getAbsolutePath());
				Process p = Runtime.getRuntime().exec(script.getAbsolutePath());
				InputStreamReader reader = new InputStreamReader(p.getInputStream());
				StringBuffer buffer = new StringBuffer();
				for (; ; ) {
					int c = reader.read();
					if (c == -1)
					break;
					buffer.append( (char) c);
				}
				String outputText = buffer.toString();
				reader.close();
				
				// parse the output text for the bytes free info
				StringTokenizer tokenizer = new StringTokenizer(outputText, "\n");
				while (tokenizer.hasMoreTokens()) {
					String line = tokenizer.nextToken().trim();

					// see if line contains the bytes free information
					if (line.endsWith("bytes free")||(line.endsWith("바이트 남음"))) {
						tokenizer = new StringTokenizer(line, " ");
						tokenizer.nextToken();
						tokenizer.nextToken();
						bytesFree = Long.parseLong(tokenizer.nextToken().replaceAll(",",""));
					}
				}
				reader.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}

		// OS가 윈도우즈가 아닐경우 ....
		if (bytesFree == -1) {
			try {
				File file = File.createTempFile("dummy","none", f);
				RandomAccessFile raf = new RandomAccessFile(file, "rw");
				
				long size = 0;
				long step = Long.MAX_VALUE - (Long.MAX_VALUE/2);
	
				while (step > 0) {
					try {
						raf.setLength(size + step);
						size += step;
					}
					catch (IOException ioe) {}
					step /= 2;
				}
	
				raf.close();
				file.delete();
				bytesFree = size;
			} catch (IOException ioe) {
				ioe.printStackTrace();
				
			}
		}

//		logger.debug("sended disk free space info..");

		
		return bytesFree;
	}
	
	/* -------------------------------------------------------------------------
	 * purpose  : Path Manipulation
	 * parameter: path
	 * description   :  URL  경로  변조 방지 
	 * -----------------------------------------------------------------------*/
	public static String cleanString(String aString) {
		if (aString == null){ 
			return null; 
	   }
	  
	   String cleanString = "";
	   for (int i = 0; i < aString.length(); ++i) {
		   cleanString += cleanChar(aString.charAt(i)); 
	  }
	   return cleanString;
	}
	
	public static char cleanChar(char aChar) {
		// 0 - 9 
		for (int i = 48; i < 58; ++i) { 
			if (aChar == i){
				return (char) i;
			} 
		} 
		// 'A' - 'Z' 
		for (int i = 65; i < 91; ++i) {
			if (aChar == i){
				return (char) i; 
			}
	   } 
	   // 'a' - 'z' 
		for (int i = 97; i < 123; ++i) { 
			if (aChar == i){ 
				return (char) i; 
			}
		} 
		// other valid characters 
		return getSpecialLetter(aChar);
	}

	public static char  getSpecialLetter(char aChar){ 
		switch (aChar) { 
			case '/': 
					return '/'; 
			case '.':
					return '.';
			case '-':
					return '-'; 
			case '_':
					return '_'; 
			case ' ':
					return ' '; 
			case ':':
					return ':';
			case '&': 
					return '&'; 
			default: 	
					return '%'; 
		}
	}
	

	/*
	 *   파일첨부 실행
	 *   (파일, 파일경로, 원본파일명, 시설구분, 위치, 팀 ,첨부순서)
	 *   
	 */
	public static Map<String, Object> uploadFileSave(MultipartFile upfile, String comPath, String uploadPath, String origName, String munuID, int seq) {	
		Map<String, Object> fileMap = new HashMap<String, Object>();
		try {
			
			uploadPath = comPath + uploadPath;			
			File uploadDir = new File(uploadPath);
			if(!uploadDir.exists()) uploadDir.mkdirs();
			String fileSize = String.valueOf(upfile.getSize());
			String fileStr ="sv_";
			String fileId = fileStr+String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", Calendar.getInstance());
		       fileId += System.nanoTime() + (Math.round(java.lang.Math.random()*1000000));			
			String ext = origName.substring(origName.lastIndexOf('.')); // 확장자 String saveFileName = getUuid() + ext; 
			String saveFileName = fileId + ext;
			
			File serverFile = new File(uploadPath + File.separator + saveFileName);
			upfile.transferTo(serverFile);
			
			//String gKey = String.valueOf(commonFileDao.getGroupKey()); //그룹키 얻기
			fileMap.put("GRP_KEY", "");
		    fileMap.put("FILE_SEQ", seq);
		    fileMap.put("FILE_NAME", fileId);
		    fileMap.put("FILE_NAME_ORIGIN", origName);
		    fileMap.put("FILE_PATH", uploadPath);
		    fileMap.put("FILE_SIZE", fileSize);
		    fileMap.put("FILE_EXT", ext);
		    fileMap.put("MENU_ID", munuID);
		    fileMap.put("TYPE", "F"); 						//File:Image
		    //commonFileDao.fileUpload(fileMap);
		    
		    fileMap.put("RESULT", "0000"); 		
			
		} catch (Exception e) {
			fileMap.put("RESULT", "9999"); 	
			e.printStackTrace();
		}
				
		return fileMap;
	}
	
	
	
}