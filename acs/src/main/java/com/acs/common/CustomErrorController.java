package com.acs.common;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@EnableAutoConfiguration
public class CustomErrorController implements ErrorController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String VIEW_PATH = "/common/";
	
	@RequestMapping(value = "/error")
   	public String handleError(HttpServletRequest request) {
   	//public ModelAndView error(HttpServletRequest request) throws Exception{
   		//ModelAndView mav = new ModelAndView();
   		
   		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

   		if (status != null) {
   			int statusCode = Integer.valueOf(status.toString());
   			if (statusCode == HttpStatus.NOT_FOUND.value()) {
   				return VIEW_PATH + "code404";
   				
   				//mav.setViewName(VIEW_PATH + "code404");
   				//return mav;
   		        
   			}
   			if (statusCode == HttpStatus.FORBIDDEN.value()) {
   				return VIEW_PATH + "code500";
   				
   				//mav.setViewName(VIEW_PATH + "code500");
   				//return mav;
   		        
   			}
   		}
   		
   		//mav.setViewName("error");
   		//return mav;
           
   		return "error";
   	}
	
	public String getErrorPath() {
        return null;
    }

	
}
