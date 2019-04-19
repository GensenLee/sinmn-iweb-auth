package com.sinmn.iweb.auth.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinmn.core.model.dto.ModelWhere;
import com.sinmn.core.utils.exception.CommonException;
import com.sinmn.core.utils.util.LongUtil;
import com.sinmn.core.utils.vo.PageResult;
import com.sinmn.iweb.auth.constant.AuthConstant;
import com.sinmn.iweb.auth.model.AuthLoginLog;
import com.sinmn.iweb.auth.model.AuthUser;
import com.sinmn.iweb.auth.repository.AuthLoginLogRepository;
import com.sinmn.iweb.auth.repository.AuthUserRepository;
import com.sinmn.iweb.auth.vo.innerVO.UserInfoInnerVO;
import com.sinmn.iweb.auth.vo.searchVO.AuthLoginLogSearchVO;

@Service
public class AuthLoginLogService {

	
	@Autowired
	private AuthLoginLogRepository authLoginLogRepository;
	
	@Autowired
	private AuthUserRepository authUserRepository;
	
	public Object list(AuthLoginLogSearchVO svo,UserInfoInnerVO userInfoInnerVO) throws CommonException{
		
		ModelWhere mw = new ModelWhere();
		
		if(LongUtil.isZero(svo.getCompanyId())){
			mw.add(AuthUser.COMPANY_ID,userInfoInnerVO.getCompanyId());
		}else if(LongUtil.toLong(svo.getCompanyId()) != AuthConstant.Common.ALL){
			mw.add(AuthUser.COMPANY_ID,svo.getCompanyId());
		}
		
		List<AuthLoginLog> authLoginLog = authLoginLogRepository.where(mw)
				.orderBy(AuthLoginLog.ID,"DESC")
				.limit(svo.getStart(),svo.getSize()).list();
		
		List<Map> listResult = authUserRepository.join(authLoginLog, AuthLoginLog.USER_ID, AuthUser.ID, AuthUser.NAME,"userName").list(Map.class);
		PageResult pr = new PageResult();
		pr.setList(listResult);
		pr.setCount(authLoginLogRepository.where(mw).count());
		
		return pr;
	}
	
}
