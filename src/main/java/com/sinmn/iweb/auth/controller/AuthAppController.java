package com.sinmn.iweb.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sinmn.core.utils.exception.CommonException;
import com.sinmn.core.utils.vo.ApiResult;
import com.sinmn.iweb.auth.context.AuthContext;
import com.sinmn.iweb.auth.model.AuthApp;
import com.sinmn.iweb.auth.resource.AuthResource;
import com.sinmn.iweb.auth.service.AuthAppService;
import com.sinmn.iweb.auth.vo.searchVO.AuthAppSearchVO;

@RestController
@AuthResource(appId = 1)
public class AuthAppController {

	@Autowired
	private AuthAppService authAppService;
	
	@RequestMapping(path ="/admin/auth/instance/authApp/list.do",method = {RequestMethod.POST})
	@AuthResource(parent="系统列表",name="列表搜索")
	public ApiResult<Object> list(@RequestBody AuthAppSearchVO svo)
			throws CommonException
	{
		return ApiResult.getSuccess(authAppService.list(svo));
	}
	
	
	@RequestMapping(path ="/admin/auth/instance/authApp/save.do",method = {RequestMethod.POST})
	@AuthResource(parent="系统列表",name="新增保存",type=AuthResource.BUTTON)
	public ApiResult<Object> save(@RequestBody AuthApp authApp)
			throws CommonException
	{
		return ApiResult.getSuccess(authAppService.save(authApp,AuthContext.getUserInfoInnerVO()));
	}
	
	@RequestMapping(path ="/admin/auth/instance/authApp/del.do",method = {RequestMethod.POST})
	@AuthResource(parent="系统列表",name="删除",type=AuthResource.BUTTON)
	public ApiResult<Object> del(String ids)
			throws CommonException
	{
		return ApiResult.getSuccess(authAppService.del(ids));
	}
}
