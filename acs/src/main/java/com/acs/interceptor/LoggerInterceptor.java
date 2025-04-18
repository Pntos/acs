package com.acs.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.acs.Constants;

@Component
public class LoggerInterceptor extends HandlerInterceptorAdapter {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//controller로 보내기 전 이벤트 작동(false - controller로 요청을 안함)
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//logger.info("============================== START ==============================");
		
		String requestURI = request.getRequestURI();
		String ext = requestURI.substring(requestURI.lastIndexOf(".")+1);
		/*
		 * String userAgent = request.getHeader("User-Agent"); String IP =
		 * request.getRemoteAddr();
		 */
		
		/*
		 * 	세션 제외 URL
		 */
		if(("/sms/store_result.do").equals(requestURI)){ 
			return true; 
		}

		// session검사
		HttpSession session = request.getSession(false);
		if("/".equals(requestURI)){
			if(session!=null && session.getAttribute("SESSION_MAP")!= null){	
				response.sendRedirect(request.getContextPath()+"/order/mappingList.do");
				return false;
			}else{
				return true;
			}
		}
		
		// 세션이 만료되었으면 로그인페이지로 이동.
		// ajax인 경우 에러코드보냄 
		if (session == null||session.getAttribute("SESSION_MAP")==null){
			if("do".equals(ext)){
				response.sendRedirect(request.getContextPath()+"/");
				return false;
			}
		}
		return true;
		
	}
	
	//controller 처리 이후 이벤트 작동
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		//log.info("================================ controller END ================================");
	}
	
	//view 처리 이후 이벤트 작동
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		//logger.info("================================ view END ================================");
	}
}
