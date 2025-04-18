package com.acs.util;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class FileManager {

	// path : 파일을 저장할 경로
	// 리턴 : 서버에 저장된 새로운 파일명
	public static String doFileUpload(File file, String originalFileName, String path) throws Exception {
		String newFileName = null;

		if(file == null)
			return null;

		// 클라이언트가 업로드한 파일의 이름
		if(originalFileName.equals(""))
			return null;

		// 확장자
		String fileExt = originalFileName.substring(originalFileName.lastIndexOf("."));
		if(fileExt == null || fileExt.equals(""))
			return null;

		// 서버에 저장할 새로운 파일명을 만든다.
		newFileName = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS",
				         Calendar.getInstance());
		newFileName += System.nanoTime();
		newFileName += fileExt;

		// 업로드할 경로가 존재하지 않는 경우 폴더를 생성 한다.
		File dir = new File(path);
		if(!dir.exists())
			dir.mkdirs();

		String fullFileName = path + File.separator + newFileName;

		FileInputStream fis = new FileInputStream(file);
		FileOutputStream fos = new FileOutputStream(fullFileName);
		int bytesRead = 0;
        byte[] buffer = new byte[1024];
        while ((bytesRead = fis.read(buffer, 0, 1024)) != -1) {
            fos.write(buffer, 0, bytesRead);
        }
        fos.close();
        fis.close();

		return newFileName;
	}

	// 파일 다운로드
	// saveFileName : 서버에 저장된 파일명
	// originalFileName : 클라이언트가 업로드한 파일명
	// path : 서버에 저장된 경로
	public static boolean doFileDownload(String saveFileName, String originalFileName, String path, HttpServletResponse response) {
		boolean result = false;

		String load_dir = null;
		if(path==null) load_dir = saveFileName;
		//else load_dir = path + File.separator + saveFileName;
		else load_dir = path + saveFileName;
		
		load_dir = FileUtil.cleanString(load_dir);
				
        try {
    		if(originalFileName == null || originalFileName.equals("")) originalFileName = saveFileName;
        	originalFileName = new String(originalFileName.getBytes("euc-kr"),"8859_1");
        } catch (UnsupportedEncodingException e) {
        }

        BufferedInputStream bis = null;
        OutputStream os    = null;
	    try {
	        File file = new File(load_dir);

	        if (file.exists()){
	            byte readByte[] = new byte[4096];

	            response.setContentType("application/octet-stream");
				response.setHeader(
						"Content-disposition",
						"attachment;filename=" + "\"" + originalFileName);
				response.setHeader("Keep-Alive", "On");
				response.setHeader("Content-Length" , ""+file.length() );

				bis  = new BufferedInputStream(new FileInputStream(file));
	            //javax.servlet.ServletOutputStream outs =	response.getOutputStream();
				os = response.getOutputStream();

	   			int read;
	    		while ((read = bis.read(readByte, 0, 4096)) != -1)
	    			os.write(readByte, 0, read);
	    		os.flush();

	    		result = true;
	        }
		} catch (IOException e) {
			if(!e.getClass().getName().equals("org.apache.catalina.connector.ClientAbortException"))
				e.printStackTrace();
	    } catch(Exception e) {
	    } finally {
    		if (os!= null) try { os.close(); } catch (Exception e) {}
    		if (bis!= null) try { bis.close(); } catch (Exception e) {}
	    }

	    return result;
	}
	public static boolean doFileDownload(String saveFileName, String path, HttpServletResponse response) {
		return doFileDownload(saveFileName, saveFileName, path, response);
	}

	public static boolean doFileDownloads(String saveFileName,String orgFileName, String path, HttpServletResponse response) {
		return doFileDownload(saveFileName, orgFileName, path, response);
	}


	// 실제 파일 삭제
	public static void doFileDelete(String fileName, String path)
	        throws Exception {
		File file = null;
		String fullFileName = path + File.separator + fileName;
        file = new java.io.File(fullFileName);
        if (file.exists())
           file.delete();
	}

	public static void fileCopy(File input, File output) {
		try {
			BufferedInputStream in = new BufferedInputStream(	new FileInputStream(input));
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(output));

			int temp;

			while ((temp = in.read()) != -1) {
				out.write(temp);
			}
			in.close();
			out.flush();
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
		
	private static  BufferedImage rotate90CW(BufferedImage bi){
        int width = bi.getWidth();
        int height = bi.getHeight();
        
        BufferedImage biFlip = new BufferedImage(height, width, bi.getType());
        
        for(int i=0; i<width; i++)
            for(int j=0; j<height; j++)
                biFlip.setRGB(height-1-j, i, bi.getRGB(i, j));
        
        return biFlip;
    }

	private static  BufferedImage rotate90CCW(BufferedImage bi){
        int width = bi.getWidth();
        int height = bi.getHeight();
        
        BufferedImage biFlip = new BufferedImage(height, width, bi.getType());
        
        for(int i=0; i<width; i++)
            for(int j=0; j<height; j++)
                biFlip.setRGB(j, width-1-i, bi.getRGB(i, j));
        
        return biFlip;
    }
	
	private static  BufferedImage rotate180(BufferedImage bi){
        int width = bi.getWidth();
        int height = bi.getHeight();
        
        BufferedImage biFlip = new BufferedImage(width, height, bi.getType());
        
        for(int i=0; i<width; i++)
            for(int j=0; j<height; j++)
                biFlip.setRGB(width-1-i, height-1-j, bi.getRGB(i, j));
        
        return biFlip;
    }
	
	// 이미지 썸네일 생성
	public static long saveThumbImage(File file, String saveFileName, String path, int width, int height) throws Exception{
		long imageSize = 0;
		try{
			File tempDir = new File(path);
			if(!tempDir.exists()) tempDir.mkdirs();
			
			BufferedImage originalImage = ImageIO.read(file);
			
			int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_RGB : originalImage.getType();
	
			BufferedImage resizedImage = new BufferedImage(width, height, type);
			Graphics2D g = resizedImage.createGraphics();
			g.setComposite(AlphaComposite.Src);
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		    g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		    
			g.drawImage(originalImage, 0, 0, width, height, null);			
			g.dispose();
						
			File saveFile = new File(path+saveFileName);			
			ImageIO.write(resizedImage, "jpg", saveFile);
						
			imageSize = saveFile.length();
		}catch(Exception e){
			e.printStackTrace();
		}finally{

		}
		return imageSize;
	}
	
	public static String getBrowser(HttpServletRequest request) { 
		String header = request.getHeader("User-Agent"); 
		if (header.indexOf("MSIE") > -1) {
            return "MSIE";
        } else if (header.indexOf("Trident") > -1) {   // IE11 문자열 깨짐 방지
            return "Trident";
        } else if (header.indexOf("Chrome") > -1) {
            return "Chrome";
        } else if (header.indexOf("Opera") > -1) {
            return "Opera";
        } else if (header.indexOf("Safari") > -1) {
            return "Safari";
        }
		return "Firefox"; 
	}
	
	public static String getDisposition(String filename, String browser) throws Exception {  
		String encodedFilename = null;
		if (browser.equals("MSIE")) {
            encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
	    } else if (browser.equals("Trident")) {       // IE11 문자열 깨짐 방지
            encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
        } else if (browser.equals("Firefox")) {
	        encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
	        encodedFilename = URLDecoder.decode(encodedFilename);
	    } else if (browser.equals("Opera")) {
	        encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
	    } else if (browser.equals("Chrome")) {
	    	StringBuffer sb = new StringBuffer();
            for (int i = 0; i < filename.length(); i++) {
                   char c = filename.charAt(i);
                   if (c > '~') {
                         sb.append(URLEncoder.encode("" + c, "UTF-8"));
                   } else {
                         sb.append(c);
                   }
            }
            encodedFilename = sb.toString();
	    } else if (browser.equals("Safari")){
            encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1")+ "\"";
            encodedFilename = URLDecoder.decode(encodedFilename);
        } else {
        	encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1")+ "\"";
        }
		return encodedFilename;	
	}


	
	
	
	
	
	
}
