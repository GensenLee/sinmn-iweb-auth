package com.sinmn.iweb.auth.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinmn.core.model.dto.ModelWhere;
import com.sinmn.core.utils.exception.CommonException;
import com.sinmn.core.utils.util.BeanUtil;
import com.sinmn.core.utils.util.LongUtil;
import com.sinmn.core.utils.verify.VerifyUtil;
import com.sinmn.core.utils.vo.PageResult;
import com.sinmn.iweb.auth.model.AuthApp;
import com.sinmn.iweb.auth.model.AuthAppInstance;
import com.sinmn.iweb.auth.model.AuthAppUserInstance;
import com.sinmn.iweb.auth.model.AuthCompany;
import com.sinmn.iweb.auth.model.AuthUser;
import com.sinmn.iweb.auth.repository.AuthAppInstanceRepository;
import com.sinmn.iweb.auth.repository.AuthAppRepository;
import com.sinmn.iweb.auth.repository.AuthAppUserInstanceRepository;
import com.sinmn.iweb.auth.repository.AuthCompanyRepository;
import com.sinmn.iweb.auth.repository.AuthUserRepository;
import com.sinmn.iweb.auth.util.PasswdUtil;
import com.sinmn.iweb.auth.vo.inVO.AuthCompanyInVO;
import com.sinmn.iweb.auth.vo.innerVO.UserInfoInnerVO;
import com.sinmn.iweb.auth.vo.searchVO.AuthCompanySearchVO;

@Service
public class AuthCompanyService {

	
	@Autowired
	private AuthCompanyRepository authCompanyRepository;
	
	@Autowired
	private AuthUserRepository authUserRepository;
	
	@Autowired
	private AuthAppRepository authAppRepository;
	
	@Autowired
	private AuthAppInstanceRepository authAppInstanceRepository;
	
	@Autowired
	private AuthAppUserInstanceRepository authAppUserInstanceRepository;
	
	public Object list(AuthCompanySearchVO svo) throws CommonException{
		
		ModelWhere mw = new ModelWhere();
		
		
		List<AuthCompany> liAuthCompany = authCompanyRepository.where(mw)
				.orderBy(AuthCompany.ID,"DESC")
				.limit(svo.getStart(),svo.getSize()).list();
		
		PageResult pr = new PageResult();
		pr.setList(liAuthCompany);
		pr.setCount(authCompanyRepository.where(mw).count());
		
		return pr;
	}
	
	@Transactional
	public Object save(AuthCompanyInVO authCompanyInVO,UserInfoInnerVO userInfoInnerVO) throws CommonException{
		
		if(LongUtil.isZero(authCompanyInVO.getId())){
			VerifyUtil.verify(authCompanyInVO,"appId",AuthCompany.NAME,AuthCompany.SHORT_NAME,AuthCompany.ACCOUNT);
			if(authCompanyRepository.where(AuthCompany.ACCOUNT,authCompanyInVO.getAccount()).isExists()){
				throw new CommonException("该账号已经存在");
			}
			BeanUtil.initCreate(authCompanyInVO, userInfoInnerVO.getUserName());
			authCompanyRepository.save(authCompanyInVO);
			
			//创建一个账号
			AuthUser authUser = new AuthUser();
			BeanUtil.initCreate(authUser, userInfoInnerVO.getUserName());
			authUser.setCompanyId(authCompanyInVO.getId());
			authUser.setIsAdmin(1);
			authUser.setOrgAccount(authCompanyInVO.getAccount());
			authUser.setAccount(authCompanyInVO.getAccount());
			authUser.setName(authCompanyInVO.getShortName()+"管理员");
			
			authUser.setPasswdSuffix(UUID.randomUUID().toString().replaceAll("-", ""));
			authUser.setPasswd(PasswdUtil.getPasswd("123456", authUser.getPasswdSuffix()));
			authUserRepository.save(authUser);
			
			AuthApp authApp = authAppRepository.get(authCompanyInVO.getAppId());
			
			AuthAppInstance authAppInstance = new AuthAppInstance();
			authAppInstance.setAppId(authCompanyInVO.getAppId());
			authAppInstance.setCompanyId(authCompanyInVO.getId());
			authAppInstance.setName(authApp.getCnName()+"-"+authCompanyInVO.getName());
			BeanUtil.initCreate(authAppInstance, userInfoInnerVO.getUserName());
			authAppInstanceRepository.save(authAppInstance);
			
			AuthAppUserInstance authAppUserInstance = new AuthAppUserInstance();
			authAppUserInstance.setAppId(authCompanyInVO.getAppId());
			authAppUserInstance.setAppInstanceId(authAppInstance.getId());
			authAppUserInstance.setCompanyId(authCompanyInVO.getId());
			authAppUserInstance.setUserId(authUser.getId());
			authAppUserInstance.setIsAdmin(1);
			BeanUtil.initCreate(authAppUserInstance, userInfoInnerVO.getUserName());
			authAppUserInstanceRepository.save(authAppUserInstance);
			
		}else{
		}
		
		return null;
		
	}
	
}
