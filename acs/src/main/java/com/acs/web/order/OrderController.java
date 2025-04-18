package com.acs.web.order;

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
import com.acs.schedule.service.TaskSchedulerService;
import com.acs.util.Function;
import com.acs.view.BaseAjaxView;
import com.acs.web.order.service.OrderService;
import com.google.gson.Gson;

@Controller
@EnableAutoConfiguration
public class OrderController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//파일 업로드 경로 properties 
	@Value("${upload.path}")
	private String UPLOAD_FILE_PATH;
	
	@Value("${upload.common}")
	private String COMMON_FILE_PATH;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	TaskSchedulerService taskSchedulerService;
	
	
	@RequestMapping("/order/mappingList.do")
	public ModelAndView mappingList(HttpServletRequest request, HttpServletResponse response, HttpSession session, @RequestParam Map<String, Object> params) throws Exception {
		ModelAndView mav = new ModelAndView("web/order/mappingList.tiles");
		// 세션정보
		Map sessionMap = (Map)session.getAttribute("SESSION_MAP"); 
		String  sUserId = Function.nvl(sessionMap.get("USER_ID")).trim();
		 logger.info("sUserId : " + sUserId);	
				
		mav.addObject("sUserId", sUserId);
		mav.addObject("menu","M01");
		return mav;
	}
	
	@RequestMapping("/order/orderVedio.do")
	public ModelAndView orderVedio(HttpServletRequest request, HttpServletResponse response, HttpSession session, @RequestParam Map<String, Object> params) throws Exception {
		ModelAndView mav = new ModelAndView("web/order/orderVedio.tiles");
		// 세션정보
		Map sessionMap = (Map)session.getAttribute("SESSION_MAP"); 
		String  sUserId = Function.nvl(sessionMap.get("USER_ID")).trim();
		 logger.info("sUserId : " + sUserId);	
				
		mav.addObject("sUserId", sUserId);
		mav.addObject("menu","M01");
		return mav;
	}
	
	/*
	 *  영상목록 리스트
	 *  @date 2022-10
	 */
	@RequestMapping(value = "/api/order/selectOrderList.json", produces = "application/json; charset=utf8")
	public @ResponseBody String selectOrderList(HttpServletRequest request , HttpSession session, @RequestParam Map<String, Object> params){	
		CommonPagenation page = new CommonPagenation();
		BaseAjaxView brv = new BaseAjaxView();
		Gson gs = new Gson();
		
		String voState =   Function.nvl(String.valueOf(params.get("voState")));
		String orderType =   Function.nvl(String.valueOf(params.get("orderType")));
		String fromDate =   Function.nvl(String.valueOf(params.get("fromDate")));
		String toDate =   Function.nvl(String.valueOf(params.get("toDate")));
		String orderNo =   Function.nvl(String.valueOf(params.get("orderNo")));
		
		//params.put("VO_STATE", voState);
		params.put("ORDER_TYPE", orderType);
		params.put("SDATE", fromDate);
		params.put("EDATE", toDate);
		params.put("ORDER_NO", orderNo);
		
		try {			
			// 상단통계
			Map<String, Object> getOrderCntMap =  orderService.selectOrderTopCnt(params);
			
			int totalCnt = orderService.selectOrderListCnt(params);
			HashMap<String, Object> pageMap = page.getPageInfo( Integer.valueOf((String) params.get("pageNumber")), Integer.valueOf((String) params.get("rowsPerPage")) , totalCnt);
			String startPage =   Function.nvl(String.valueOf(pageMap.get("startPage")));
			params.put("startPage",  Integer.parseInt(startPage) - 1);
			params.put("endPage",  pageMap.get("endPage"));
			List<HashMap<String,Object>> rList = orderService.selectOrderList(params); 
			
			brv.setData("cntMap", getOrderCntMap);
			brv.setData("result", rList);
			brv.setData("page", pageMap);
			brv.setData("params", params);
		}catch(Exception e) {
			brv.setResultMessage(Constants.ERROR, Constants.errorCodeMsg);
			e.printStackTrace();
		}
		return gs.toJson(brv);	
	}	
	
	/**
     * 공통파일정보를 다운로드함
     * @param FILSEQ 첨부파일SEQ
     * @return 
     */
    @RequestMapping(value = "/api/common/commonOrgFileInfo.json", produces = "application/text; charset=utf8")
	public @ResponseBody String commonOrgFileInfo(HttpServletResponse response, HttpServletRequest request , HttpSession session, @RequestParam Map<String, Object> params){	
		BaseAjaxView brv = new BaseAjaxView();
		Gson gs = new Gson();
		HashMap<String,Object> fileInfo = new HashMap<String,Object>();
		String voWmvNo = (String)params.get("voWmvNo");		
		params.put("VO_WMS_NO", voWmvNo);
		
		try {
			fileInfo = (HashMap<String, Object>) taskSchedulerService.selectOrgFileInfo(params);
			brv.setData("result", fileInfo);
		}catch(Exception e) {
			String errMsg = "오류입니다.";
			brv.setResultMessage(Constants.ERROR, errMsg);
			e.printStackTrace();
		}
		return gs.toJson(brv);
	}
    
}
