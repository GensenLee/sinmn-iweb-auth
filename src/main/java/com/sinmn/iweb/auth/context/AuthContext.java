package com.sinmn.iweb.auth.context;

import com.sinmn.iweb.auth.vo.innerVO.UserInfoInnerVO;

public class AuthContext {

	private static ThreadLocal<String> sessionKeyHandle = new ThreadLocal<String>();
	
	private static ThreadLocal<UserInfoInnerVO> userInfoInnerVOHandle = new ThreadLocal<UserInfoInnerVO>();
	
	public static String getSessionKey() {
		return sessionKeyHandle.get();
	}

	public static void setSessionKey(String sessionKey) {
		AuthContext.sessionKeyHandle.set(sessionKey);
	}
	
	public static UserInfoInnerVO getUserInfoInnerVO() {
		return userInfoInnerVOHandle.get();
	}

	public static void setUserInfoInnerVO(UserInfoInnerVO userInfoInnerVO) {
		AuthContext.userInfoInnerVOHandle.set(userInfoInnerVO);
	}

	public static void clear(){
		userInfoInnerVOHandle.remove();
		sessionKeyHandle.remove();
	}
	
}
