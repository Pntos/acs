package com.acs.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebFilter(urlPatterns = "/*")
public class XSSFilter implements Filter {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("init XSSFilter");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		 HttpServletRequest req = (HttpServletRequest) request; HttpServletResponse
		 res = (HttpServletResponse) response;
		 
		 /*
		 *
		 * res.setHeader("Access-Control-Allow-Origin", "*");
		 * res.setHeader("Access-Control-Allow-Methods", "GET,POST,DELETE,PUT,OPTIONS");
		 * res.setHeader("Access-Control-Allow-Headers", "*");
		 * res.setHeader("Access-Control-Allow-Credentials", true);
		 * res.setHeader("Access-Control-Max-Age", 180); chain.doFilter(req, res);
		 */

		
		/*
		 * HttpServletRequest req = (HttpServletRequest) request; HttpServletResponse
		 * res = (HttpServletResponse) response;
		 * 
		 * res.setHeader("Access-Control-Allow-Origin", "*");
		 * res.setHeader("Access-Control-Allow-Methods", "GET,POST,DELETE,PUT,OPTIONS");
		 * res.setHeader("Access-Control-Allow-Headers", "*");
		 * res.setHeader("Access-Control-Max-Age", "180");
		 */
		 
		// logger.info("##### filter - before #####"); 
		 
		 
		 chain.doFilter(req, res);
		 
		 //logger.info("##### filter - after #####");
		 
	}

	@Override
	public void destroy() {
		logger.info("destroy XSSFilter");
	}

}
