package com.acs.util;

import java.util.StringTokenizer;

import com.acs.util.TextUtil;

public class TextUtil {

	//private static Log log = LogFactory.getLog(TextUtil.class);

	/**
	 * 문자열을 지정된 Token list_seperator로 Tokenize한다.<br>
	 * <code>
	 *  String source = "Text token\tis A Good\nAnd bad.";<br>
	 *  String[] result = TextUtil.split(source, " \t\n");<br>
	 *  </code> <code>result</code>는
	 * <code>"Text","token","is","A","Good","And","bad."</code> 를 가지게 된다.
	 * 
	 * @param str
	 *            원본 문자열
	 * @param pattern
	 *            Token list_seperators
	 * @return as 토큰들의 배열
	 */
	public static String[] split(String str, String pattern) throws Exception {

		if (str == null) {
			return null;
		}

		StringTokenizer strToken = null;
		String[] as = null;

		try {
			strToken = new StringTokenizer(str, pattern);
			int i = strToken.countTokens();
			as = new String[i];
			for (int j = 0; j < i; j++) {
				as[j] = strToken.nextToken();
			}
		} catch (Exception e) {
			// log.error("split()" + e);
			throw e;
		}
		return as;
	}

	/**
	 * 문자열 좌우 공백/탭/개행문자 제거
	 * 
	 * @param String
	 *            str 문자열 값
	 * @return String str 좌우 공백/탭/개행문자 가 제거된 문자열 값
	 * @throws Exception
	 */
	public static String trim(String str) throws Exception {

		if (str == null)
			return "";
		int st = 0;
		int count = 0;
		int len = 0;
		try {
			char[] val = str.toCharArray();
			count = val.length;
			len = count;
			while ((st < len)
					&& ((val[st] <= ' ') || (val[st] == ' ')
							|| (val[st] == '\r') || (val[st] == '\n')))
				st++;
			while ((st < len)
					&& ((val[len - 1] <= ' ') || (val[len - 1] == ' ')))
				len--;
		} catch (Exception e) {

			throw e;
		}
		return ((st > 0) || (len < count)) ? str.substring(st, len) : str;
	}

	/**
	 * 문자열 값이 null 이면 "", 아니면 원 문자열 값을 반환한다.
	 * 
	 * @param String
	 *            str 문자열 값
	 * @return String str null 이면 "", 아니면 원 문자열 값
	 * @throws Exception
	 */
	/*
	public static String isNull(String str) throws Exception {

		return (str==null) ? "" : str;
	}*/

	/**
	 * 문자열 값이 null 이면 val, 아니면 원 문자열 값을 반환한다.
	 * 
	 * @param String
	 *            str 문자열 값
	 * @param String
	 *            val 문자열 값이 null 일경우 반환될 문자열 값
	 * @return String str null 이면 val, 아니면 원 문자열 값
	 * @throws Exception
	 */
	public static String isNull(String str, String val) throws Exception {

		return (str == null || "".equals(str)) ? val : str;
	}
	
	/**
	 */
	public static boolean isNull(String str) {
		if( "null".equals(str) || "NULL".equals(str) || "".equals(str) ) {
			return true;
		}
		
		return (str == null || "".equals(str)) ? true : false;
	}

	/**
	 * num 값에 len 자리수 만큼 '0' 값을 붙힌 문자열 값을 반환한다.
	 * 
	 * @param int
	 *            num 정수 값
	 * @param int
	 *            len 반환될 문자열 수
	 * @return String str len 자리수 만큼 '0' 값을 붙힌 문자열
	 * @throws Exception
	 */
	public static String lpad(int num, int len) throws Exception {

		return lpad(String.valueOf(num), len, '0');
	}

	/**
	 * str 값에 len 자리수 만큼 '0' 값을 붙힌 문자열 값을 반환한다.
	 * 
	 * @param String
	 *            str 원본 문자열
	 * @param int
	 *            len 반환될 문자열 수
	 * @return String str len 자리수 만큼 '0' 값을 붙힌 문자열
	 * @throws Exception
	 */
	public static String lpad(String str, int len) throws Exception {

		return lpad(str, len, '0');
	}

	/**
	 * str 값에 len 자리수 만큼 c 값을 붙힌 문자열 값을 반환한다.
	 * 
	 * @param String
	 *            str 원본 문자열
	 * @param int
	 *            len 반환될 문자열 수
	 * @param char
	 *            c 추가할 문자 값
	 * @return String str len 자리수 만큼 c 값을 붙힌 문자열
	 * @throws Exception
	 */
	public static String lpad(String str, int len, char c) throws Exception {
		if (str == null)
			return "";
		if (len < 0)
			return str;
		StringBuffer buf = null;
		try {
			if (str.length() > len)
				return str.substring(str.length() - len);

			buf = new StringBuffer();
			for (int i = 0; i < len - str.length(); i++) {
				buf.append(c);
			}
			buf.append(str);
		} catch (Exception e) {
			// log.error("lpad()" + e);
			throw e;
		}
		return buf.toString();
	}

	/**
	 * String을 자리수만큼 특정 문자로 채워줌 사용함
	 * 
	 * @param String
	 *            str 검증 대상 파라미터 값
	 * @return String str 유효한 파라미터 값
	 * @throws Exception
	 */
	public static String lpad(String str, int len, String c) throws Exception {
		if (str == null)
			return "";
		if (len < 0)
			return str;
		StringBuffer buf = null;
		try {
			if (str.length() > len)
				return str.substring(str.length() - len);

			buf = new StringBuffer();
			for (int i = 0; i < len - str.length(); i++) {
				buf.append(c);
			}
			buf.append(str);
		} catch (Exception e) {
			// log.error("lpad()" + e);
			throw e;
		}
		return buf.toString();
	}

	/**
	 * String을 자리수만큼 특정 문자로 채워줌 사용함
	 * 
	 * @param String
	 *            str 검증 대상 파라미터 값
	 * @return String str 유효한 파라미터 값
	 * @throws Exception
	 */
	public static String rpad(String str, int len, String c) throws Exception {
		if (str == null)
			return "";
		if (len < 0)
			return str;
		StringBuffer buf = null;
		try {
			if (str.length() > len)
				return str.substring(str.length() - len);

			buf = new StringBuffer();
			buf.append(str);
			for (int i = 0; i < len - str.length(); i++) {
				buf.append(c);
			}

		} catch (Exception e) {
			// log.error("lpad()" + e);
			throw e;
		}
		return buf.toString();
	}

	/**
	 * 한글혼용 문자열을 주어진 길이만큼 자르는 method이다.
	 * 
	 * @param s :
	 *            문자열
	 * @param len :
	 *            제한 길이
	 * @param tail :
	 *            끝에 붙이고자 하는 문자열
	 * @return
	 */
	public static String truncateNicely(String s, int len, String tail) throws Exception {

		if (s == null)
			return "";

		int srcLen = getRealLength(s);

		if (srcLen < len) {
			return s;
		}

		String tmpTail = (tail == null) ? "" : tail;

		int tailLen = getRealLength(tmpTail);

		if (tailLen > len) {
			return "";
		}

		char a;
		int i = 0;
		int realLen = 0;
		for (i = 0; i < len - tailLen && realLen < len - tailLen; i++) {
			a = s.charAt(i);

			realLen += ((a & 0xFF00) == 0) ? 1 : 2;
		}

		while (getRealLength(s.substring(0, i)) > len - tailLen) {
			i--;
		}

		return s.substring(0, i) + tmpTail;
	}

	/**
	 * 문자열에 대한 실바이트수를 반환하는 method이다.
	 * 
	 * @param str :
	 *            문자열
	 * @return
	 */
	public static int getRealLength(String str) throws Exception {

		if (str == null)
			return 0;
		return str.getBytes().length;
	}
	
	
	/**
	 * HTML의 입력상자 (&ltINPUT&gt, &ltTEXTAREA&gt)에서 원래의 String 형태 그대로 보여지도록 바꿔주는 메소드<br>
	 * db query 시 데이타 특수문자 처리
	 * @param strIn - 입력상자에서도 그대로 보여지기를 원하는 String
	 * @return 형태 그대로 입력상자에서 보여지도록 바뀐 최종 String
	 */
	public static String toHtmlInputText(String strIn) throws Exception {
		String str = strIn;

		str = str.replaceAll("&", "&amp;");
		str = str.replaceAll("<", "&lt;");
		str = str.replaceAll(">", "&gt;");
		str = str.replaceAll("'", "&acute;");
		str = str.replaceAll("\"", "&quot;");

		return str;
	}	
	
	
	/**
	 */
	public static String setCrLf(String strIn) throws Exception {
		String str = strIn;
		
		str = str.replaceAll("\r", " ");
		str = str.replaceAll("\n", " ");
		
		return str;
	}	
	
	/**
	 * DB2에서 J Mode 케릭세셑 변경처리 함수<br>
	 * db query 시 데이타 특수문자 처리
	 * @param strIn - 2Byte 문자를 포함한 String
	 * @return 1Byte 문자열로 바뀐 String
	 */
	public static String convertTwoOneByte(String strIn) {
		String str = strIn==null?"":strIn;

		str = str.replaceAll("　", " ");
		str = str.replaceAll("Ａ", "A");
		str = str.replaceAll("Ｂ", "B");
		str = str.replaceAll("Ｃ", "C");
		str = str.replaceAll("Ｄ", "D");
		str = str.replaceAll("Ｅ", "E");
		str = str.replaceAll("Ｆ", "F");
		str = str.replaceAll("Ｇ", "G");
		str = str.replaceAll("Ｈ", "H");
		str = str.replaceAll("Ｉ", "I");
		str = str.replaceAll("Ｊ", "J");
		str = str.replaceAll("Ｋ", "K");
		str = str.replaceAll("Ｌ", "L");
		str = str.replaceAll("Ｍ", "M");
		str = str.replaceAll("Ｎ", "N");
		str = str.replaceAll("Ｏ", "O");
		str = str.replaceAll("Ｐ", "P");
		str = str.replaceAll("Ｑ", "Q");
		str = str.replaceAll("Ｒ", "R");
		str = str.replaceAll("Ｓ", "S");
		str = str.replaceAll("Ｔ", "T");
		str = str.replaceAll("Ｕ", "U");
		str = str.replaceAll("Ｖ", "V");
		str = str.replaceAll("Ｗ", "W");
		str = str.replaceAll("Ｘ", "X");
		str = str.replaceAll("Ｙ", "Y");
		str = str.replaceAll("Ｚ", "Z");
		str = str.replaceAll("１", "1");
		str = str.replaceAll("２", "2");
		str = str.replaceAll("３", "3");
		str = str.replaceAll("４", "4");
		str = str.replaceAll("５", "5");
		str = str.replaceAll("６", "6");
		str = str.replaceAll("７", "7");
		str = str.replaceAll("８", "8");
		str = str.replaceAll("９", "9");
		str = str.replaceAll("０", "0");
		str = str.replaceAll("ａ", "a");
		str = str.replaceAll("ｂ", "b");
		str = str.replaceAll("ｃ", "c");
		str = str.replaceAll("ｄ", "d");
		str = str.replaceAll("ｅ", "e");
		str = str.replaceAll("ｆ", "f");
		str = str.replaceAll("ｇ", "g");
		str = str.replaceAll("ｈ", "h");
		str = str.replaceAll("ｉ", "i");
		str = str.replaceAll("ｊ", "j");
		str = str.replaceAll("ｋ", "k");
		str = str.replaceAll("ｌ", "l");
		str = str.replaceAll("ｍ", "m");
		str = str.replaceAll("ｎ", "n");
		str = str.replaceAll("ｏ", "o");
		str = str.replaceAll("ｐ", "p");
		str = str.replaceAll("ｑ", "q");
		str = str.replaceAll("ｒ", "r");
		str = str.replaceAll("ｓ", "s");
		str = str.replaceAll("ｔ", "t");
		str = str.replaceAll("ｕ", "u");
		str = str.replaceAll("ｖ", "v");
		str = str.replaceAll("ｗ", "w");
		str = str.replaceAll("ｘ", "x");
		str = str.replaceAll("ｙ", "y");
		str = str.replaceAll("ｚ", "z");
		str = str.replaceAll("！", "!");
		str = str.replaceAll("＠", "@");
		str = str.replaceAll("＃", "#");
		str = str.replaceAll("＄", "$");
		str = str.replaceAll("％", "%");
		str = str.replaceAll("＾", "^");
		str = str.replaceAll("＆", "&");
		str = str.replaceAll("＊", "*");
		str = str.replaceAll("（", "(");
		str = str.replaceAll("）", ")");
		str = str.replaceAll("＿", "_");
		str = str.replaceAll("＋", "+");
		str = str.replaceAll("－", "-");
		str = str.replaceAll("＝", "=");
		str = str.replaceAll("［", "[");
		str = str.replaceAll("］", "]");
		str = str.replaceAll("｛", "{");
		str = str.replaceAll("｝", "}");
		str = str.replaceAll("；", ";");
		str = str.replaceAll("＇", "'");
		str = str.replaceAll("：", ":");
		str = str.replaceAll("＂", "\"");
		str = str.replaceAll("，", ",");
		str = str.replaceAll("．", ".");
		str = str.replaceAll("／", "/");
		str = str.replaceAll("＜", "<");
		str = str.replaceAll("＞", ">");
		str = str.replaceAll("？", "?");
		str = str.replaceAll("｀", "`");
		str = str.replaceAll("～", "~");

		return str;
	}

	/**
	 * DB2에서 J Mode 케릭세셑 변경처리 함수<br>
	 * db query 시 데이타 특수문자 처리
	 * @param strIn - 2Byte 문자를 포함한 String
	 * @return 1Byte 문자열로 바뀐 String
	 */
	public static String convertOneTwoByte(String strIn) {
		String str = strIn==null?"":strIn;

		str = str.replaceAll(" ", "　");
		str = str.replaceAll("A", "Ａ");
		str = str.replaceAll("B", "Ｂ");
		str = str.replaceAll("C", "Ｃ");
		str = str.replaceAll("D", "Ｄ");
		str = str.replaceAll("E", "Ｅ");
		str = str.replaceAll("F", "Ｆ");
		str = str.replaceAll("G", "Ｇ");
		str = str.replaceAll("H", "Ｈ");
		str = str.replaceAll("I", "Ｉ");
		str = str.replaceAll("J", "Ｊ");
		str = str.replaceAll("K", "Ｋ");
		str = str.replaceAll("L", "Ｌ");
		str = str.replaceAll("M", "Ｍ");
		str = str.replaceAll("N", "Ｎ");
		str = str.replaceAll("O", "Ｏ");
		str = str.replaceAll("P", "Ｐ");
		str = str.replaceAll("Q", "Ｑ");
		str = str.replaceAll("R", "Ｒ");
		str = str.replaceAll("S", "Ｓ");
		str = str.replaceAll("T", "Ｔ");
		str = str.replaceAll("U", "Ｕ");
		str = str.replaceAll("V", "Ｖ");
		str = str.replaceAll("W", "Ｗ");
		str = str.replaceAll("X", "Ｘ");
		str = str.replaceAll("Y", "Ｙ");
		str = str.replaceAll("Z", "Ｚ");
		str = str.replaceAll("1", "１");
		str = str.replaceAll("2", "２");
		str = str.replaceAll("3", "３");
		str = str.replaceAll("4", "４");
		str = str.replaceAll("5", "５");
		str = str.replaceAll("6", "６");
		str = str.replaceAll("7", "７");
		str = str.replaceAll("8", "８");
		str = str.replaceAll("9", "９");
		str = str.replaceAll("0", "０");
		str = str.replaceAll("a", "ａ");
		str = str.replaceAll("b", "ｂ");
		str = str.replaceAll("c", "ｃ");
		str = str.replaceAll("d", "ｄ");
		str = str.replaceAll("e", "ｅ");
		str = str.replaceAll("f", "ｆ");
		str = str.replaceAll("g", "ｇ");
		str = str.replaceAll("h", "ｈ");
		str = str.replaceAll("i", "ｉ");
		str = str.replaceAll("j", "ｊ");
		str = str.replaceAll("k", "ｋ");
		str = str.replaceAll("l", "ｌ");
		str = str.replaceAll("m", "ｍ");
		str = str.replaceAll("n", "ｎ");
		str = str.replaceAll("o", "ｏ");
		str = str.replaceAll("p", "ｐ");
		str = str.replaceAll("q", "ｑ");
		str = str.replaceAll("r", "ｒ");
		str = str.replaceAll("s", "ｓ");
		str = str.replaceAll("t", "ｔ");
		str = str.replaceAll("u", "ｕ");
		str = str.replaceAll("v", "ｖ");
		str = str.replaceAll("w", "ｗ");
		str = str.replaceAll("x", "ｘ");
		str = str.replaceAll("y", "ｙ");
		str = str.replaceAll("z", "ｚ");
		str = str.replaceAll("!", "！");
		str = str.replaceAll("@", "＠");
		str = str.replaceAll("#", "＃");
		str = str.replaceAll("\\$", "＄");
		str = str.replaceAll("%", "％");
		str = str.replaceAll("\\^", "＾");
		str = str.replaceAll("&", "＆");
		str = str.replaceAll("\\*", "＊");
		str = str.replaceAll("\\(", "（");
		str = str.replaceAll("\\)", "）");
		str = str.replaceAll("_", "＿");
		str = str.replaceAll("\\+", "＋");
		str = str.replaceAll("\\-", "－");
		str = str.replaceAll("=", "＝");
		str = str.replaceAll("\\[", "［");
		str = str.replaceAll("\\]", "］");
		str = str.replaceAll("\\{", "｛");
		str = str.replaceAll("\\}", "｝");
		str = str.replaceAll(";", "；");
		str = str.replaceAll("'", "＇");
		str = str.replaceAll(":", "：");
		str = str.replaceAll("\\\"", "＂");
		str = str.replaceAll(",", "，");
		str = str.replaceAll("\\.", "．");
		str = str.replaceAll("/", "／");
		str = str.replaceAll("<", "＜");
		str = str.replaceAll(">", "＞");
		str = str.replaceAll("\\?", "？");
		str = str.replaceAll("`", "｀");
		str = str.replaceAll("~", "～");
		return str;
	}	

	 public static String insertComma(String str)
	 {
	  if (str == null||"".equals(str))
	   return "";
	  
	  String bk = "";
	  /*--------------------소수점 아랫자리 존재할때------------*/
	  if(str.indexOf('.')>-1){
	  bk = str.substring(str.indexOf('.')); //str의 소숫점자리
	  str = str.substring(0,str.indexOf('.'));
	  }

	  int lm_iLen = str.length();
	  String lm_sRet = "";
	  
	  if (lm_iLen < 0) return "";
	  
	  if (str.charAt(0) == '-')
	  {
	   str = str.substring(1);
	   lm_iLen -= 1;
	  
	   int size = 3;
	   int cnt = lm_iLen / size;
	   int pos = lm_iLen % size;
	   String result = "";
	  
	   for( int i = cnt ; i > 0 ; i-- )
	   {
	    result = "," + str.substring(pos+(i-1)*size, pos+i*size) + result ;
	   }
	  
	   if( pos == 0 )
	    lm_sRet = "-" + result.substring(1);
	   else
	   lm_sRet = "-" + str.substring(0,pos) + result;
	  }
	  else
	  {
	   int size = 3;
	   int cnt = lm_iLen / size;
	   int pos = lm_iLen % size;
	   String result = "";
	  
	   for( int i = cnt ; i > 0 ; i-- )
	   {
	    result = "," + str.substring(pos+(i-1)*size, pos+i*size) + result ;
	   }
	  
	   if( pos == 0 )
	    lm_sRet = result.substring(1);
	   else
	   lm_sRet = str.substring(0,pos) + result;
	  }
	  
	   if(bk.indexOf('.')>-1){
	    lm_sRet+=bk;
	  }
	  
	  return lm_sRet;
	 }

	public static String nvl(Object obj ) {
		if (obj==null) {
			return "";
		} else {
			return obj.toString();
		}
	}

	/**
	 * 문자열 포맷 입히기
	 * @param 
	 * @return String 문자열로 바뀐 String
	 */
	public static String formatString(String format, String strIn) {
		String str = strIn==null?"":strIn.trim();
		String strOut = ""; 
		
		if (str.length()==0) return "";
		
		int strInCnt = 0;
		for (int i=0;i<format.length();i++) {
			if (format.substring(i,i+1).equals("#")) {
				strOut += str.substring(strInCnt,strInCnt+1);
				strInCnt++;
			} else {
				strOut += format.substring(i,i+1);
			}
		}
		
		return strOut;
	}
	
	public static String converTimeToString(long time) {
		
		long hh = time>360?time/360:0; 
		long mm = time>60?(time-hh*60)/60:0;
		long ss = (time - hh*60 - mm*60);
		return (hh>9?""+hh:"0"+hh) + ":" + (mm>9?""+mm:"0"+mm) + ":" + (ss>9?""+ss:"0"+ss);				
	}
	
	public static String formatTelNoString(String hpno) {

		String result = hpno;
		if (hpno.length()==10) {
			result = hpno.substring(0,3)+"-"+hpno.substring(3,6)+"-"+hpno.substring(6,10);
 		} else if (hpno.length()==11) {
 			result = hpno.substring(0,3)+"-"+hpno.substring(3,7)+"-"+hpno.substring(7,11);
 		}
		
		return result;
	}
	
	
	/**
	 * 엑셀 데이터 날짜포맷을 20130413 String 형식으로 리턴
	 * @param yyymmdd
	 * @return
	 */
	public static String excelFormatDateToString( String yyymmdd ) {
		if( yyymmdd!=null || !yyymmdd.equals("") ) {
			try {
				yyymmdd = yyymmdd.replaceAll("\\p{Space}","");
				String k = "";
				if( yyymmdd.lastIndexOf(".") != -1 ) { k = "."; }
				if( yyymmdd.lastIndexOf("-") != -1 ) { k = "-"; }
				if( yyymmdd.lastIndexOf("/") != -1 ) { k = "/"; }
				
				String [] sp = yyymmdd.split("["+k+"]");
				if( sp.length >= 3 ) {
					String year  = sp[0];
					String month = sp[1];
					String day   = sp[2];
					
					if( k.equals("/") ) {
						year  = sp[2];
						month = sp[0];
						day   = sp[1];
					}
					
					if( year.length() == 2 ) {
						year = "20" + year;
					}
					if( month.length() == 1 ) {
						month = "0" + month;
					}
					if( day.length() == 1 ) {
						day = "0" + day;
					}
					yyymmdd = year + month + day;
				} else {
					yyymmdd = yyymmdd.replaceAll("-", "");
					yyymmdd = yyymmdd.replaceAll(".", "");
					yyymmdd = yyymmdd.replaceAll("/", "");
				}
				
				if( yyymmdd.length() != 8 ) {
					yyymmdd = null;
				}
			} catch (Exception e) {
				yyymmdd = null;
			}
		}
		
		return yyymmdd;
	}
	
	
	/**
	* String이 알파벳과 숫자로만 이루어졌는지 체크한다
	* @param str String
	* @return boolean
	*/
	public static boolean isAlphaNumeic(String str){
	 
	     if(str == null) return false;
	     char[] ch = str.toCharArray();
	     for( char c : ch ){
	         if(!(( c >= '0' && c <= '9')||( c >= 'a' && c <= 'z')||( c >= 'A' && c <= 'Z'))){
	             return false;
	          }
	     }
	     return true;
	}
	
	
	/**
	* 에러 메세지 출력
	*/
	/*
	 * public static void printError(Exception e){ System.out.println(
	 * "\n\n\n###error... " + Function.getCurrDateTime() + "\n" + e.getMessage() +
	 * "\n\n\n" ); } public static void printError(HttpServletRequest request,
	 * Exception e){ String url = request.getRequestURI(); System.out.println(
	 * "\n\n\n###error... " + DateTime.getCurrDateTime() + "\n" + "url: " + url +
	 * "\n" + e.getMessage() + "\n\n\n" ); }
	 */
	
	
	public static int parseInt(Object value) {
		if( value==null ) {
			return 0;
		}
		
		String valueStr = String.valueOf( value );
		valueStr = valueStr.replaceAll("[^0-9]", "");
		if( isNull(valueStr) ) {
			return 0;
		}
		
		try {
			double dValue = Double.parseDouble( valueStr );
			long iValue = Math.round(dValue);
			return (int)iValue;
		} catch (Exception e) {
		}
		
		return 0;
	}
	
	
	
	public static double parseDouble(Object value) {
		if( value==null ) {
			return 0.0;
		}
		
		String valueStr = String.valueOf( value );
		if( isNull(valueStr) ) {
			return 0.0;
		}
		
		try {
			double dValue = Double.parseDouble( valueStr );
			return dValue;
		} catch (Exception e) {
		}
		
		return 0;
	}
	
	
	
	
	/**
	 * 블라인드 SQL 인젝션 파라메터 확인
	 */
	public static String checkSqlInjectValue(String value){
		if( TextUtil.isNull(value) ) {
			return value;
		}
		String [] injectionCodes = {
			"'", "|", "--", "--, #", "/* */", "'or 1=1--", "-1 or", "-1' or", "../", "unexisting", "win.ini", "\"", 
			"union", "select", "delete", "insert", "update", "drop", "on error resume", "execute", "windows", "boot", };
		for (int i = 0; i < injectionCodes.length; i++) {
			value = value.replace(injectionCodes[i], "");
		}
		return value;
	}

	/**
	 * 부등호 구분값 변경
	 * > : 'H'
	 * < : 'L'
	 * 
	 */
	public static String changeSoiGb(String strIn) {
		String str = strIn;

		str = str.replaceAll("<", "L");
		str = str.replaceAll(">", "H");

		return str;
	}
}
