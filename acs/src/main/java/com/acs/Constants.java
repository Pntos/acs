package com.acs;

import javax.servlet.http.HttpServletRequest;
import com.acs.Constants;

/**
* <pre>
* 시스템 전역적으로 사용할 컨피값 로딩.
* </pre>
* created on : 2020-07
* created by : (주)Dimotech
* last updated on : 
* last updated by : 
*/
public class Constants {
	public static boolean debugMode       = false;
	
	public static String systemUrl        =  "https://ssv.dimotech.co.kr";
	public static String systemIp         =  "https://10.0.111.86:444";
	public static String testMode         =  "N";
	public static String testMail         =  ""; 
	
	public static String smtpMode         =  "dimotech";
	private static int mailSendTime      = 0;
	private static String smtpAddr        = "10.0.103.32";
	private static String smtpPort        = "25";
	private static String smtpId          = "anonymous";
	private static String smtpPw          = "";
	private static String mailSenderName  = "ssv";
	private static String mailSenderEmail = "admin@dimotech.co.kr";
	private static String userPin      = "";
	
	public static String errorCode        = "0001";
	public static String errorCodeMsg     = "오류가 발생하였습니다.";
	public static String errorSessionCode = "0010";
	public static String errorSessionMsg  = "세션이 만료되었습니다.";
	public static String errorSSOSessionMsg  = "자동로그인 세션이 만료되었습니다.";	
	public static String errorParam        = "0002";
	public static String errorParamMsg     = "파라미터가 잘못되었습니다.";	
	
	public static String getPSFilePath = "/uploadFile/ssv/";	
	
	public final static String RES_CODE  = "resCode";// 필수 처리 결과 파라미터명
	public final static String RES_MSG   = "resMessage"; // 필수 처리 결과  파라미터명
	public final static String OK     	 = "0000"; // Application Result Code
	public final static String ERROR  	 = "0001";// Application Result Code
	public final static String SESSION_ERROR  	 = "9999";// Application Result Code
	
	public final static String COMMON_FILE_PATH = "D:/4k/upload";
	public final static String SUB_FILE_PATH1 = "/1";
	public final static String SUB_FILE_PATH2 = "/2";
	
	// FFMpeg 경로
	public static String FFMPEG_PATH = "D:/4k/ffmpeg/bin/ffmpeg";	
	public static String FFPROBE_PATH = "D:/4k/ffmpeg/bin/ffprobe";	
	public static String ORG_FILE_PATH = "D:/4k/upload";	
	
	public static String getFFMPEG_PATH() {
		return FFMPEG_PATH;
	}

	public static void setFFMPEG_PATH(String fFMPEG_PATH) {
		FFMPEG_PATH = fFMPEG_PATH;
	}

	public static String getFFPROBE_PATH() {
		return FFPROBE_PATH;
	}

	public static void setFFPROBE_PATH(String fFPROBE_PATH) {
		FFPROBE_PATH = fFPROBE_PATH;
	}

	public static String getORG_FILE_PATH() {
		return ORG_FILE_PATH;
	}

	public static void setORG_FILE_PATH(String oRG_FILE_PATH) {
		ORG_FILE_PATH = oRG_FILE_PATH;
	}

	public static void setSmtpAddr(String smtpAddr) {
		Constants.smtpAddr = smtpAddr;
	}

	public static String getSmtpAddr() {
		return smtpAddr;
	}
	
	public static void setSmtpPort(String smtpPort) {
		Constants.smtpPort = smtpPort;
	}

	public static String getSmtpPort() {
		return smtpPort;
	}
	
	public static void setSmtpId(String smtpId) {
		Constants.smtpId = smtpId;
	}

	public static String getSmtpId() {
		return smtpId;
	}

	public static void setSmtpPw(String smtpPw) {
		Constants.smtpPw = smtpPw;
	}
	
	public static String getSmtpPw() {
		return smtpPw;
	}

	public static void setMailSenderName(String mailSenderName) {
		Constants.mailSenderName = mailSenderName;
	}

	public static String getMailSenderName() {
		return mailSenderName;
	}

	public static void setMailSenderEmail(String mailSenderEmail) {
		Constants.mailSenderEmail = mailSenderEmail;
	}
	public static String getMailSenderEmail() {
		return mailSenderEmail;
	}

	public static void setMailSendTime(int mailSendTime) {
		Constants.mailSendTime = mailSendTime;
	}

	public static int getMailSendTime() {
		return mailSendTime;
	}
	
	public static void setUserPin(String userPin) {
		Constants.userPin = userPin;
	}
	
	public static String getUserPin() {
		return userPin;
	}
	
	
	
	
	
	
	
	
	public static String getIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		    ip = request.getHeader("Proxy-Client-IP"); 
		} 
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		    ip = request.getHeader("WL-Proxy-Client-IP"); 
		} 
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		    ip = request.getHeader("HTTP_CLIENT_IP"); 
		} 
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		    ip = request.getHeader("HTTP_X_FORWARDED_FOR"); 
		} 
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		    ip = request.getRemoteAddr(); 
		}
		
		return ip;
	}
	
	
	
	
}
