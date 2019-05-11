package com.sinmn.iweb.auth.vo.searchVO;

import com.sinmn.core.utils.vo.BaseSearchVO;
import com.sinmn.iweb.auth.vo.inVO.AuthUserRepasswdInVO;

import lombok.Data;

@Data
public class AuthAppInstanceSearchVO  extends BaseSearchVO{

	private Long appId;
}
