package com.sinmn.iweb.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sinmn.core.utils.constant.ApiResultCode;
import com.sinmn.core.utils.exception.CommonException;
import com.sinmn.core.utils.util.LongUtil;
import com.sinmn.core.utils.vo.ApiResult;
import com.sinmn.iweb.auth.context.AuthContext;
import com.sinmn.iweb.auth.resource.AuthResource;
import com.sinmn.iweb.auth.service.AuthAppUserInstanceService;
import com.sinmn.iweb.auth.vo.inVO.AuthAppUserInstanceSaveInVO;
import com.sinmn.iweb.auth.vo.inVO.AuthToInstanceInVO;
import com.sinmn.iweb.auth.vo.searchVO.AuthAppUserInstanceSearchVO;

@RestController
@AuthResource(appId = 1)
public class AuthAppUserInstanceController {

	@Autowired
	private AuthAppUserInstanceService authAppUserInstanceService;
	
	@RequestMapping(path ="/admin/auth/user/authAppUserInstance/list.do")
	@AuthResource(parent="用户实例",name="列表搜索")
	public ApiResult<Object> list()
			throws CommonException
	{
		return ApiResult.getSuccess(authAppUserInstanceService.list(AuthContext.getUserInfoInnerVO()));
	}
	
	@RequestMapping(path ="/admin/auth/user/authAppUserInstance/listForPage.do")
	@AuthResource(parent="用户实例",name="列表搜索_页面")
	public ApiResult<Object> listForPage(@RequestBody AuthAppUserInstanceSearchVO svo)
			throws CommonException
	{
		if(LongUtil.isZero(svo.getUserId())){
			throw new CommonException("缺少参数user_id");
		}
		return ApiResult.getSuccess(authAppUserInstanceService.listForPage(svo,AuthContext.getUserInfoInnerVO()));
	}
	
	@RequestMapping(path ="/admin/auth/user/authAppUserInstance/toInstance.do")
	public ApiResult<Object> toInstance(@RequestBody AuthToInstanceInVO authToInstanceInVO)
			throws CommonException
	{
		return ApiResult.getSuccess(authAppUserInstanceService.toInstance(authToInstanceInVO,AuthContext.getUserInfoInnerVO()));
	}
	
	@RequestMapping(path ="/admin/auth/instance/authAppUserInstance/save.do",method = {RequestMethod.POST})
	@AuthResource(parent="用户实例",name="新增保存",type=AuthResource.BUTTON)
	public ApiResult<Object> save(@RequestBody AuthAppUserInstanceSaveInVO authAppUserInstanceSaveInVO)
			throws CommonException
	{
		return ApiResult.getSuccess(authAppUserInstanceService.save(authAppUserInstanceSaveInVO,AuthContext.getUserInfoInnerVO()));
	}
	
	@RequestMapping(path ="/admin/auth/instance/authAppUserInstance/saveByRole.do",method = {RequestMethod.POST})
	@AuthResource(parent="用户实例",name="根据角色保存",type=AuthResource.BUTTON)
	public ApiResult<Object> saveByRole(@RequestBody AuthAppUserInstanceSaveInVO authAppUserInstanceSaveInVO)
			throws CommonException
	{
		return ApiResult.getSuccess(authAppUserInstanceService.saveByRole(authAppUserInstanceSaveInVO,AuthContext.getUserInfoInnerVO()));
	}
	
	@RequestMapping(path ="/admin/auth/instance/authAppUserInstance/get.do")
	public ApiResult<Object> get(Long id)
			throws CommonException
	{
		return ApiResult.getSuccess(authAppUserInstanceService.get(id,AuthContext.getUserInfoInnerVO()));
	}
	
	@RequestMapping(path ="/admin/auth/instance/authAppUserInstance/delete.do")
	@AuthResource(parent="用户实例",name="删除",type=AuthResource.BUTTON)
	public ApiResult<Object> delete(String ids)
			throws CommonException
	{
		return ApiResult.getSuccess(authAppUserInstanceService.delete(ids,AuthContext.getUserInfoInnerVO()));
	}
}
