package com.poscodx.jblog.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

import com.poscodx.jblog.vo.UserVo;

public class AdminInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo) session.getAttribute("authUser");

		if (authUser == null) {
			response.sendRedirect("/jblog03/user/login");
			return false;
		}

		String currentUserId = authUser.getId();
		String uri = request.getRequestURI();
		String[] uriParts = uri.split("/");
		String targetUserId = null;
		if (uriParts.length >= 2) {
			targetUserId = uriParts[2];
		}

		if (targetUserId == null || !currentUserId.equals(targetUserId)) {
		    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		    return false;
		}

		return true;
	}
}
