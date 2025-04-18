package com.acs.web.log;

import java.util.HashMap;
import java.util.List;
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
import com.acs.common.paging.CommonPagenation;
import com.acs.web.log.service.LogSercive;
import com.acs.util.Function;
import com.acs.view.BaseAjaxView;
import com.google.gson.Gson;

@Controller
@EnableAutoConfiguration
public class LogController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//파일 업로드 경로 properties 
	@Value("${upload.path}")
	private String UPLOAD_FILE_PATH;
	
	@Value("${upload.common}")
	private String COMMON_FILE_PATH;
	
	@Autowired
	LogSercive logSercive;
	
	@RequestMapping("/log/logList.do")
	public ModelAndView logList(HttpServletRequest request, HttpServletResponse response, HttpSession session, @RequestParam Map<String, Object> params) throws Exception {
		ModelAndView mav = new ModelAndView("web/log/logList.tiles");
		// 세션정보
		Map sessionMap = (Map)session.getAttribute("SESSION_MAP"); 
		String  sUserId = Function.nvl(sessionMap.get("USER_ID")).trim();
		 logger.info("sUserId : " + sUserId);	
				
		mav.addObject("sUserId", sUserId);
		mav.addObject("menu","M03");
		return mav;
	}
	
	/*
	 *  LOG 정보 리스트
	 *  @date 2022-10
	 */
	@RequestMapping(value = "/api/log/selectLogList.json", produces = "application/json; charset=utf8")
	public @ResponseBody String selectLogList(HttpServletRequest request , HttpSession session, @RequestParam Map<String, Object> params){	
		CommonPagenation page = new CommonPagenation();
		BaseAjaxView brv = new BaseAjaxView();
		Gson gs = new Gson();
		
		String logGb  = Function.nvl((String) params.get("logGb"));
		String sDate  = Function.nvl((String) params.get("sDate"));
		String eDate  = Function.nvl((String) params.get("eDate"));
		params.put("LOG_GB",  logGb);
		
		try {			
			int totalCnt = logSercive.selectLogListCnt(params);
			HashMap<String, Object> pageMap = page.getPageInfo( Integer.valueOf((String) params.get("pageNumber")), Integer.valueOf((String) params.get("rowsPerPage")) , totalCnt);
			String startPage =   Function.nvl(String.valueOf(pageMap.get("startPage")));
			params.put("startPage",  Integer.parseInt(startPage) - 1);
			params.put("endPage",  pageMap.get("endPage"));
			List<HashMap<String,Object>> rList = logSercive.selectLogList(params);
			brv.setData("result", rList);
			brv.setData("page", pageMap);
			brv.setData("params", params);
		}catch(Exception e) {
			brv.setResultMessage(Constants.ERROR, Constants.errorCodeMsg);
			e.printStackTrace();
		}
		return gs.toJson(brv);	
	}	
	
}
