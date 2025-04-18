package com.acs.common;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acs.Constants;
import com.acs.schedule.service.TaskSchedulerService;
import com.acs.util.FileManager;
import com.acs.view.BaseAjaxView;
import com.google.gson.Gson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@EnableAutoConfiguration
public class CommonController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	TaskSchedulerService taskSchedulerService;
	
	/**
     * 공통파일정보를 다운로드함
     * @param FILSEQ 첨부파일SEQ
     */
    @RequestMapping(value = "/web/common/commonFileDown.json", produces = "application/text; charset=utf8")
	public @ResponseBody void commonFileDown(HttpServletResponse response, HttpServletRequest request , HttpSession session, @RequestParam Map<String, Object> params){	
		BaseAjaxView brv = new BaseAjaxView();
		Gson gs = new Gson();
		HashMap<String,Object> fileInfo = new HashMap<String,Object>();
		String VO_NO = (String)params.get("VO_NO");		
		if( VO_NO == null || "".equals("VO_NO") ) {
			String errMsg = "파일정보가 없습니다.";
			brv.setResultMessage(Constants.ERROR, errMsg);
		}

		try {
			
			fileInfo = (HashMap<String, Object>) taskSchedulerService.selectFileInfo(params);
			if( fileInfo == null || fileInfo.isEmpty() ) {
				String errMsg = "파일정보가 없습니다.";
				brv.setResultMessage(Constants.ERROR, errMsg);
			}
			
			String FILONM = (String)fileInfo.get("VO_FILENAME");
			String FILSNM = (String)fileInfo.get("VO_FILENAME");
			String FILPAT = (String)fileInfo.get("VO_FILEPATH");
			
			boolean result = false;
			//result = FileManager.doFileDownload(FILSNM, FILONM, Constants.ORG_FILE_PATH+FILPAT, response);
			result = FileManager.doFileDownload(FILSNM, FILONM, FILPAT+"/", response);
			
			if( result == false ) {
				String errMsg = "파일다운로드 오류입니다.";
				brv.setResultMessage(Constants.ERROR, errMsg);
			}
			
		}catch(Exception e) {
			String errMsg = "파일다운로드 오류입니다.";
			brv.setResultMessage(Constants.ERROR, errMsg);
			e.printStackTrace();
		}
	}
    
    
	
	
}
