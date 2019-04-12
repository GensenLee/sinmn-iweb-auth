package com.sinmn.iweb.auth.vo.inVO;

import java.util.List;

import com.sinmn.iweb.auth.model.AuthAppUserInstance;
import com.sinmn.iweb.auth.model.AuthAppUserInstanceMenu;

import lombok.Data;

@Data
public class AuthAppUserInstanceSaveInVO extends AuthAppUserInstance{

	private List<AuthAppUserInstanceMenu> menus;

}
