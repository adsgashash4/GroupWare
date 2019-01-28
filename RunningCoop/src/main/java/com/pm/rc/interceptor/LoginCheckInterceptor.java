package com.pm.rc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.pm.rc.dto.MemberDto;

public class LoginCheckInterceptor extends HandlerInterceptorAdapter {
	
		private static final Logger logger = LoggerFactory.getLogger(LoginCheckInterceptor.class);

		public void afterCompletion(HttpServletRequest req, HttpServletResponse resp, Object handler, Exception ex) throws Exception{
			super.afterCompletion(req, resp, handler, ex);
		}
		@Override
		public void afterConcurrentHandlingStarted(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception{
			super.afterConcurrentHandlingStarted(req, resp, handler);
		}
		
		public void postHandle(HttpServletRequest req, HttpServletResponse resp, Object handler, ModelAndView mav) throws Exception{
			logger.info("==================<<인터셉터 끝>>==================");
			super.postHandle(req, resp, handler, mav);
		}
		
		public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception{
			logger.info("==================<<인터셉터 시작>>==================");
			try{
				if(req.getSession().getAttribute("mem_id")==null){
					resp.sendRedirect("./main.do");
					return false;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return true;
		}
		
}
