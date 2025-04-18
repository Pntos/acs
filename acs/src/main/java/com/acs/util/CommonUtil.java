package com.acs.util;

import javax.servlet.http.HttpServletRequest;

public class CommonUtil {
	/**
	 * IP를 조금 더 정확하게 가져옵니다.
	 * @param request
	 * @return
	 */
	public static String getClientIpAddr( HttpServletRequest request ) {
		if ( request == null ) {
			return "";
		}
		String ip = request.getHeader( "X-Forwarded-For" );
		if ( ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase( ip ) ) {
			ip = request.getHeader( "Proxy-Client-IP" );
		}
		if ( ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase( ip ) ) {
			ip = request.getHeader( "WL-Proxy-Client-IP" );
		}
		if ( ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase( ip ) ) {
			ip = request.getHeader( "HTTP_CLIENT_IP" );
		}
		if ( ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase( ip ) ) {
			ip = request.getHeader( "HTTP_X_FORWARDED_FOR" );
		}
		if ( ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase( ip ) ) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	private boolean checkIpBand( String ip, String userIp ) throws Exception {
		boolean result = true;
		String[] ipArr = ip.split( "\\." );
		String[] userIpArr = userIp.split( "\\." );
		
		for( int i = 0; i < ipArr.length; i++ ) {
			if( ipArr[i].indexOf( "/" ) >= 0 ) {
				String[] tempArr = ipArr[i].split( "/" );
				boolean tResult = false;
				Integer startNum = Integer.valueOf( tempArr[0] );
				Integer endNum = Integer.valueOf( tempArr[1] );
				
				for( int j = startNum; j <= endNum; j++ ) {
					if( String.valueOf( j ).equals( userIpArr[i] ) ) {
						tResult = true;
					}
				}
				if( !tResult ) {
					result = false;
				}
			} else {
				if( !ipArr[i].equals( userIpArr[i] ) ) {
					result = false;
					break;
				}
			}
		}
		return result;
	}

}
