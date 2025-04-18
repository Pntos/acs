package com.acs.web.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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

import com.acs.Constants;
import com.acs.common.paging.CommonPagenation;
import com.acs.util.Function;
import com.acs.view.BaseAjaxView;
import com.acs.web.system.service.IpService;
import com.google.gson.Gson;

@Controller
@EnableAutoConfiguration
public class IpController {
private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//파일 업로드 경로 properties 
	@Value("${upload.path}")
	private String UPLOAD_FILE_PATH;
	
	@Value("${upload.common}")
	private String COMMON_FILE_PATH;
	
	@Autowired
	IpService ipService;
	
	/*
	 *  IP 접속관리 리스트
	 *  @date 2022-10
	 */
	@RequestMapping(value = "/api/system/selectIpList.json", produces = "application/json; charset=utf8")
	public @ResponseBody String selectIpList(HttpServletRequest request , HttpSession session, @RequestParam Map<String, Object> params){	
		CommonPagenation page = new CommonPagenation();
		BaseAjaxView brv = new BaseAjaxView();
		Gson gs = new Gson();
		
		try {			
			int totalCnt = ipService.selectIpListCnt(params);
			HashMap<String, Object> pageMap = page.getPageInfo( Integer.valueOf((String) params.get("pageNumber2")), Integer.valueOf((String) params.get("rowsPerPage2")) , totalCnt);
			String startPage =   Function.nvl(String.valueOf(pageMap.get("startPage")));
			params.put("startPage",  Integer.parseInt(startPage) - 1);
			params.put("endPage",  pageMap.get("endPage"));
			List<HashMap<String,Object>> rList = ipService.selectIpList(params); 
			brv.setData("result", rList);
			brv.setData("page", pageMap);
			brv.setData("params", params);
		}catch(Exception e) {
			brv.setResultMessage(Constants.ERROR, Constants.errorCodeMsg);
			e.printStackTrace();
		}
		return gs.toJson(brv);	
	}	
	
	/*
	 *  IP 접속관리 저장
	 *  @date 2022-10
	 */
	@RequestMapping(value = "/api/system/saveIp.json", produces = "application/json; charset=utf8")
	public @ResponseBody String saveIp(HttpServletRequest request, HttpSession session,  @RequestParam Map<String, Object> params){
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
		
		String mode    = Function.nvl((String) params.get("iMode"));
		String ipAddr1  = Function.nvl((String) params.get("ipAddr1"));
		String ipAddr2  = Function.nvl((String) params.get("ipAddr2"));
		String ipAddr3  = Function.nvl((String) params.get("ipAddr3"));
		String ipAddr4  = Function.nvl((String) params.get("ipAddr4"));
		String ipUser = Function.nvl((String) params.get("ipUser"));
		String ipMemo  = Function.nvl((String) params.get("ipMemo"));
		String delYn = Function.nvl((String) params.get("delYn"));
		
		Map<String, Object> paramMap = new HashMap<String, Object>();	
		paramMap.put("IP_ADDR1", ipAddr1);
		paramMap.put("IP_ADDR2", ipAddr2);
		paramMap.put("IP_ADDR3", ipAddr3); 
		paramMap.put("IP_ADDR4", ipAddr4);
		paramMap.put("IP_USER", ipUser); 
		paramMap.put("IP_MEMO", ipMemo); 
		paramMap.put("IP_DELYN", delYn); 
		paramMap.put("ISRT_ID", sUserID);
		paramMap.put("UPDT_ID", sUserID);
		
		try {
			if("N".equals(mode)) {
				int  IpInsert = ipService.IpInsert(paramMap);
			}else {
				int  IpUpdate = ipService.IpUpdate(paramMap);
			}
			
		} catch (Exception e) {
			logger.info("e :: " + e);
			brv.setResultMessage(Constants.ERROR, "저장 오류.");
			return gs.toJson(brv);	
		} 

		return gs.toJson(brv);
	}	
	
	/*
	 *  IP 접속관리 삭제
	 *  @date 2022-10
	 */
	@RequestMapping(value = "/api/system/deleteIp.json", produces = "application/json; charset=utf8")
	public @ResponseBody String deleteIp(HttpServletRequest request, HttpSession session,  @RequestParam Map<String, Object> params){
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
		
		String ipNo  = Function.nvl((String) params.get("ipNo"));
		
		Map<String, Object> paramMap = new HashMap<String, Object>();	
		paramMap.put("IP_NO", ipNo);
		paramMap.put("IP_DELYN", "Y");
		paramMap.put("UPDT_ID", sUserID);
		
		try {
			int  ipDelete = ipService.IpUpdate(paramMap);
			
		} catch (Exception e) {
			logger.info("e :: " + e);
			brv.setResultMessage(Constants.ERROR, "삭제 오류.");
			return gs.toJson(brv);	
		} 

		return gs.toJson(brv);
	}	
	
	
}
