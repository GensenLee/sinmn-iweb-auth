package com.sinmn.iweb.auth.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sinmn.core.utils.util.IntUtil;
import com.sinmn.core.utils.util.StringUtil;
import com.sinmn.iweb.auth.vo.inVO.AuthUserResetInVO;
import com.sinmn.iweb.auth.vo.inVO.EmailInVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sinmn.core.utils.exception.CommonException;
import com.sinmn.core.utils.vo.ApiResult;
import com.sinmn.iweb.auth.context.AuthContext;
import com.sinmn.iweb.auth.model.AuthUser;
import com.sinmn.iweb.auth.resource.AuthResource;
import com.sinmn.iweb.auth.service.AuthUserService;
import com.sinmn.iweb.auth.vo.inVO.AuthLoginInVO;
import com.sinmn.iweb.auth.vo.inVO.AuthUserRepasswdInVO;
import com.sinmn.iweb.auth.vo.searchVO.AuthUserSearchVO;

import java.io.IOException;

@RestController
@AuthResource(appId = 1)
public class AuthUserController {



	@Autowired
	private AuthUserService authUserService;
	

	@RequestMapping(path ="/admin/auth/common/authUser/login.do",method = {RequestMethod.POST})
	public ApiResult<Object> login(@RequestBody AuthLoginInVO authLoginInVO,HttpServletRequest req)
			throws CommonException
	{
		return ApiResult.getSuccess("登录成功",authUserService.login(authLoginInVO,req));
	}
	
	@RequestMapping(path ="/admin/auth/instance/authUser/list.do",method = {RequestMethod.POST})
	@AuthResource(parent="用户列表",name="列表搜索")
	public ApiResult<Object> list(@RequestBody AuthUserSearchVO svo)
			throws CommonException
	{
		return ApiResult.getSuccess(authUserService.list(svo,AuthContext.getUserInfoInnerVO()));
	}
	
	@RequestMapping(path ="/admin/auth/instance/authUser/save.do",method = {RequestMethod.POST})
	@AuthResource(parent="用户列表",name="新增保存",type=AuthResource.BUTTON)
	public ApiResult<Object> save(@RequestBody AuthUser authUser)
			throws CommonException
	{
		return ApiResult.getSuccess(authUserService.save(authUser,AuthContext.getUserInfoInnerVO()));
	}
	
	@RequestMapping(path ="/admin/auth/instance/authApp/rePasswd.do",method = {RequestMethod.POST})
	public ApiResult<Object> rePasswd(@RequestBody AuthUserRepasswdInVO authUserRepasswdInVO)
			throws CommonException
	{
		return ApiResult.getSuccess(authUserService.rePasswd(authUserRepasswdInVO,AuthContext.getUserInfoInnerVO()));
	}
	
	@RequestMapping(path ="/admin/auth/instance/authUser/active.do",method = {RequestMethod.POST})
	@AuthResource(parent="用户列表",name="账号激活",type=AuthResource.BUTTON)
	public ApiResult<Object> active(@RequestBody AuthUser authUser)
			throws CommonException
	{
		return ApiResult.getSuccess(authUserService.active(authUser,AuthContext.getUserInfoInnerVO()));
	}

	@RequestMapping(path = "/admin/auth/auhtUser/sandResetMail.do",method = {RequestMethod.POST})
	public ApiResult sandResetEmail(@RequestBody EmailInVO email){
        if (StringUtil.isEmpty(email.getEmail())) {
            return ApiResult.getFailed("邮箱错误");
        }
        Object sandResetMail = authUserService.sandResetMail(email.getEmail());
        if (StringUtil.isEmpty(sandResetMail)) {
            return ApiResult.getSuccess("发送成功!");
        }
        return ApiResult.getFailed((String) sandResetMail);
    }

	@RequestMapping(path ="/reset/{userid}/{token1}/{token2}",method = {RequestMethod.GET} )
	public ApiResult<Object> emailResetUser(HttpServletResponse response, @PathVariable(name = "userid") String userId, @PathVariable(name = "token1") String resetToken1, @PathVariable(name = "token2") String resetToken2) throws IOException {
        if (StringUtil.isEmpty(userId)||StringUtil.isEmpty(resetToken1)||StringUtil.isEmpty(resetToken2)) {
            return ApiResult.getFailed("请求异常");
        }
        AuthUserResetInVO authUserResetInVO = new AuthUserResetInVO();
        authUserResetInVO.setUserId(userId);
        authUserResetInVO.setResetToken1(resetToken1);
        authUserResetInVO.setResetToken2(resetToken2);
        Object verifyResetReq = authUserService.verifyResetReq(authUserResetInVO);
        if (StringUtil.isNotEmpty(verifyResetReq)) {
            return ApiResult.getFailed((String)verifyResetReq);
        }
        String userResetToken = (String) authUserService.getUserResetToken(userId);

        response.sendRedirect("http://192.168.0.128:8080/resetPwd.html?k="+userResetToken+"&t1="+resetToken1+"&t2="+resetToken2);
        return ApiResult.getSuccess("发送成功");
    }

    @RequestMapping(path = "/admin/auth/auhtUser/reset.do",method = {RequestMethod.POST})
    public ApiResult resetPwd(@RequestBody AuthUserResetInVO vo){
        if (StringUtil.isEmpty(vo.getResetKey())) {
            return ApiResult.getFailed("请求异常");
        }
        Object verifyResetReq = authUserService.verifyResetReq2(vo);
        if (StringUtil.isNotEmpty(verifyResetReq)) {
            return ApiResult.getFailed((String)verifyResetReq);
        }
        return ApiResult.getSuccess((String)authUserService.updatePwd(vo));
    }


	
}
