package com.acs.web.system;

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
import com.acs.web.system.service.UserService;
import com.google.gson.Gson;
import com.acs.util.Function;
import com.acs.view.BaseAjaxView;

@Controller
@EnableAutoConfiguration
public class UserController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//파일 업로드 경로 properties 
	@Value("${upload.path}")
	private String UPLOAD_FILE_PATH;
	
	@Value("${upload.common}")
	private String COMMON_FILE_PATH;
	
	@Autowired
	UserService userService;
	
	@RequestMapping("/user/userList.do")
	public ModelAndView userList(HttpServletRequest request, HttpServletResponse response, HttpSession session, @RequestParam Map<String, Object> params) throws Exception {
		ModelAndView mav = new ModelAndView("web/user/userList.tiles");
		// 세션정보
		Map sessionMap = (Map)session.getAttribute("SESSION_MAP"); 
		String sUserID = Function.nvl(sessionMap.get("USER_ID")).trim();
		logger.info("sUserID : " + sUserID);	
				
		mav.addObject("sUserId", sUserID);
		mav.addObject("menu","M01");
		return mav;
	}
	
	@RequestMapping("/user/userEdit.do")
	public ModelAndView userEdit(HttpServletRequest request, HttpServletResponse response, HttpSession session, @RequestParam Map<String, Object> params) throws Exception {
		ModelAndView mav = new ModelAndView("web/user/userEdit.tiles");
		// 세션정보
		Map sessionMap = (Map)session.getAttribute("SESSION_MAP"); 
		String sUserID = Function.nvl(sessionMap.get("USER_ID")).trim();
		logger.info("sUserID : " + sUserID);	
				
		mav.addObject("sUserId", sUserID);
		mav.addObject("menu","M01");
		return mav;
	}
	
	/*
	 *  사용자 정보 리스트
	 *  @date 2022-10
	 */
	@RequestMapping(value = "/api/user/selectUserList.json", produces = "application/json; charset=utf8")
	public @ResponseBody String selectUserList(HttpServletRequest request , HttpSession session, @RequestParam Map<String, Object> params){	
		CommonPagenation page = new CommonPagenation();
		BaseAjaxView brv = new BaseAjaxView();
		Gson gs = new Gson();
		
		try {			
			int totalCnt = userService.selectUserListCnt(params);
			HashMap<String, Object> pageMap = page.getPageInfo( Integer.valueOf((String) params.get("pageNumber")), Integer.valueOf((String) params.get("rowsPerPage")) , totalCnt);
			String startPage =   Function.nvl(String.valueOf(pageMap.get("startPage")));
			params.put("startPage",  Integer.parseInt(startPage) - 1);
			params.put("endPage",  pageMap.get("endPage"));
			List<HashMap<String,Object>> rList = userService.selectUserList(params); 
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
	 *  사용자 정보 리스트
	 *  @date 2022-10
	 */
	@RequestMapping(value = "/api/user/selectUserInfo.json", produces = "application/json; charset=utf8")
	public @ResponseBody String selectUserInfo(HttpServletRequest request , HttpSession session, @RequestParam Map<String, Object> params){	
		CommonPagenation page = new CommonPagenation();
		BaseAjaxView brv = new BaseAjaxView();
		Gson gs = new Gson();
		
		try {			
			Map<String, Object> getUserMap = userService.getUser(params);
			brv.setData("result", getUserMap);
		}catch(Exception e) {
			brv.setResultMessage(Constants.ERROR, Constants.errorCodeMsg);
			e.printStackTrace();
		}
		return gs.toJson(brv);	
	}	
	
	
	/*
	 *  아이디 중복확인
	 *  @date 2022-10
	 */
	@RequestMapping(value = "/api/user/selectUserId.json", produces = "application/json; charset=utf8")
	public @ResponseBody String selectUserId(HttpServletRequest request , HttpSession session, @RequestParam Map<String, Object> params){	
		CommonPagenation page = new CommonPagenation();
		BaseAjaxView brv = new BaseAjaxView();
		Gson gs = new Gson();
		
		try {			
			int checkIdCnt = userService.selectUserId(params);
			brv.setData("checkIdCnt", checkIdCnt);
		}catch(Exception e) {
			brv.setResultMessage(Constants.ERROR, Constants.errorCodeMsg);
			e.printStackTrace();
		}
		return gs.toJson(brv);	
	}	
	
	/*
	 *  사용자 정보 저장
	 *  @date 2022-10
	 */
	@RequestMapping(value = "/api/user/saveUser.json", produces = "application/json; charset=utf8")
	public @ResponseBody String saveUser(HttpServletRequest request, HttpSession session,  @RequestParam Map<String, Object> params){
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
		
		String mode    = Function.nvl((String) params.get("uMode"));
		String userNo  = Function.nvl((String) params.get("userNo"));
		String userID  = Function.nvl((String) params.get("userID"));
		String passwd  = Function.nvl((String) params.get("passwd"));
		String userNm  = Function.nvl((String) params.get("userNm"));
		String userOrg = Function.nvl((String) params.get("userOrg"));
		String userGb  = Function.nvl((String) params.get("userGb"));
		String useYn   = Function.nvl((String) params.get("useYn"));
		String userMemo = Function.nvl((String) params.get("userMemo"));
		String delYn = Function.nvl((String) params.get("delYn"));
		
		Map<String, Object> paramMap = new HashMap<String, Object>();	
		paramMap.put("USER_NO", userNo);
		paramMap.put("USER_ID", userID);
		paramMap.put("USER_NM", userNm); 
		paramMap.put("USER_PW", passwd);
		paramMap.put("USER_GB", userGb); 
		paramMap.put("USER_ORG", userOrg); 
		paramMap.put("USE_YN", useYn); 
		paramMap.put("DEl_YN", delYn);
		paramMap.put("USER_MEMO", userMemo);
		paramMap.put("ISRT_ID", sUserID);
		paramMap.put("UPDT_ID", sUserID);
		
		
		try {
			if("N".equals(mode)) {
				int  userInsert = userService.userInsert(paramMap);
			}else {
				int  userUpdate = userService.userUpdate(paramMap);
			}
			
		} catch (Exception e) {
			logger.info("e :: " + e);
			brv.setResultMessage(Constants.ERROR, "저장 오류.");
			return gs.toJson(brv);	
		} 

		return gs.toJson(brv);
	}	
	
	/*
	 *  사용자 정보 삭제
	 *  @date 2022-10
	 */
	@RequestMapping(value = "/api/user/deleteUser.json", produces = "application/json; charset=utf8")
	public @ResponseBody String deleteUser(HttpServletRequest request, HttpSession session,  @RequestParam Map<String, Object> params){
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
		
		String userNo  = Function.nvl((String) params.get("userNo"));
		
		Map<String, Object> paramMap = new HashMap<String, Object>();	
		paramMap.put("USER_NO", userNo);
		paramMap.put("DEL_YN", "Y");
		paramMap.put("UPDT_ID", sUserID);
		
		try {
			int  userDelete = userService.userUpdate(paramMap);
			
		} catch (Exception e) {
			logger.info("e :: " + e);
			brv.setResultMessage(Constants.ERROR, "삭제 오류.");
			return gs.toJson(brv);	
		} 

		return gs.toJson(brv);
	}	
	
	
		
}
