package com.pm.rc.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// 26. 화면 이동의 정보를 출력해 줌
public class AccessLogFilter implements Filter {
	
	private Logger logger = LoggerFactory.getLogger(AccessLogFilter.class);

	// 초기화
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}
	
	// destroy
	@Override
	public void destroy() {

	}

	// 서비스
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest)req;
		
		//접속지의 주소 가지고 옴.
		String remoteAddr = StringUtils.defaultString(request.getRemoteAddr());
		
		//URI 실질적으로 요청된 주소
		String uri = StringUtils.defaultIfEmpty(request.getRequestURI(), "");
		
		//URL
		String url = (request.getRequestURL() == null) ? "" : request.getRequestURL().toString();
		
		//Query
		String queryString = StringUtils.defaultString(request.getQueryString(), "");
		
		//Header 정보를 가지고 오면 웹의 사용환경을 파악하여 그에 맞게 변경해준다.
		//refer
		String refer = StringUtils.defaultString(request.getHeader("Refer"), "");
		
		//agent => browser 정보 크롬인지 모질라인지 등
		String agent = StringUtils.defaultString(request.getHeader("User-Agent"), "");
		
		String fullUrl = url;
		fullUrl += StringUtils.isNotEmpty(queryString) ? "?"+queryString : queryString;
		
		StringBuilder result = new StringBuilder();
		result.append(":")
		.append(remoteAddr)
		.append(":")
		.append(refer)
		.append(":")
		.append(agent);
		
		logger.info("[LOG FILTER]"+result.toString());
		chain.doFilter(req, resp);
		
	}

}

