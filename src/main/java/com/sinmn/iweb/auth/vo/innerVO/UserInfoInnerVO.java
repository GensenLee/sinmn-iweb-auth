package com.sinmn.iweb.auth.vo.innerVO;

import com.sinmn.core.utils.vo.BaseBean;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UserInfoInnerVO extends BaseBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String sessionKey;
	
	private Long userId;
	
	private Long companyId;
	
	private Long userInstanceId;
	
	private String userName;

}
