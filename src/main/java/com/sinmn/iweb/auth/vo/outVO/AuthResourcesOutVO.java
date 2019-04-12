package com.sinmn.iweb.auth.vo.outVO;

import java.util.ArrayList;
import java.util.List;

import com.sinmn.iweb.auth.model.AuthResources;
import com.sinmn.iweb.auth.vo.inVO.AuthUserRepasswdInVO;

import lombok.Data;

@Data
public class AuthResourcesOutVO extends AuthResources{

	private List<AuthResourcesOutVO> children;

	public List<AuthResourcesOutVO> getChildren() {
		return children;
	}

	public void setChildren(List<AuthResourcesOutVO> children) {
		this.children = children;
	}
	

	public void addChildren(AuthResourcesOutVO child) {
		if(children == null){
			children = new ArrayList<AuthResourcesOutVO>();
		}
		children.add(child);
	}
}
