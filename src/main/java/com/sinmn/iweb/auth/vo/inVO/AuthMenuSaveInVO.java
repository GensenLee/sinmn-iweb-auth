package com.sinmn.iweb.auth.vo.inVO;

import java.util.List;

import com.sinmn.iweb.auth.model.AuthMenu;

import lombok.Data;

@Data
public class AuthMenuSaveInVO extends AuthMenu{

	private List<AuthMenuSaveInVO> children;

}
