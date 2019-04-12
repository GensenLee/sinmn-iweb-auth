package com.sinmn.iweb.auth.vo.inVO;

import java.util.ArrayList;
import java.util.List;

import com.sinmn.iweb.auth.model.AuthResources;

import lombok.Data;

@Data
public class AuthResourcesSaveInVO extends AuthResources{

	private List<AuthResourcesSaveInVO> children;

	public List<AuthResourcesSaveInVO> getChildren() {
		return children;
	}

	public void setChildren(List<AuthResourcesSaveInVO> children) {
		this.children = children;
	}
	
	public void addChildren(AuthResourcesSaveInVO child) {
		if(children == null){
			children = new ArrayList<AuthResourcesSaveInVO>();
		}
		children.add(child);
	}
	
	
	
	
}
