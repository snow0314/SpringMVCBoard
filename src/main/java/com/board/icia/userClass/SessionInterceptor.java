package com.board.icia.userClass;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class SessionInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("preHandle Call!!!!");
		if(request.getSession().getAttribute("id")==null) {
			response.sendRedirect("./"); //home.jsp
			return false; //controller 진입 금지
		}
		
		return true; //controller 진입 가능
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("postHandle Call!!!!");
		super.postHandle(request, response, handler, modelAndView);
	}
	
}
