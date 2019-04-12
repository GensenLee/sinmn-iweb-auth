package com.sinmn.iweb.auth.vo.inVO;

import com.sinmn.core.utils.verify.VerifyField;
import com.sinmn.core.utils.vo.BaseBean;

import lombok.Data;

@Data
public class AuthLoginInVO extends BaseBean{

	@VerifyField(value = "账号")
	private String account;
	
	@VerifyField(value = "密码")
	private String passwd;
	
}
