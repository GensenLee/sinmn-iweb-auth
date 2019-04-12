package com.sinmn.iweb.auth.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinmn.core.model.dto.ModelWhere;
import com.sinmn.core.utils.exception.CommonException;
import com.sinmn.core.utils.util.BeanUtil;
import com.sinmn.core.utils.util.LongUtil;
import com.sinmn.core.utils.verify.VerifyUtil;
import com.sinmn.core.utils.vo.PageResult;
import com.sinmn.iweb.auth.model.AuthApp;
import com.sinmn.iweb.auth.model.AuthAppInstance;
import com.sinmn.iweb.auth.repository.AuthAppInstanceRepository;
import com.sinmn.iweb.auth.repository.AuthAppRepository;
import com.sinmn.iweb.auth.vo.innerVO.UserInfoInnerVO;
import com.sinmn.iweb.auth.vo.searchVO.AuthAppInstanceSearchVO;

@Service
public class AuthAppInstanceService {

	
	@Autowired
	private AuthAppInstanceRepository authAppInstanceRepository;
	
	@Autowired
	private AuthAppRepository authAppRepository;
	
	public Object list(AuthAppInstanceSearchVO svo,UserInfoInnerVO userInfoInnerVO) throws CommonException{
		
		ModelWhere mw = new ModelWhere();
		mw.add(AuthAppInstance.COMPANY_ID, userInfoInnerVO.getCompanyId());
		List<AuthAppInstance> liAuthAppInstance = authAppInstanceRepository
				.where(mw)
				.limit(svo.getStart(),svo.getSize())
				.orderBy(AuthAppInstance.ID,"DESC")
				.list();
		List<Map> mapList = authAppRepository.join(liAuthAppInstance, AuthAppInstance.APP_ID, AuthApp.ID,
				AuthApp.CN_NAME,"appName",AuthApp.TYPE,AuthApp.TYPE).list(Map.class);
		PageResult pr = PageResult.get();
		pr.setList(mapList);
		pr.setCount(authAppInstanceRepository.where(mw).count());
		
		return pr;
	}
	
	public Object save(AuthAppInstance authAppInstance,UserInfoInnerVO userInfoInnerVO) throws CommonException{
		VerifyUtil.verify(authAppInstance);
		authAppInstance.setCompanyId(userInfoInnerVO.getCompanyId());
		if(LongUtil.isZero(authAppInstance.getId())){
			BeanUtil.initCreate(authAppInstance, userInfoInnerVO.getUserName());
		}else{
			BeanUtil.initModify(authAppInstance, userInfoInnerVO.getUserName());
		}
		return authAppInstanceRepository.save(authAppInstance);
	}
	
}
