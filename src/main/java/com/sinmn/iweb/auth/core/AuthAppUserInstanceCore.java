package com.sinmn.iweb.auth.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sinmn.core.utils.exception.CommonException;
import com.sinmn.iweb.auth.model.AuthAppUserInstance;
import com.sinmn.iweb.auth.redis.IWebAuthRedisDao;
import com.sinmn.iweb.auth.vo.innerVO.UserInfoInnerVO;

@Component
public class AuthAppUserInstanceCore {

	@Autowired
	private IWebAuthRedisDao iWebAuthRedisDao;
	
	/**
	 * @Description 跳到具体的某个页面
	 * @author xhz
	 * @date 2018年9月18日 下午4:00:00
	 * @param authAppUserInstance
	 * @param req
	 * @param res
	 * @throws CommonException
	 * @lastModifier
	 */
	public void toInstance(AuthAppUserInstance authAppUserInstance,UserInfoInnerVO userInfoInnerVO) {
		userInfoInnerVO.setUserInstanceId(authAppUserInstance.getId());
		iWebAuthRedisDao.set(userInfoInnerVO);
	}
}
