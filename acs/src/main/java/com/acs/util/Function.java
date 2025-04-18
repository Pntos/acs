package com.acs.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import com.acs.view.BaseRestView;

/**
 * <pre>
 * 프로젝트에서 사용할 여러가지 단일 함수 정의.
 * 타입: static
 * </pre>
 * @author 
 */
public class Function {
	/**
	 * <pre>
	 * 현재 날짜 얻어오기.
	 * 형식: YYYYMMDD
	 * </pre>
	 * @return 8자리 날짜표기 문자열
	 */
	public static String getCurrentDate() {

		// 캘린더 인스턴스 얻어오기
		java.util.Calendar currentdate = java.util.Calendar.getInstance();

		int currentYear  = currentdate.get(java.util.Calendar.YEAR);         // 년
		int currentMonth = currentdate.get(java.util.Calendar.MONTH) + 1;    // 월
		int currentDay   = currentdate.get(java.util.Calendar.DAY_OF_MONTH); // 일

		String getYear   = Integer.toString(currentYear).substring(0,4);
		String getMonth  = currentMonth < 10 ? "0" + Integer.toString(currentMonth) : Integer.toString(currentMonth);
		String getDay    = currentDay   < 10 ? "0" + Integer.toString(currentDay)   : Integer.toString(currentDay);

		return getYear+getMonth+getDay;

	}
	/**
	 * <pre>
	 * 현재 날짜 얻어오기.
	 * 형식: YYYY-MM-DD
	 * </pre>
	 * @return 10자리 날짜표기 문자열
	 */
	public static String getCurrentDate2() {

		// 캘린더 인스턴스 얻어오기
		java.util.Calendar currentdate = java.util.Calendar.getInstance();

		int currentYear  = currentdate.get(java.util.Calendar.YEAR);         // 년
		int currentMonth = currentdate.get(java.util.Calendar.MONTH) + 1;    // 월
		int currentDay   = currentdate.get(java.util.Calendar.DAY_OF_MONTH); // 일

		String getYear   = Integer.toString(currentYear).substring(0,4);
		String getMonth  = currentMonth < 10 ? "0" + Integer.toString(currentMonth) : Integer.toString(currentMonth);
		String getDay    = currentDay   < 10 ? "0" + Integer.toString(currentDay)   : Integer.toString(currentDay);

		return getYear+"-"+getMonth+"-"+getDay;

	}
	/**
	 * <pre>
	 * 현재 날짜 얻어오기.
	 * 형식: YYMMDD
	 * </pre>
	 * @return 8자리 날짜표기 문자열
	 */
	public static String getCurrentDate3() {

		// 캘린더 인스턴스 얻어오기
		java.util.Calendar currentdate = java.util.Calendar.getInstance();

		int currentYear  = currentdate.get(java.util.Calendar.YEAR);         // 년
		int currentMonth = currentdate.get(java.util.Calendar.MONTH) + 1;    // 월
		int currentDay   = currentdate.get(java.util.Calendar.DAY_OF_MONTH); // 일

		String getYear   = Integer.toString(currentYear).substring(2);
		String getMonth  = currentMonth < 10 ? "0" + Integer.toString(currentMonth) : Integer.toString(currentMonth);
		String getDay    = currentDay   < 10 ? "0" + Integer.toString(currentDay)   : Integer.toString(currentDay);

		return getYear+getMonth+getDay;

	}
	
	/**
	 * <pre>
	 * 내일 날짜 얻어오기.
	 * 형식: YYMMDD
	 * </pre>
	 * @return 8자리 날짜표기 문자열
	 */
	public static String getNextDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	    Calendar c = Calendar.getInstance();
		Date d;
		
		String nextDate = "";
		try {
			d = sdf.parse(date);
			c.setTime(d);
			c.add(Calendar.DATE,1);
			nextDate = sdf.format(c.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
  
	    
		return nextDate;
	

	}
	
	/**
	 * <pre>
	 * 현재 년도
	 * 형식: YYYY
	 * </pre>
	 * @return 4자리 년도
	 */
	public static String getYear() {

		// 캘린더 인스턴스 얻어오기
		java.util.Calendar currentdate = java.util.Calendar.getInstance();
		int currentYear  = currentdate.get(java.util.Calendar.YEAR);         // 년
		String getYear   = Integer.toString(currentYear).substring(0,4);
		return getYear;

	}
	/**
	 * <pre>
	 * 현재 월
	 * 형식: MM
	 * </pre>
	 * @return 2자리 월
	 */
	public static String getMonth() {

		// 캘린더 인스턴스 얻어오기
		java.util.Calendar currentdate = java.util.Calendar.getInstance();
		int currentMonth = currentdate.get(java.util.Calendar.MONTH) + 1;    // 월
		String getMonth  = currentMonth < 10 ? "0" + Integer.toString(currentMonth) : Integer.toString(currentMonth);
		return getMonth;

	}
	/**
	 * <pre>
	 * 현재 날짜
	 * 형식: DD
	 * </pre>
	 * @return 2자리 날짜
	 */
	public static String getDay() {

		// 캘린더 인스턴스 얻어오기
		java.util.Calendar currentdate = java.util.Calendar.getInstance();
		int currentDay   = currentdate.get(java.util.Calendar.DAY_OF_MONTH); // 일
		String getDay    = currentDay   < 10 ? "0" + Integer.toString(currentDay)   : Integer.toString(currentDay);
		return getDay;

	}

	/**
	 * <pre>
	 * 현재 시간 얻어오기.
	 * 형식: HHMMSS
	 * </pre>
	 * @return 6자리 시간표기 문자열
	 */
	public static String getCurrentTime() {

		// 캘린더 인스턴스 얻어오기
		java.util.Calendar currentdate = java.util.Calendar.getInstance();

		int currentHour  = currentdate.get(java.util.Calendar.HOUR_OF_DAY);  // 시간
		int currentMinute= currentdate.get(java.util.Calendar.MINUTE);       // 분
		int currentSecond= currentdate.get(java.util.Calendar.SECOND);       // 초

		String getHour   = currentHour  < 10 ? "0" + Integer.toString(currentHour)  : Integer.toString(currentHour);
		String getMinute = currentMinute< 10 ? "0" + Integer.toString(currentMinute): Integer.toString(currentMinute);
		String getSecond = currentSecond< 10 ? "0" + Integer.toString(currentSecond): Integer.toString(currentSecond);

		return getHour+getMinute+getSecond;

	}
	
	/**
	 * <pre>
	 * 시리얼 코드 얻어오기.
	 * 서버 파일 업로드 및 삭제 이동 처리시 파일 중복등을 막기 위해
	 * 유일코드를 생성해서 파일명 또는 폴더명에 적용하기 위한 함수.
	 * 형식: YYYYMMDD_HHMMSS_
	 * 길이: 16 byte
	 * </pre>
	 * @return 년도부터 초까지의 시간 문자열
	 */
	public static String getDateTime() {

		// 캘린더 인스턴스 얻어오기
		java.util.Calendar currentdate = java.util.Calendar.getInstance();

		int currentYear  = currentdate.get(java.util.Calendar.YEAR);         // 년
		int currentMonth = currentdate.get(java.util.Calendar.MONTH) + 1;    // 월
		int currentDay   = currentdate.get(java.util.Calendar.DAY_OF_MONTH); // 일
		int currentHour  = currentdate.get(java.util.Calendar.HOUR_OF_DAY);  // 시간
		int currentMinute= currentdate.get(java.util.Calendar.MINUTE);       // 분
		int currentSecond= currentdate.get(java.util.Calendar.SECOND);       // 초
		int currentMili  = currentdate.get(java.util.Calendar.MILLISECOND);  // 밀리초

		String getYear   = Integer.toString(currentYear).substring(0,4);
		String getMonth  = currentMonth < 10 ? "0" + Integer.toString(currentMonth) : Integer.toString(currentMonth);
		String getDay    = currentDay   < 10 ? "0" + Integer.toString(currentDay)   : Integer.toString(currentDay);
		String getHour   = currentHour  < 10 ? "0" + Integer.toString(currentHour)  : Integer.toString(currentHour);
		String getMinute = currentMinute< 10 ? "0" + Integer.toString(currentMinute): Integer.toString(currentMinute);
		String getSecond = currentSecond< 10 ? "0" + Integer.toString(currentSecond): Integer.toString(currentSecond);
		String getMili   = "000";
		try {
			getMili = TextUtil.lpad(currentMili,3);
		} catch (Exception e) { }

		return getYear+getMonth+getDay+getHour+getMinute+getSecond+getMili;

	}
	
	public static String getDateTime9(String strDate) {

		SimpleDateFormat Format  = new SimpleDateFormat("yy-MM-dd HH:mm:ss");  
		SimpleDateFormat returnFormat  = new SimpleDateFormat("HH:mm:ss"); 
		
		Date d1;
		String timedata = "";
		try {
			d1 = Format.parse(strDate);
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(d1);
	        /* cal.add(Calendar.MINUTE, -20); */
	        cal.add(Calendar.HOUR, -9);
	        timedata = returnFormat.format(cal.getTime()).toString();
		} catch (ParseException e) {
			timedata = "";
			e.printStackTrace();
		}

		return timedata.replaceAll(":", "");

	}

	/**
	 * <pre>
	 * 파라미터로 넘어온 가변필드 문자열을 구분하여 벡터에 저장.
	 * 파라미터 예제: "스트링1,스트링2,스트링3"
	 * </pre>
	 * @param String string : 문자열
	 * @return 벡터
	 */
	public static java.util.Vector<String> convertCommaStringToVector(String string) {
	
		java.util.Vector<String> vecResult = new java.util.Vector<String>(0,0);

		while (string.indexOf(',') > -1) {
			vecResult.add(string.substring(0, string.indexOf(',')));
			string = string.substring(string.indexOf(',')+1);
		}
		vecResult.add(string);

		return vecResult;
	}

	/**
	 * <pre>
	 * 파일사이즈를 문자열 형태로 표시.
	 * 숫자로 된 파일 사이즈를 받아서 디스플레이 포맷에 맞춘 문자열 리턴.
	 * 사용방법: SizeToStr("1024") -> return "1KB"
	 * </pre>
	 * @param String file_size : 파일사이즈
	 * @return 문자표기 파일 사이즈
	 */
	public static String SizeToStr(String file_size) {

		return SizeToStr( Long.parseLong(file_size) );
	}

	/**
	 * <pre>
	 * 파일사이즈를 문자열 형태로 표시.
	 * 숫자로 된 파일 사이즈를 받아서 디스플레이 포맷에 맞춘 문자열 리턴.
	 * 사용방법: SizeToStr(1024) -> return "1KB"
	 * </pre>
	 * @param long file_size : 파일사이즈
	 * @return 문자표기 파일 사이즈
	 */
	public static String SizeToStr(long file_size) {
		int allot = 0;
		int mod   = 0;
		String result = "0";

		if (file_size > 1024*1024*1024) {
			allot = (int) ( file_size / (1024*1024*1024) );
			if (allot >= 100) {
				result = Integer.toString(allot) + "GB";
			} else if (allot >= 10) {
				mod = (int) Math.floor( (file_size % (1024*1024*1024) * 100 / 10 / (1024*1024*1024) ) );
				result = Integer.toString(allot) + "." + Integer.toString(mod) + "GB";
			} else {
				mod = (int) Math.floor( (file_size % (1024*1024*1024) * 100 / (1024*1024*1024)));
				if (mod > 9) {
					result = Integer.toString(allot) + "." + Integer.toString(mod) + "GB";
				} else {
					result = Integer.toString(allot) + ".0" + Integer.toString(mod) + "GB";
				}
			}

		} else if (file_size > 1024*1024) {
			allot = (int) ( file_size / (1024*1024) );
			if (allot >= 100) {
				result = Integer.toString(allot) + "MB";
			} else if (allot >= 10) {
				mod = (int) Math.floor( (file_size % (1024*1024) * 100 / 10 / (1024*1024)) );
				result = Integer.toString(allot) + "." + Integer.toString(mod) + "MB";
			} else {
				mod = (int) Math.floor( (file_size % (1024*1024) * 100 / (1024*1024)) );
				if (mod > 9) {
					result = Integer.toString(allot) + "." + Integer.toString(mod) + "MB";
				} else {
					result = Integer.toString(allot) + ".0" + Integer.toString(mod) + "MB";
				}
			}

		} else if (file_size > 1024) {
			result = Integer.toString( (int)(file_size / 1024) ) + "KB";

		} else {
			result = Long.toString(file_size);
		}


		return result;
	}

	public static String nvl(Object obj ) {
		if (obj==null) {
			return "";
		} else {
			return obj.toString();
		}
	}
	public static String nvl(Object obj , String str) {
		if (obj==null || "".equals(obj.toString())){
			return str;
		} else {
			return obj.toString();
		}
	}
	public static String num(Object obj ) {
		if (obj=="") {
			return "0";
		}else if (obj==null) {
			return "0";
		}else {
			return obj.toString();
		}
	}
	
	public static String app_nvl(Object obj ) {
		if (obj=="") {
			return "1";
		}else if (obj==null) {
			return "1";
		}else {
			return obj.toString();
		}
	}
	
	public static String mApp_nvl(Object obj ) {
		if (obj=="") {
			return "10";
		}else if (obj==null) {
			return "10";
		}else {
			return obj.toString();
		}
	}
	
	public static String app_nvl2(Object obj ) {
		if (obj=="0") {
			return "";
		}else {
			return obj.toString();
		}
	}
	
	public static String pop_nvl(Object obj ) {
		if (obj=="") {
			return "01";
		}else if (obj==null) {
			return "01";
		}else {
			return obj.toString();
		}
	}
	
	
	
	/**
	 * <pre>
	 * 날짜 포맷 변환.
	 * 예제: format(20090101)  => 2009-01-01
	 * </pre>
	 * @return 포맷에 맞춰진 문자열
	 */
	
	public static String formatDateTime(String format_str) throws Exception {
		
		if (format_str==null||format_str.trim().equals("")) return "";

		SimpleDateFormat dateFormatter = new SimpleDateFormat ("yyyyMMddHHmmss");
		Date sourceDate = dateFormatter.parse(format_str);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String result = df.format(sourceDate);
		return result;
	}

	/**
	 * <pre>
	 * 날짜 포맷 변환.
	 * 예제: format(20090101)  => 2009-01-01
	 * </pre>
	 * @return 포맷에 맞춰진 문자열
	 */
	
	public static String formatDate(String format_str) throws Exception {
		
		if (format_str==null||format_str.equals("")) return "";

		String str = format_str+"000000";
		SimpleDateFormat dateFormatter = new SimpleDateFormat ("yyyyMMddHHmmss");
		Date sourceDate = dateFormatter.parse(str);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String result = df.format(sourceDate);
		return result;
	}
	
	/**
	 * <pre>
	 * 날짜 포맷 변환.
	 * 예제: format(2014-04-09)  => 20140409
	 * </pre>
	 * @return 포맷에 맞춰진 문자열
	 */
	public static String formatDate2(String format_str) throws Exception {		
		if (format_str==null||format_str.equals("")) return "";

		String str = format_str.replaceAll("-", "");
		return str;
	}

	/**
	 * <pre>
	 * 시간 포맷 변환.
	 * 예제: format(120000)  => 12:00:00
	 * </pre>
	 * @return 포맷에 맞춰진 문자열
	 */
	
	public static String formatTime(String format_str) throws Exception {
		
		if (format_str==null||format_str.equals("")) return "";

		String str = "19700202"+format_str;
		SimpleDateFormat dateFormatter = new SimpleDateFormat ("yyyyMMddHHmmss");
		Date sourceDate = dateFormatter.parse(str);
		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		String result = df.format(sourceDate);
		return result;
	}

	/**
	 * <pre>
	 * 요일 얻어오기
	 * 일요일,월요일,화요일..
	 * </pre>
	 * @return 요일 String[]배열
	 */
	
	public static String [] day() throws Exception {
		
		DateFormatSymbols symbols_Kor= new DateFormatSymbols(Locale.KOREA);
		String [] days=	symbols_Kor.getWeekdays();
		return days;
	}
	
	
	/**
	 * <pre>
	 * 날짜 형식 변경
	 * 형식: YYYYMMDD -> YYYY.MM.DD
	 * 형식: YYYYMMDD <- YYYY.MM.DD
	 * </pre>
	 * @return 8자리 날짜표기 문자열
	 */
	public static String getReplaceDate(String date) {

		if(date.length()==8){
			date = date.substring(0,4)+"."+date.substring(4,6)+"."+date.substring(6,8);
		}else if(date.length()==10){
			date = date.substring(0,4)+date.substring(5,7)+date.substring(8,10);
			//date = date.replaceAll(".","");
			System.out.println(date);
		}
		return date;

	}
	
	/**
	 * <pre>
	 * 입력된 월에서 ?월 빼기 계산
	 * 형식: 200908 -> 200905
	 * </pre>
	 * @return ?월 뺀 날짜
	 */
	public static String getMonthMinus(String date, String MMinus) {
		String sYear = date.substring(0,4);
		String sYonth = date.substring(4,6);
		
		int iYear = Integer.parseInt(sYear);
		int iMonth = Integer.parseInt(sYonth);
		int minus = 1;
		
		for(int i=0; i<Integer.parseInt(MMinus); i++){
			iMonth = iMonth - minus;
			System.out.println(iMonth);
			if(iMonth==0){
				iMonth = 12;
				iYear -=1;
			}
		}
		
		String reYDate = Integer.toString(iYear);
		String reMDate = Integer.toString(iMonth);
		if(reMDate.length()==1)reMDate = "0"+reMDate;
		
		return reYDate + reMDate;

	}
	
	/**
	 * <pre>
	 * 소숫점 첫째 자리에서 반올림
	 * </pre>
	 * @return 빈올림
	 */
	public static String round1(String arge)
	{
	   int    v_index = 0 ;
	   String v_value = "";
	   int    v_int   = 0 ;
       int    v_temp  = 0 ;

	   v_index = arge.indexOf('.',v_temp);
       v_temp  = Integer.parseInt(arge.substring(0,v_index));
       v_value = arge.substring(v_index+1);
	   v_int  = Integer.parseInt(v_value.substring(0,1));
      

	   if(v_int >= 5){
          v_temp = v_temp + 1;
       }

		return Integer.toString(v_temp);
	}
	
	/**
	 * <pre>
	 * 문자열 콤마 표시
	 * </pre>
	 * @return 123,456
	 */
	public static String insertComma( String str )
	{
		int j = 0;
		int m = str.length();
		int r = 0;
		
		if(str.indexOf(".") != -1){
				r = str.indexOf(".");
		}else{
				r = m;
		}
		
		StringBuffer bstr = new StringBuffer( "" );

		for( int i = 1; i <= m; i = i + 1 )
		{
			bstr.append( str.charAt( m - i ) );

			if( j == 2 && m != i )
			{
				if( str.charAt( m - i - 1 ) != '-' )
					bstr.append( "," );
				j = 0;
			}
			else
			{
				if( m - i < r){
					j = j + 1;
				}
			}
		
		}

		bstr.reverse( );
		return bstr.toString( );
	}
	
    /**
     * <pre>
     *  Description	: 기간(월수) 구하기
     *  return days between two date strings with default defined format.(&quot;yyyyMMdd&quot;)
     *  @param String from date string
     *  @param String to date string
     *  @return int 날짜 형식이 맞고, 존재하는 날짜일 때 2개 일자 사이의 월수 리턴
     *            형식이 잘못 되었거나 존재하지 않는 날짜: java.text.ParseException 발생
     * </pre>
     */
    public static int monthsBetween(String from, String to)
            throws java.text.ParseException {
        return monthsBetween(from, to, "yyyyMMdd");
    }

    /**
     * <pre>
     *  Description	: 기간(월수) 구하기
     *  return days between two date strings with default defined format.(&quot;yyyyMMdd&quot;)
     *  @param String from date string
     *  @param String to date string
     *  @param String format format string
     *  @return int 날짜 형식이 맞고, 존재하는 날짜일 때 2개 일자 사이의 월수 리턴
     *            형식이 잘못 되었거나 존재하지 않는 날짜: java.text.ParseException 발생
     * </pre>
     */
    public static int monthsBetween(String from, String to, String format)
            throws java.text.ParseException {

        // 형식 지정
        //java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format, java.util.Locale.KOREA);

        java.util.Date fromDate = check(from, format);
        java.util.Date toDate = check(to, format);

        // if two date are same, return 0.
        if (fromDate.compareTo(toDate) == 0)
            return 0;

        java.text.SimpleDateFormat yearFormat = new java.text.SimpleDateFormat("yyyy", java.util.Locale.KOREA);
        java.text.SimpleDateFormat monthFormat = new java.text.SimpleDateFormat("MM", java.util.Locale.KOREA);
        java.text.SimpleDateFormat dayFormat = new java.text.SimpleDateFormat("dd", java.util.Locale.KOREA);

        int fromYear = Integer.parseInt(yearFormat.format(fromDate));
        int toYear = Integer.parseInt(yearFormat.format(toDate));
        int fromMonth = Integer.parseInt(monthFormat.format(fromDate));
        int toMonth = Integer.parseInt(monthFormat.format(toDate));
        int fromDay = Integer.parseInt(dayFormat.format(fromDate));
        int toDay = Integer.parseInt(dayFormat.format(toDate));

        int result = 0;
        // 년
        result += ((toYear - fromYear) * 12);
        // 월
        result += (toMonth - fromMonth);
        // 일
        if (((toDay - fromDay) > 0))
            result += toDate.compareTo(fromDate);

        return result;
    }
    
    /**
     * <pre>
     *  Description	: 일자 검사
     *  check date string validation with an user defined format.
     *  @param s date string you want to check.
     *  @param format string representation of the date format. For example, &quot;yyyy-MM-dd&quot;.
     *  @return date java.util.Date
     * </pre>
     */
    public static java.util.Date check(String s, String format)
            throws java.text.ParseException {
        // 파라메터 검사
        if (s == null) {
            throw new java.text.ParseException("date string to check is null", 0);
        }
        if (format == null) {
            throw new java.text.ParseException("format string to check date is null", 0);
        }

        // 날짜 형식 지정
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format, java.util.Locale.KOREA);

        // 검사
        java.util.Date date = null;

        try {
            date = formatter.parse(s);
        } catch (java.text.ParseException e) {
            throw new java.text.ParseException(" wrong date:\"" + s + "\" with format \"" + format + "\"", 0);
        }

        // 날짜 포멧이 틀린 경우
        if (!formatter.format(date).equals(s)) {
            throw new java.text.ParseException("Out of bound date:\"" + s + "\" with format \"" + format + "\"", 0);
        }

        // 리턴
        return date;
    }
    /**
	  * HTML이 포함된 문자열을 브라우저 화면에 적용할 수 있도록 변환
	  * @param content
	  * @return
	  */
	 public static String htmlConvert( String content ) {
	  content = content.replaceAll( "&amp;", "&");
	  content = content.replaceAll( "&quot;", "\\");
	  content = content.replaceAll( "&lt;", "<");
	  content = content.replaceAll( "&gt;", ">");
	  
	  return content;
	 }
	
	 /**
	 * 해당 문자열을 다른 문자열로 대체한다.
	 * @param strString 문자열
	 * @param strOld 대상 문자열
	 * @param strNew 대체할 새로운 문자열
	 * @return
	 */
	 public static String replace(String sStrString, String sStrOld, String sStrNew) {
	 if (sStrString == null)return null;
	 for (int iIndex = 0 ; (iIndex = sStrString.indexOf(sStrOld, iIndex)) >= 0 ; iIndex += sStrNew.length())
	 sStrString = sStrString.substring(0, iIndex) + sStrNew + sStrString.substring(iIndex + sStrOld.length());

	 return sStrString;
	 }
	 
	 public static void createThumbnail(File cvtFile,String save,String type,int w,int h) throws Exception {
			BufferedInputStream bis = null;
			try{
				bis = new BufferedInputStream(new FileInputStream(cvtFile));
				File file = new File(save);
				BufferedImage bi = ImageIO.read(bis);

				int width = bi.getWidth();
				int height = bi.getHeight();
				if (w < width) width = w;
				if (h < height) height = h;

				BufferedImage bufferIm = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				Image atemp = bi.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
				Graphics2D g2 = bufferIm.createGraphics();
				g2.drawImage(atemp, 0, 0, width, height, null);
				ImageIO.write(bufferIm, type, file);
			}catch(Exception e) {
				throw e;
			}finally{
				if(bis!=null) try{bis.close();}catch(Exception e){}
			}
	 }
	 
	//Code Object -> option html
		public static String getOptionHtml(HttpServletRequest request, String codeName, String selectedCode) throws Exception {
			String codeKey = "CD";
			String nameKey = "CD_MN";
			return getOptionHtml(request, codeName, selectedCode, codeKey, nameKey);
		}
		
		//Code Object -> option html
		public static String getOptionHtml(HttpServletRequest request, String codeName, String selectedCode, String codeKey, String nameKey) throws Exception {
			List<?> list = (List<?>)request.getAttribute(codeName);
			Iterator<?> it = list.iterator();
			StringBuffer sb = new StringBuffer();
			
			while(it.hasNext()){
				Map<?, ?> map = (Map<?, ?>)it.next();
				String code = (String)map.get(codeKey);
				String name = (String)map.get(nameKey);
				sb.append("<option value='"+code+"'");
				if(selectedCode!=null && code.equals(selectedCode)){
					sb.append(" selected");
				}
				sb.append(">");
				sb.append(name+"</option>");
			}
			return sb.toString();
		}
		

		/**
		 * String 빈값체크.
		 * @author : 
		 * @date : 
		 * @param str
		 * @return 값이 있으면 true, 없으면 false
		 * @throws Exception
		 */
		public static boolean empty(String str) throws Exception {
			boolean result = true;
			if (str == null || str.equals("")) {
				result = false;
			} else {
				str = str.replaceAll(" ", "");
				if (str.length()==0) {
					result = false;
				}
			}
			return result;
		}
		
		/**
		 * 	두 날짜간의 날짜수를 반환(윤년을 감안함)
		 * @author : 
		 * @date : 
		 * @param 시작 날짜
		 * @param 끝 날짜
		 * @return 날수
		 *   - 사용예
		 *   long date = Function.getDiffDays("20141201", "20150211")
		 * @throws Exception 
		 * @throws Exception
		 */
		public static long getDiffDays(String begin, String end) throws Exception{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			 
			Date beginDate = formatter.parse(begin);
		    Date endDate   = formatter.parse(end);
		 
		    long diff = endDate.getTime() - beginDate.getTime();
		    long diffDays = diff / (24 * 60 * 60 * 1000);
		 
		    return diffDays;
			
		}
	 
		//에러처리
		public static ModelAndView errorView(HttpServletRequest request, Exception e) throws Exception {
			e.printStackTrace();
			String requestURI = request.getRequestURI();
			String ext = requestURI.substring(requestURI.lastIndexOf(".")+1);
			if("xml".equals(ext) || "json".equals(ext)){
				BaseRestView brv = new BaseRestView();
				brv.setResultMessage("9998", "오류가 발생하였습니다.\n"+e.toString());
				return brv;
			}else{
				ModelAndView mav = new ModelAndView("error.tiles");
				mav.addObject("EXCEPTION", e);
				return mav;
			}
		}
	 
	
}
