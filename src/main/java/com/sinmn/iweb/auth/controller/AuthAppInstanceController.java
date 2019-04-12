package com.sinmn.iweb.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sinmn.core.utils.exception.CommonException;
import com.sinmn.core.utils.vo.ApiResult;
import com.sinmn.iweb.auth.context.AuthContext;
import com.sinmn.iweb.auth.model.AuthAppInstance;
import com.sinmn.iweb.auth.resource.AuthResource;
import com.sinmn.iweb.auth.service.AuthAppInstanceService;
import com.sinmn.iweb.auth.vo.searchVO.AuthAppInstanceSearchVO;

@RestController
@AuthResource(appId = 1)
public class AuthAppInstanceController {

	@Autowired
	private AuthAppInstanceService authAppInstanceService;
	
	@RequestMapping(path ="/admin/auth/instance/authAppInstance/list.do",method = {RequestMethod.POST})
	@AuthResource(parent="系统实例",name="列表搜索")
	public ApiResult<Object> list(@RequestBody AuthAppInstanceSearchVO svo)
			throws CommonException
	{
		return ApiResult.getSuccess(authAppInstanceService.list(svo,AuthContext.getUserInfoInnerVO()));
	}
	
	@RequestMapping(path ="/admin/auth/instance/authAppInstance/save.do",method = {RequestMethod.POST})
	@AuthResource(parent="系统实例",name="新增保存",type=AuthResource.BUTTON)
	public ApiResult<Object> save(@RequestBody AuthAppInstance authAppInstance)
			throws CommonException
	{
		return ApiResult.getSuccess(authAppInstanceService.save(authAppInstance,AuthContext.getUserInfoInnerVO()));
	}
}
