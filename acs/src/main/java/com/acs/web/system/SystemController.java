package com.acs.web.system;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.acs.Constants;
import com.acs.schedule.TaskScheduler;
import com.acs.util.Function;
import com.acs.view.BaseAjaxView;
import com.acs.web.system.service.SystemService;
import com.google.gson.Gson;

import org.ebml.io.DataSource;

@Controller
@EnableAutoConfiguration
public class SystemController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//파일 업로드 경로 properties 
	@Value("${upload.path}")
	private String UPLOAD_FILE_PATH;
	
	@Value("${upload.common}")
	private String COMMON_FILE_PATH;
	
	@Autowired
	SystemService systemService;
	
	@Autowired
	TaskScheduler taskScheduler;
	
	@RequestMapping("/system/setting.do")
	public ModelAndView setting(HttpServletRequest request, HttpServletResponse response, HttpSession session, @RequestParam Map<String, Object> params) throws Exception {
		ModelAndView mav = new ModelAndView("web/system/setting.tiles");
		String dataYn = "";
		int sysCnt = systemService.selectSystemCnt(params);
		
		// 시스템 설정 데이터
		Map<String, Object>  getUserMap = null;
		if(sysCnt > 0) {
			dataYn = "Y";
			getUserMap = systemService.selectSystemInfo(params);
		}else {
			dataYn = "N";
		}
				
		mav.addObject("dataYn", dataYn);
		mav.addObject("resultMap", getUserMap);
		mav.addObject("menu","M02");
		return mav;
	}
	
	/*
	 *  시스템 설정 저장
	 *  @date 2022-10
	 */
	@RequestMapping(value = "/api/system/saveSystem.json", produces = "application/json; charset=utf8")
	public @ResponseBody String saveSystem(HttpServletRequest request, HttpSession session,  @RequestParam Map<String, Object> params){
		BaseAjaxView brv = new BaseAjaxView();
		Gson gs = new Gson();
		logger.info("params ::     " + params);
		
		// 세션정보
		Map sessionMap = (Map)session.getAttribute("SESSION_MAP"); 
		if(sessionMap==null || sessionMap.size()<0){
			brv.setResultMessage(Constants.SESSION_ERROR, "세션이 만료되었습니다.");
			return gs.toJson(brv);
		}
		
		String sUserID = Function.nvl(sessionMap.get("USER_ID")).trim();
		logger.info("sUserID : " + sUserID);	
		
		String mode    	= Function.nvl((String) params.get("mode"));
		String systemNo = Function.nvl((String) params.get("systemNo"));
		String wmsUrl  	= Function.nvl((String) params.get("wmsUrl"));
		String wmsId  	= Function.nvl((String) params.get("wmsId"));
		String wmsPw  	= Function.nvl((String) params.get("wmsPw"));
		String wmsPt 	= Function.nvl((String) params.get("wmsPt"));
		String apiUrl  	= Function.nvl((String) params.get("apiUrl"));
		String apiId  	= Function.nvl((String) params.get("apiId"));
		String apiPw  	= Function.nvl((String) params.get("apiPw"));
		String apiPt 	= Function.nvl((String) params.get("apiPt"));
		
		String sysStime = Function.nvl((String) params.get("sysStime"));
		String sysEtime = Function.nvl((String) params.get("sysEtime"));
		String sysPtime = Function.nvl((String) params.get("sysPtime"));
		String sysPath  = Function.nvl((String) params.get("sysPath"));
		
		Map<String, Object> paramMap = new HashMap<String, Object>();	
		paramMap.put("SYSTEM_NO", systemNo);
	    paramMap.put("WMS_URL", wmsUrl);
		paramMap.put("WMS_ID", wmsId);
		paramMap.put("WMS_PW", wmsPw);
		paramMap.put("WMS_PORT", wmsPt);
		paramMap.put("API_URL", apiUrl);
		paramMap.put("API_ID", apiId);
		paramMap.put("API_PW", apiPw);
		paramMap.put("API_PORT", apiPt);
		paramMap.put("SYS_PATH", sysPath);
		paramMap.put("SYS_STIME", sysStime);
		paramMap.put("SYS_ETIME", sysEtime);
		paramMap.put("SYS_PTIME", sysPtime);
		//paramMap.put("SYS_VTIME", "");
		paramMap.put("ISRT_ID", sUserID);
		paramMap.put("UPDT_ID", sUserID);
		
		try {
			if("N".equals(mode)) {
				int  systemInsert = systemService.systemInsert(paramMap);
			}else {
				int  systemUpdate = systemService.systemUpdate(paramMap);
			}
			
			// 스케줄러 이벤트
			HashMap<Object, Object> result = updateScheduler(paramMap);
			String res = Function.nvl((String) result.get("res"));
			
			logger.info("sys res ::     " + res);
			
		} catch (Exception e) {
			logger.info("e :: " + e);
			brv.setResultMessage(Constants.ERROR, "SYSTEM 저장 오류.");
			return gs.toJson(brv);	
		} 

		return gs.toJson(brv);
	}	
	
	public @ResponseBody HashMap<Object, Object>  updateScheduler(@RequestParam Map<String, Object> params) throws Exception{
		String sysPtime = Function.nvl((String) params.get("SYS_PTIME"));	//API Polling 시간
		
		logger.info("sys params ::     " + sysPtime);
		
		taskScheduler.stopScheduler();
		Thread.sleep(1000);
		taskScheduler.changeCronSet("0 0/"+sysPtime+" * * * *");
		taskScheduler.startScheduler();
		
		HashMap<Object, Object> res = new HashMap<Object, Object>();
		res.put("res", "success");
		return res;
		
	}
	
	
	
	
	
}
