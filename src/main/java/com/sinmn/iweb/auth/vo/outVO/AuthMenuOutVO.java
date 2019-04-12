package com.sinmn.iweb.auth.vo.outVO;

import java.util.ArrayList;
import java.util.List;

import com.sinmn.iweb.auth.model.AuthMenu;
import com.sinmn.iweb.auth.vo.inVO.AuthUserRepasswdInVO;

import lombok.Data;

public class AuthMenuOutVO extends AuthMenu{

	private List<AuthMenuOutVO> children;

	public List<AuthMenuOutVO> getChildren() {
		return children;
	}

	public void setChildren(List<AuthMenuOutVO> children) {
		this.children = children;
	}
	

	public void addChildren(AuthMenuOutVO child) {
		if(children == null){
			children = new ArrayList<AuthMenuOutVO>();
		}
		children.add(child);
	}
	
	
	
	
}
