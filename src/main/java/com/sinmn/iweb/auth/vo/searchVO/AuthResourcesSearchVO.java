package com.sinmn.iweb.auth.vo.searchVO;

import com.sinmn.core.utils.verify.VerifyField;
import com.sinmn.core.utils.vo.BaseSearchVO;

import lombok.Data;

@Data
public class AuthResourcesSearchVO  extends BaseSearchVO{

	@VerifyField("系统id")
	private Long appId;
	
	@VerifyField("实例id")
	private Long appInstanceId;
	
	private Integer type;
}
