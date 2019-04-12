package com.sinmn.iweb.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sinmn.core.utils.exception.CommonException;
import com.sinmn.core.utils.vo.ApiResult;
import com.sinmn.iweb.auth.context.AuthContext;
import com.sinmn.iweb.auth.resource.AuthResource;
import com.sinmn.iweb.auth.service.AuthLoginLogService;
import com.sinmn.iweb.auth.vo.searchVO.AuthLoginLogSearchVO;

@RestController
@AuthResource(appId = 1)
public class AuthLoginLogController {

	@Autowired
	private AuthLoginLogService authLoginLogService;
	
	@RequestMapping(path ="/admin/auth/instance/authLoginLog/list.do",method = {RequestMethod.POST})
	@AuthResource(parent="登录日志",name="列表搜索")
	public ApiResult<Object> list(@RequestBody AuthLoginLogSearchVO svo)
			throws CommonException
	{
		return ApiResult.getSuccess(authLoginLogService.list(svo,AuthContext.getUserInfoInnerVO()));
	}
}
