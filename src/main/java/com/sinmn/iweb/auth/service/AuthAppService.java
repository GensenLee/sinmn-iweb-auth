package com.sinmn.iweb.auth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinmn.core.model.dto.ModelWhere;
import com.sinmn.core.model.emun.ModelCondition;
import com.sinmn.core.model.emun.ModelOperator;
import com.sinmn.core.utils.exception.CommonException;
import com.sinmn.core.utils.util.BeanUtil;
import com.sinmn.core.utils.util.LongUtil;
import com.sinmn.core.utils.util.StringUtil;
import com.sinmn.core.utils.verify.VerifyUtil;
import com.sinmn.core.utils.vo.PageResult;
import com.sinmn.iweb.auth.model.AuthApp;
import com.sinmn.iweb.auth.repository.AuthAppRepository;
import com.sinmn.iweb.auth.vo.innerVO.UserInfoInnerVO;
import com.sinmn.iweb.auth.vo.searchVO.AuthAppSearchVO;

@Service
public class AuthAppService {

	
	@Autowired
	private AuthAppRepository authAppRepository;
	
	public Object list(AuthAppSearchVO svo) throws CommonException{
		
		ModelWhere mw = new ModelWhere();
		if(StringUtil.isNotEmpty(svo.getQuickSearch())){
			ModelWhere smw = new ModelWhere();
			smw.add(AuthApp.NAME,svo.getQuickSearch(),ModelOperator.LIKE);
			smw.add(AuthApp.CN_NAME,svo.getQuickSearch(),ModelOperator.LIKE,ModelCondition.OR);
			mw.add(smw);
		}
		if(svo.getType() != null){
			mw.add(AuthApp.TYPE,svo.getType());
		}
		
		List<AuthApp> liAuthApp = authAppRepository.where(mw)
				.orderBy(AuthApp.ID,"DESC")
				.limit(svo.getStart(),svo.getSize()).list();
		
		PageResult pr = new PageResult();
		pr.setList(liAuthApp);
		pr.setCount(authAppRepository.where(mw).count());
		
		return pr;
	}
	
	public Object save(AuthApp authApp,UserInfoInnerVO userInfoInnerVO) throws CommonException{
		VerifyUtil.verify(authApp);
		if(LongUtil.isZero(authApp.getId())){
			authApp.setType(1);
			BeanUtil.initCreate(authApp, userInfoInnerVO.getUserName());
		}else{
			BeanUtil.initModify(authApp, userInfoInnerVO.getUserName());
		}
		return authAppRepository.save(authApp);
	}
	
	public Object del(String ids) throws CommonException{
		return authAppRepository.where(AuthApp.ID,ids,ModelOperator.IN).delete();
	}
}
