package com.pm.rc.util;

import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class logExecute {
	
	public void before(JoinPoint j){
		Logger logger = LoggerFactory.getLogger(j.getTarget()+"");
		logger.debug("---시작---");
		Object[] args = j.getArgs();
		if(args!=null){
			logger.debug("Method명 :\t"+j.getSignature().getName());
			for (int i = 0; i < args.length; i++) {
				logger.debug("Argument : "+args[i]+"번째 값");
			}
			logger.debug("Method명 :\t"+j.getSignature().getName());
		}
	}
	
	public void afterReturning(JoinPoint j){
		Logger logger = LoggerFactory.getLogger(j.getTarget()+"");
		logger.debug("------끝------");
	}
	
	public void afterThrowing(JoinPoint j){
		Logger logger = LoggerFactory.getLogger(j.getTarget()+"//"+j.getTarget());
		logger.debug("------에러 :"+j.getArgs());
		logger.debug("------에러 :"+j.toString());
	}

}

