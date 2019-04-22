package com.sinmn.iweb.auth.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinmn.core.model.emun.ModelOperator;
import com.sinmn.iweb.auth.constant.AuthConstant;
import com.sinmn.iweb.auth.context.AuthContext;
import com.sinmn.iweb.auth.model.AuthAppInstance;
import com.sinmn.iweb.auth.model.AuthAppUserInstance;
import com.sinmn.iweb.auth.model.AuthKeyValue;
import com.sinmn.iweb.auth.repository.AuthAppInstanceRepository;
import com.sinmn.iweb.auth.repository.AuthAppUserInstanceRepository;
import com.sinmn.iweb.auth.repository.AuthKeyValueRepository;
import com.sinmn.iweb.auth.vo.innerVO.UserInfoInnerVO;

@Service
public class AuthCommonService {

	@Autowired
	private AuthKeyValueRepository authKeyValueRepository;
	
	@Autowired
	private AuthAppUserInstanceRepository authAppUserInstanceRepository;
	
	@Autowired
	private AuthAppInstanceRepository authAppInstanceRepository;
	
	public Object getForCommon(){
		Map<String,Object> result = new HashMap<String,Object>();
		String name = authKeyValueRepository
				.include(AuthKeyValue.VALUE)
				.where(AuthKeyValue.KEY,AuthConstant.KeyValueKey.SYSTEM_NAME)
				.getString();
		
		String copyright = authKeyValueRepository
				.include(AuthKeyValue.VALUE)
				.where(AuthKeyValue.KEY,AuthConstant.KeyValueKey.COPYRIGHT)
				.getString();
		
		result.put(AuthConstant.KeyValueKey.SYSTEM_NAME, name);
		result.put(AuthConstant.KeyValueKey.COPYRIGHT, copyright);
		return result;
	}
	
	public Object getForUser(){
		Map<String,Object> result = new HashMap<String,Object>();
		String name = authKeyValueRepository
				.include(AuthKeyValue.VALUE)
				.where(AuthKeyValue.KEY,AuthConstant.KeyValueKey.SYSTEM_NAME)
				.getString();
		result.put(AuthConstant.KeyValueKey.SYSTEM_NAME, name);
		return result;
	}
	
	public Object getForInstance(UserInfoInnerVO userInfoInnerVO){
		Map<String,Object> result = new HashMap<String,Object>();
		String name = authKeyValueRepository
				.include(AuthKeyValue.VALUE)
				.where(AuthKeyValue.KEY,AuthConstant.KeyValueKey.SYSTEM_NAME)
				.getString();
		result.put(AuthConstant.KeyValueKey.SYSTEM_NAME, name);
		result.put("instanceName", authAppInstanceRepository.include(AuthAppInstance.NAME)
				.where(AuthAppInstance.ID,
						authAppUserInstanceRepository.createSelect().fields(AuthAppUserInstance.APP_INSTANCE_ID).where(AuthAppUserInstance.ID,userInfoInnerVO.getUserInstanceId().intValue()).sql(ModelOperator.EQ)
				,ModelOperator.PLAIN)
				.getString());
		return result;
	}
}
