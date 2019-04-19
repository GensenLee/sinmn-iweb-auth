package com.sinmn.iweb.auth.vo.inVO;

import java.util.List;

import com.sinmn.iweb.auth.model.AuthRole;
import com.sinmn.iweb.auth.model.AuthRoleMenu;

import lombok.Data;

@Data
public class AuthRoleSaveInVO extends AuthRole{

	private List<AuthRoleMenu> menus;

}
