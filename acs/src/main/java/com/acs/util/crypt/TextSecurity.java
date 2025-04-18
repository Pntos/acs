package com.acs.util.crypt;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import com.acs.util.TextUtil;

/* *
 * <pre>
 * @author 
 * 텍스트보안 처리를 위한 암호화/복호화 모듈.
 * </pre>
 */
public class TextSecurity {

	private static long OPERATION_KEY;
	private static long PUBLIC_KEY;
	private static long PERSONAL_KEY;
	private static byte[] mSSOBaseEncMap, mSSOBaseDecMap;
	
    static {
    	byte[] ssobaseMap = {
	    	(byte)'n', (byte)'r', (byte)'s', (byte)'t', (byte)'b', (byte)'v', 
			(byte)'H', (byte)'Y', (byte)'K', (byte)'Q', (byte)'G', (byte)'C',
	        (byte)'W', (byte)'X', (byte)'J', (byte)'V', (byte)'S', (byte)'T',
	        (byte)'B', (byte)'Z', (byte)'x', (byte)'u', (byte)'c', (byte)'w', 
	        (byte)'h', (byte)'f', (byte)'g', (byte)'e', (byte)'i', (byte)'j', 
	        (byte)'M', (byte)'N', (byte)'O', (byte)'P', (byte)'D', (byte)'R',
	        (byte)'k', (byte)'z', (byte)'m', (byte)'q', (byte)'o', (byte)'p', 
	        (byte)'E', (byte)'A', (byte)'I', (byte)'U', (byte)'F', (byte)'L',
	        (byte)'d', (byte)'a', (byte)'y', (byte)'l',
	        (byte)'0', (byte)'1', (byte)'2', (byte)'3', (byte)'4', (byte)'5',
	        (byte)'6', (byte)'7', (byte)'8', (byte)'9', (byte)'@', (byte)'/' };
    	
        mSSOBaseEncMap = ssobaseMap;
        mSSOBaseDecMap = new byte[128];
        for (int i=0; i<mSSOBaseEncMap.length; i++)
            mSSOBaseDecMap[mSSOBaseEncMap[i]] = (byte) i;
    }

	static {
		OPERATION_KEY = 62;
		PUBLIC_KEY = 52845;
		PERSONAL_KEY = 22719;
	}
	
	public TextSecurity( ) {
	}

	private static long unsignedLong(long b) {
		if (b > 65535) {
			return (long) (b & 0xFFFF);
		} else if (b < 0) {
			return (long) (b & 0xFF);
		} else {
			return (long) b;
		}
	}

	private static long unsignedByteToLong(byte b) {
		if (b > 65535) {
			return (long) (b & 0xFFFF);
		} else if (b < 0) {
			return (long)(b & 0xFF);
		} else {
			return (long) b;
		}
	}

    /**
     * @param aData the data to be encoded
     * @return the SSOBase-encoded <var>aData</var>
     * @exception IllegalArgumentException if NULL or empty array is passed
     */
    private static String ssoBaseEncode(byte[] aData) {
        if ((aData == null) || (aData.length == 0))
            throw new IllegalArgumentException("Can not encode NULL or empty byte array.");

        byte encodedBuf[] = new byte[((aData.length+2)/3)*4];

        // 3-byte to 4-byte conversion
        int srcIndex, destIndex;
        for (srcIndex=0, destIndex=0; srcIndex < aData.length-2; srcIndex += 3) {
            encodedBuf[destIndex++] = mSSOBaseEncMap[(aData[srcIndex] >>> 2) & 077];
            encodedBuf[destIndex++] = mSSOBaseEncMap[(aData[srcIndex+1] >>> 4) & 017 |
                        (aData[srcIndex] << 4) & 077];
            encodedBuf[destIndex++] = mSSOBaseEncMap[(aData[srcIndex+2] >>> 6) & 003 |
                        (aData[srcIndex+1] << 2) & 077];
            encodedBuf[destIndex++] = mSSOBaseEncMap[aData[srcIndex+2] & 077];
        }
        
        if (srcIndex < aData.length) {
            encodedBuf[destIndex++] = mSSOBaseEncMap[(aData[srcIndex] >>> 2) & 077];
            if (srcIndex < aData.length-1) {
                encodedBuf[destIndex++] = mSSOBaseEncMap[(aData[srcIndex+1] >>> 4) & 017 |
                    (aData[srcIndex] << 4) & 077];
                encodedBuf[destIndex++] = mSSOBaseEncMap[(aData[srcIndex+1] << 2) & 077];
            }
            else {
                encodedBuf[destIndex++] = mSSOBaseEncMap[(aData[srcIndex] << 4) & 077];
            }
        }

        // 인코딩 된 데이터의 끝 부분에 패딩 추가
        while (destIndex < encodedBuf.length) {
            encodedBuf[destIndex] = (byte) '=';
            destIndex++;
        }

        String result = new String(encodedBuf);
        return result;
    }


    /**
     * @param aData the SSOBase-encoded aData.
     * @return the decoded <var>aData</var>.
     * @exception IllegalArgumentException if NULL or empty data is passed
     */
    private static byte[] ssoBaseDecode(String aData) {
        if ((aData == null) || (aData.length() == 0))
            throw new IllegalArgumentException("Can not decode NULL or empty string.");

        byte[] data = aData.getBytes();

        // Skip padding from the end of encoded data
        int tail = data.length;
        while (data[tail-1] == '=')
            tail--;

        byte decodedBuf[] = new byte[tail - data.length/4];

        // ASCII-printable to 0-63 conversion
        for (int i = 0; i < data.length; i++)
            data[i] = mSSOBaseDecMap[data[i]];

        // 4-byte to 3-byte conversion
        int srcIndex, destIndex;
        for (srcIndex = 0, destIndex=0; destIndex < decodedBuf.length-2;
                srcIndex += 4, destIndex += 3) {
            decodedBuf[destIndex] = (byte) ( ((data[srcIndex] << 2) & 255) |
                ((data[srcIndex+1] >>> 4) & 003) );
            decodedBuf[destIndex+1] = (byte) ( ((data[srcIndex+1] << 4) & 255) |
                ((data[srcIndex+2] >>> 2) & 017) );
            decodedBuf[destIndex+2] = (byte) ( ((data[srcIndex+2] << 6) & 255) |
                (data[srcIndex+3] & 077) );
        }

        // Handle last 1 or 2 bytes
        if (destIndex < decodedBuf.length)
            decodedBuf[destIndex] = (byte) ( ((data[srcIndex] << 2) & 255) |
                ((data[srcIndex+1] >>> 4) & 003) );
        if (++destIndex < decodedBuf.length)
            decodedBuf[destIndex] = (byte) ( ((data[srcIndex+1] << 4) & 255) |
                ((data[srcIndex+2] >>> 2) & 017) );

        return decodedBuf;
    }
	
    
	
	/**
	 * 문자열 암호화 처리
	 * 
	 * @param org_str 암호화할 문자열
	 * @return String 암호화 완료된 문자열
	 * @throws 
	 */
	public static String encode(String org_str) {
		
		String fin_str = "";
		
		//문자열 길이만큼의 배열을 만들고 byte 형으로 변환한다.
		byte orgByte[] = new byte[org_str.getBytes().length]; 
		orgByte = org_str.getBytes();		
		char finChar[] = new char[org_str.getBytes().length];
		byte finByte[] = new byte[org_str.getBytes().length];
		
		try {
			long wKey = OPERATION_KEY;

			for (int i=0;i<orgByte.length; i++) {
				finChar[i] = (char)( (byte)((orgByte[i])^(wKey>>8)) & 0xFF );
				wKey = ( (finChar[i] + wKey) * PUBLIC_KEY + PERSONAL_KEY);
				wKey = unsignedLong(wKey); 
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//char 배열을 byte 배열로 변환
		for (int i=0;i<finChar.length; i++) {
			finByte[i] = (byte)finChar[i];
		}

		// SSO인코딩 처리
		fin_str = ssoBaseEncode(finByte);

		return fin_str;
	}
	
	public static String doubleEncode(String org_str) {
		String fin_str = encode(org_str);
		fin_str = encode(fin_str);
		fin_str = encode(fin_str);
		fin_str = encode(fin_str);
		fin_str = encode(fin_str);
		return fin_str;
	}
	
	
	/**
	 * 문자열 복호화 처리
	 * 
	 * @param org_str
	 *            복호화할 문자열
	 * @return String
	 *            복호화 완료된 문자열
	 * @throws 
	 */
	public static String decode(String org_str) {
		return decode(org_str, null);
	}
	public static String doubleDecode(String org_str) {
		return decode(decode(decode(decode(decode(org_str, null)))));
	}
	
	/**
	 * 문자열 복호화 처리
	 * 
	 * @param org_str 복호화할 문자열
	 *        encode 문자열 인코드셑(EUC-KR,UTF-8,...)
	 * @return String 복호화 완료된 문자열
	 * @throws 
	 */
	public static String decode(String org_str, String encode) {
		String fin_str = null;
		
		// sso 디코딩 처리
		byte orgByte[] = null; 
		try {
			orgByte = ssoBaseDecode(org_str);
		} catch (Exception e) {
			e.printStackTrace();
		}

		char orgChar[] = new char[orgByte.length];
		char finChar[] = new char[orgByte.length];
		byte finByte[] = new byte[orgByte.length];
		
		//디코딩 처리
		try {
			long wKey = OPERATION_KEY;

			for (int i=0;i<orgByte.length; i++) {
				orgChar[i] = (char)unsignedByteToLong(orgByte[i]);
			}

			for (int i=0;i<orgByte.length; i++) {
				finChar[i] = (char)( (orgByte[i])^(wKey>>8)  );
				finByte[i] = (byte)finChar[i];
				wKey = ( (orgChar[i] + wKey) * PUBLIC_KEY + PERSONAL_KEY);
				wKey = unsignedLong(wKey);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			if (encode==null) {
				fin_str = new String(finByte);
			} else {
				fin_str = new String(finByte, encode);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return fin_str;
	}
	
	/**
	 * 문자열 10자리의 랜덤키값 추출
	 * @return  String  키값
	 * @exception Exception
	 */
	public static String getRandomString(int length) {
		Random r1 = new Random();        
		Random r2 = new Random();        

		//int string_length = length + (int)(r1.nextDouble()*2);
		int string_length = length;       
		String big = "";
		
		int cnt = 0;
		
		//랜덤하게 발생시킨 문자열의 길이만큼 값이 들어올때까지 실행
		while (cnt < string_length){
			
			//알파벳 대,소문자, 숫자범위의 아스키값발생
			//int num1 = (int)48 + (int)(r2.nextDouble()*74);
			
			//알파벳 대,소문자 아스키값발생
			int num1 = (int)65 + (int)(r2.nextDouble()*74);
			
			// 특수문자 제외시킴
			if ((num1>=48 && num1<=57)||(num1>=65 && num1<=90)||(num1>=97 && num1<=122)){
				
				//아스키코드값을 char로 형변환 후 저장
				big =big+(char)num1;
				cnt++;
            }       
		}
		return big;
	}
	
	
	
	
	/**
	 * SHA-256 일방향 암호 알고리즘을 사용한 문자열 암호화
	 * @return  String  키값
	 * @throws UnsupportedEncodingException 
	 * @exception Exception
	 */
	private static String encryptionSHA256(String plainStr) throws UnsupportedEncodingException {
		String cipherStr = "";
		byte plain[] = plainStr.getBytes("UTF-8");
		byte pbCipher[] = new byte[32];
		KISA_SHA256.SHA256_Encrpyt( plain, plainStr.length(), pbCipher );
		for (int i=0; i<32; i++) {
			cipherStr += Integer.toHexString(0xff&pbCipher[i])+"";
		}
		
		return cipherStr;
	}
	
	/**
	 * SEED 방식의 대칭키 암호 알고리즘을 사용한 문자열 암호화
	 * @param key seed 값
	 * @param plainStr 암호화할 평문
	 * @throws UnsupportedEncodingException 
	 * @exception Exception
	 */
	public static String encryptionSeed(String key, String plainStr) throws UnsupportedEncodingException {
		String cipherStr = "";
		if( TextUtil.isNull(key) || TextUtil.isNull(plainStr) ) {
			return "";
		}
		
		int pdwRoundKey[] = new int[32];
		byte pbCipher[]   = new byte[16];
		byte plainKey[] = encryptionSHA256(doubleEncode(key)).getBytes("UTF-8");
		byte plain[] = encryptionSHA256(doubleEncode(plainStr)).getBytes("UTF-8");
		
		SEED_KISA.SeedRoundKey(pdwRoundKey, plainKey);
		SEED_KISA.SeedEncrypt(plain, pdwRoundKey, pbCipher);
		
		for (int i=0; i<16; i++) {
			cipherStr += Integer.toHexString(0xff&pbCipher[i])+"";
		}
		cipherStr = encryptionSHA256(cipherStr);
		
		return cipherStr;
	}
}
