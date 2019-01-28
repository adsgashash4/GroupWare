package com.pm.rc.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Ws_InitController {
	
	@RequestMapping(value = { "/rcAdmin.do" })
	public String init(Model model, HttpServletRequest req, HttpServletResponse res){
		String contextPath = req.getContextPath();
		System.out.println(req.getRequestURI());
		String url = null;
		String page = null;
		try {
			url = getPageUrl(req.getRequestURI());
			//System.out.println(url);
			page = req.getParameter("w2xPath");
		} catch (Exception e) {
			
		} finally {
			if (page == null) {
				page = contextPath + "/sysManager_ws5/sys_main.xml";
			}
			model.addAttribute("page", page);
		}
		return url;
	}
	
	private static String getPageUrl(String urlPath) {
		//System.out.println(urlPath);
		String view = "websquare";
		String[] urlArr = {"i18n","popup"};
		String url = urlPath.replaceAll("(.*websquare/)(.*)(.do|.html)", "$2");
		//System.out.println("url::"+url);
		for(int i=0;i<urlArr.length;i++){
			if(urlArr[i].equals(url)){
				view = url;
				break;
			}
		}
		return "/websquare/"+view;
	}
}
