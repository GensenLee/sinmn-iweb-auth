package com.sinmn.iweb.auth.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.sinmn.core.utils.interceptor.AbstractInterceptor;
import com.sinmn.iweb.auth.context.AuthContext;

public class GlobalAuthInterceptor extends AbstractInterceptor {

	private String[] excludeUrls;

	public String[] getExcludeUrls() {
		return excludeUrls;
	}

	public void setExcludeUrls(String[] excludeUrls) {
		this.excludeUrls = excludeUrls;
	}

	/**
	 * 不需要权限控制URL
	 * 
	 * @param uri
	 * @return
	 */
	private boolean exclude(String uri) {
		log.debug("************GlobalAuthInterceptor exclude excludeUrls:" + excludeUrls);
		if (excludeUrls != null) {
			for (String exc : excludeUrls) {
				log.debug("************GlobalAuthInterceptor exclude exc:" + exc);
				if (exc.equals(uri)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {
		AuthContext.clear();

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		log.debug("[{} preHandle] 进入拦截器",this.getClass().getSimpleName());
		AuthContext.clear();
		String uri = request.getRequestURI();

		if (exclude(uri)) {
			return true;
		}

		return true;
	}

}
