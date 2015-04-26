package cn.edu.hstc.foodserver.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class AdminSecurityInterceptor implements HandlerInterceptor {

	private static Logger logger = Logger.getLogger(AdminSecurityInterceptor.class);
	
	
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		HttpSession session =   request.getSession();
		Object adminName = session.getAttribute("adminName");
		if (adminName != null) {			
			return true;			
		}
		String path = request.getServletContext().getContextPath() + "/login";
		logger.info(path);
		response.sendRedirect(path);
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

		
	}

}
