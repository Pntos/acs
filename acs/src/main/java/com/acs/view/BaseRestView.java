package com.acs.view;

import java.util.HashMap;

import org.springframework.web.servlet.ModelAndView;

import com.acs.Constants;


@SuppressWarnings({"rawtypes","unchecked"})
public class BaseRestView extends ModelAndView {
	
	private HashMap mResultMap;
	
	public BaseRestView() {
		mResultMap = new HashMap();
		addObject("result", mResultMap);
		setResultMessage("0000", "");
	}
	
	public void setData(String attrName, Object data) {
		mResultMap.put(attrName, data);
	}

	/**
	* <pre>
	* 서버 처리결과 코드 및 처리결과 메시지를 담기 위한 메소드
	* </pre>
	* @param : resCode 서버 처리 결과 코드(정상=0000)
	* @param : resMessage 서버 처리 결과 메시지
	* @return : 
	*/
	public void setResultMessage(String resCode, String resMessage) {
		mResultMap.put(Constants.RES_CODE    ,resCode   );
		mResultMap.put(Constants.RES_MSG ,resMessage);
	}
}
