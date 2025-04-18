package com.acs.common;

import java.util.Enumeration;
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
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.acs.Constants;
import com.acs.util.CommonUtil;
import com.acs.util.Function;
import com.acs.view.BaseAjaxView;
import com.acs.util.crypt.TextSecurity;
import com.acs.web.system.service.IpService;
import com.acs.web.system.service.UserService;
import com.acs.web.log.service.LogSercive;

@Controller
@EnableAutoConfiguration
public class LoginController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//파일 업로드 경로 properties 
	@Value("${upload.path}")
	private String UPLOAD_FILE_PATH;
	
	@Value("${upload.common}")
	private String COMMON_FILE_PATH;
	
	@Autowired
	UserService userService;
	
	@Autowired
	LogSercive logSercive;
	
	@Autowired
	IpService ipService;
		
	private String VIEW_PATH = "/common/";
	
	@RequestMapping(value="/")
    @ResponseBody
    public ModelAndView index(HttpServletRequest request,  @RequestParam Map<String, Object> params) throws Exception{     	
    	ModelAndView mav = new ModelAndView();
    	
    	//접속아이피 체크        
    	String ckId = (String)params.get("chid"); // 테스트용 아이디
    	//String remoteAddr = request.getRemoteAddr();   
    	String remoteAddr = CommonUtil.getClientIpAddr(request);
    	System.out.println("remoteAddr : " + remoteAddr);
    	
    	boolean isInside = false;		
    	
        if("master".equals(ckId)) {
        	isInside = true;
        }else{
        	if("127.0.0.1".equals(remoteAddr) || "0:0:0:0:0:0:0:1".equals(remoteAddr)){
         		isInside = true;
         	}else{
         		Map<String, Object> ipChkMap = new HashMap<String, Object>();	
         		ipChkMap.put("IP_ACTIVEYN", "Y");
        		try {
        			List<HashMap<String, Object>> ipList = ipService.selectCheckIpList(ipChkMap);
        			if(ipList.size()>0){
        				 for(int i=0; i<ipList.size(); i++){
        					Map<String, Object> map = ipList.get(i);
        	 				String DBiP1 = String.valueOf(map.get("IP_ADDR1"));
        	 				String DBiP2 = String.valueOf(map.get("IP_ADDR2"));
        	 				String DBiP3 = String.valueOf(map.get("IP_ADDR3"));
        	 				String DBiP4 = String.valueOf(map.get("IP_ADDR4"));
        	 				String DBiP = DBiP1+"."+DBiP2+"."+DBiP3+"."+DBiP4;
        	 				System.out.println("DBiP : " + DBiP);
        	 				if(DBiP.equals(remoteAddr)){
        	 					isInside = true;	
        	 				}
        	 			 }
        	        }
        			
        		} catch (Exception e) {
        			isInside = false;	
        			e.printStackTrace();
        		}
	       	    
         	}
        }
		
        System.out.println("isInside : " + isInside);
        if(isInside == true) {
        	mav.setViewName("index");
        }else{
        	mav.setViewName(VIEW_PATH + "code404");
        }
        return mav;        
    }
	
	@RequestMapping(value = "/api/login.json", produces = "application/text; charset=utf8")
	public @ResponseBody String login(HttpServletRequest request , @RequestParam Map<String, Object> params){	
		BaseAjaxView brv = new BaseAjaxView();
		Gson gs = new Gson();						
		
		String userId = Function.nvl((String)params.get("id")).trim();
		String userPw = Function.nvl((String)params.get("password")).trim();
		
		//세션정보 확인
		HttpSession session =  request.getSession();
		if (session==null) {
			brv.setResultMessage("0002", "세션정보가 잘못되었습니다.");
			return gs.toJson(brv);
		}
		
		// 세션 초기화
		if(session!=null && session.getAttribute("SESSION_MAP")!= null){	
			Enumeration<String> sNm = session.getAttributeNames();
			while(sNm.hasMoreElements()){
				String name = sNm.nextElement().toString();
				String value = session.getAttribute(name).toString();
				System.out.println(name + " ////// " + value);
			}
			session.removeAttribute("SESSION_MAP");
		}
		
		try {		
			// 사용자 검색
			params.put("USER_ID", userId);
			Map<String, Object>  getUserMap = userService.getUser(params);
			
			if (getUserMap==null || getUserMap.size()==0) {	
				brv.setResultMessage("ssoMessage", "등록되지 않은 계정 정보입니다.");
				return gs.toJson(brv);
			}
			System.out.println("getUserMap ::: " + getUserMap);
			
			String inputPw = TextSecurity.encryptionSeed("PANTOS", userPw);
			// DB
			String dbUserPw   = String.valueOf(getUserMap.get("USER_PW"));
			String confirmPw = TextSecurity.encryptionSeed("PANTOS", dbUserPw);
			String dbUseYn   = String.valueOf(getUserMap.get("USE_YN"));	// 사용여부
			
			System.out.println(inputPw + "   <<< pwd  :::  " + confirmPw);
			if (!inputPw.equals(confirmPw)){
				brv.setResultMessage("0002", "비밀번호가 일치하지 않습니다.");
				return gs.toJson(brv);
			}
			if (dbUseYn.equals("N")){
				brv.setResultMessage("0003", "사용 정지된 계정 정보입니다.");
				return gs.toJson(brv);
			}
			
			// 세션에 로그인정보 세팅
			session.setAttribute("SESSION_MAP", getUserMap);
			
			//로그인 이력
			Map<String, Object> logMap = new HashMap<String, Object>();	
			logMap.put("LOG_GB", "LIO");
			logMap.put("LOG_MEMO", userId + " 로그인");
			logMap.put("ISRT_ID", userId);
			logSercive.logInsert(logMap);
			//로그인 이력
			
		} catch (Exception e) {
			brv.setResultMessage(Constants.ERROR, Constants.errorCodeMsg);
			e.printStackTrace();
		}
		
		
		
		return gs.toJson(brv);	
	}
	
	// logout
	@RequestMapping(value = "/api/logout.json", produces = "application/text; charset=utf8")
	public @ResponseBody String logout(HttpServletRequest request , HttpSession session, @RequestParam Map<String, Object> params){	
		BaseAjaxView brv = new BaseAjaxView();
		Gson gs = new Gson();	
		// 세션정보
		Map sessionMap = (Map)session.getAttribute("SESSION_MAP"); 
		if(sessionMap==null || sessionMap.size()<0){
			brv.setResultMessage(Constants.SESSION_ERROR, "세션이 만료되었습니다.");
			return gs.toJson(brv);
		}
		
		String sUserID = Function.nvl(sessionMap.get("USER_ID")).trim();
		logger.info("sUserID : " + sUserID);	
		
		//로그인 이력
		Map<String, Object> logMap = new HashMap<String, Object>();	
		logMap.put("LOG_GB", "LGO");
		logMap.put("LOG_MEMO", sUserID + " 로그아웃");
		logMap.put("ISRT_ID", sUserID);
		try {
			logSercive.logInsert(logMap);
		} catch (Exception e) {
			brv.setResultMessage(Constants.ERROR, Constants.errorCodeMsg);
			e.printStackTrace();
		}
		//로그인 이력
		
		if(session!=null) {			
			session.invalidate();
		}
		return gs.toJson(brv);	
	}
		
	// 세션체크
	@RequestMapping(value = "/api/sessionCheck.json", produces = "application/text; charset=utf8")
	public @ResponseBody String sessionCheck(HttpServletRequest request , @RequestParam Map<String, Object> params){	
		BaseAjaxView brv = new BaseAjaxView();
		Gson gs = new Gson();	
		
		String resultCode = Function.nvl(request.getAttribute("RESULT_CODE"));
		if("9999".equals(resultCode)){
			brv.setResultMessage("9999", "세션이 만료되었습니다.");
		}
		return gs.toJson(brv);	
	}
	
	
	
	
}
