package com.sinmn.iweb.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sinmn.core.utils.exception.CommonException;
import com.sinmn.core.utils.util.LongUtil;
import com.sinmn.core.utils.vo.ApiResult;
import com.sinmn.iweb.auth.context.AuthContext;
import com.sinmn.iweb.auth.resource.AuthResource;
import com.sinmn.iweb.auth.service.AuthRoleService;
import com.sinmn.iweb.auth.vo.inVO.AuthRoleSaveInVO;
import com.sinmn.iweb.auth.vo.searchVO.AuthRoleSearchVO;

@RestController
@AuthResource(appId = 1)
public class AuthRoleController {

	@Autowired
	private AuthRoleService authRoleService;
	
	@RequestMapping(path ="/admin/auth/user/authRole/list.do")
	@AuthResource(parent="角色列表",name="列表搜索")
	public ApiResult<Object> list(@RequestBody AuthRoleSearchVO svo)
			throws CommonException
	{
		return ApiResult.getSuccess(authRoleService.list(svo,AuthContext.getUserInfoInnerVO()));
	}
	
	@RequestMapping(path ="/admin/auth/user/authRole/listForPage.do")
	@AuthResource(parent="角色列表",name="列表搜索_页面")
	public ApiResult<Object> listForPage(@RequestBody AuthRoleSearchVO svo)
			throws CommonException
	{
		return ApiResult.getSuccess(authRoleService.listForPage(svo,AuthContext.getUserInfoInnerVO()));
	}
	
	@RequestMapping(path ="/admin/auth/instance/authRole/save.do",method = {RequestMethod.POST})
	@AuthResource(parent="角色列表",name="修改",type=AuthResource.BUTTON)
	public ApiResult<Object> save(@RequestBody AuthRoleSaveInVO authAppUserInstanceSaveInVO)
			throws CommonException
	{
		return ApiResult.getSuccess(authRoleService.save(authAppUserInstanceSaveInVO,AuthContext.getUserInfoInnerVO()));
	}
	
	@RequestMapping(path ="/admin/auth/instance/authRole/singleSave.do",method = {RequestMethod.POST})
	@AuthResource(parent="角色列表",name="新增",type=AuthResource.BUTTON)
	public ApiResult<Object> singleSave(@RequestBody AuthRoleSaveInVO authAppUserInstanceSaveInVO)
			throws CommonException
	{
		return ApiResult.getSuccess(authRoleService.singleSave(authAppUserInstanceSaveInVO,AuthContext.getUserInfoInnerVO()));
	}
	
	@RequestMapping(path ="/admin/auth/instance/authRole/get.do")
	public ApiResult<Object> get(Long id)
			throws CommonException
	{
		return ApiResult.getSuccess(authRoleService.get(id,AuthContext.getUserInfoInnerVO()));
	}
	
	@RequestMapping(path ="/admin/auth/instance/authRole/delete.do")
	@AuthResource(parent="角色列表",name="删除",type=AuthResource.BUTTON)
	public ApiResult<Object> delete(String ids)
			throws CommonException
	{
		return ApiResult.getSuccess(authRoleService.delete(ids,AuthContext.getUserInfoInnerVO()));
	}
}
