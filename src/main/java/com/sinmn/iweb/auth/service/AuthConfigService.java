package com.sinmn.iweb.auth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinmn.core.model.dto.ModelWhere;
import com.sinmn.core.utils.exception.CommonException;
import com.sinmn.core.utils.util.BeanUtil;
import com.sinmn.core.utils.util.LongUtil;
import com.sinmn.core.utils.verify.VerifyUtil;
import com.sinmn.core.utils.vo.PageResult;
import com.sinmn.iweb.auth.context.AuthContext;
import com.sinmn.iweb.auth.model.AuthKeyValue;
import com.sinmn.iweb.auth.repository.AuthKeyValueRepository;
import com.sinmn.iweb.auth.vo.innerVO.UserInfoInnerVO;
import com.sinmn.iweb.auth.vo.searchVO.AuthConfigSearchVO;

@Service
public class AuthConfigService {

	@Autowired
	private AuthKeyValueRepository authKeyValueRepository;
	
	public Object list(AuthConfigSearchVO svo) throws CommonException{
		
		ModelWhere mw = new ModelWhere();

		List<AuthKeyValue> liAuthKeyValue = authKeyValueRepository.where(mw).limit(svo.getStart(),svo.getSize()).list();
		
		PageResult pr = new PageResult();
		pr.setList(liAuthKeyValue);
		pr.setCount(authKeyValueRepository.where(mw).count());
		
		return pr;
	}
	
	public Object save(AuthKeyValue authKeyValue,UserInfoInnerVO userInfoInnerVO) throws CommonException{
		VerifyUtil.verify(authKeyValue,AuthKeyValue.KEY,AuthKeyValue.VALUE,AuthKeyValue.ID);
		if(LongUtil.isZero(authKeyValue.getId())){
			BeanUtil.initCreate(authKeyValue, userInfoInnerVO.getUserName());
		}else{
			BeanUtil.initModify(authKeyValue, userInfoInnerVO.getUserName());
		}
		return authKeyValueRepository.save(authKeyValue);
	}
}
