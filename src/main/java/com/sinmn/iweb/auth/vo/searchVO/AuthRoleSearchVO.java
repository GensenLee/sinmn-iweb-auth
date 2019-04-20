package com.sinmn.iweb.auth.vo.searchVO;

import com.sinmn.core.utils.vo.BaseSearchVO;

import lombok.Data;

@Data
public class AuthRoleSearchVO extends BaseSearchVO{

	private Long appId;
	
	private Long appInstanceId;
	
	private Long companyId;
}
