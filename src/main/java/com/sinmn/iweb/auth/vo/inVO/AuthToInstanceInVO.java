package com.sinmn.iweb.auth.vo.inVO;

import com.sinmn.core.utils.verify.VerifyField;
import com.sinmn.core.utils.vo.BaseBean;

import lombok.Data;

@Data
public class AuthToInstanceInVO extends BaseBean{

	/**
	 * 目标实例id
	 */
	@VerifyField("用户系统实例ID")
	private Long userInstanceId;
}
