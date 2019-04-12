package com.sinmn.iweb.auth.vo.inVO;

import com.sinmn.core.utils.verify.VerifyField;
import com.sinmn.core.utils.vo.BaseBean;

import lombok.Data;

@Data
public class AuthUserRepasswdInVO extends BaseBean{

	@VerifyField("原始密码")
	private String passwd;
	
	@VerifyField("确认密码")
	private String newPasswd;

	
}
