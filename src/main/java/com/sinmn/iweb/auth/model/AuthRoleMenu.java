package com.sinmn.iweb.auth.model;

import java.util.ArrayList;
import java.util.List;

import com.sinmn.core.model.annotation.Column;
import com.sinmn.core.model.annotation.Table;
import com.sinmn.core.utils.vo.BaseBean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Table(value="auth_role_menu",create=true,comment="角色菜单")
@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(value = "角色菜单",description="角色菜单")
public class AuthRoleMenu extends BaseBean{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 角色id */
	public static final String ROLE_ID = "role_id";
	/** 菜单id */
	public static final String MENU_ID = "menu_id";
	/** 资源，前后都有逗号 */
	public static final String RESOURCES = "resources";

	@SuppressWarnings("serial")
	public static List<AuthRoleMenu> init(){
		return new ArrayList<AuthRoleMenu>(){{
		}};
	}
	
    	/** 角色id */
	@Column(name = "role_id",jdbcType="bigint(20)",notNull=true,def="0",comment="角色id")
	@ApiModelProperty("角色id")
	private Long roleId;
    
    	/** 菜单id */
	@Column(name = "menu_id",jdbcType="bigint(20)",notNull=true,def="0",comment="菜单id")
	@ApiModelProperty("菜单id")
	private Long menuId;
    
    	/** 资源，前后都有逗号 */
	@Column(name = "resources",jdbcType="varchar(500)",notNull=true,def="''",comment="资源，前后都有逗号")
	@ApiModelProperty("资源，前后都有逗号")
	private String resources;
    
}
