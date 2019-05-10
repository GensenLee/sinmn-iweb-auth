package com.sinmn.iweb.auth.model;

import java.util.ArrayList;
import java.util.List;

import com.sinmn.core.model.annotation.Column;
import com.sinmn.core.model.annotation.Table;
import com.sinmn.core.utils.verify.VerifyField;
import com.sinmn.core.utils.vo.BaseBean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;

@Table(value="auth_role",create=true,comment="用户角色")
@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(value = "用户角色",description="用户角色")
public class AuthRole extends BaseBean{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 流水号 */
	public static final String ID = "id";
	/** 应用id */
	public static final String APP_ID = "app_id";
	/** 集团id */
	public static final String COMPANY_ID = "company_id";
	/** 角色名字 */
	public static final String NAME = "name";
	/** 角色编码 */
	public static final String CODE = "code";
	/** 扩展字段 */
	public static final String EXT = "ext";
	/** 系统管理员，默认拥有所有的菜单 */
	public static final String IS_ADMIN = "is_admin";
	/** 是否能被修改 0否 1是 */
	public static final String IS_EDIT = "is_edit";
	/** 删除标志位 0未删除 1删除 */
	public static final String DEL_FLAG = "del_flag";
	/** 创建人 */
	public static final String CREATE_NAME = "create_name";
	/** 创建时间 */
	public static final String CREATE_TIME = "create_time";
	/** 最后修改人 */
	public static final String MODIFY_NAME = "modify_name";
	/** 最后修改时间 */
	public static final String MODIFY_TIME = "modify_time";

	@SuppressWarnings("serial")
	public static List<AuthRole> init(){
		return new ArrayList<AuthRole>(){{
		}};
	}
	
    	/** 流水号 */
	@Column(name = "id",jdbcType="bigint(20)",priKey=true,autoIncrement=true, comment="流水号")
	@ApiModelProperty("流水号")
	private Long id;
    
    	/** 应用id */
	@Column(name = "app_id",jdbcType="bigint(20)",notNull=true,def="0",comment="应用id")
	@ApiModelProperty("应用id")
	private Long appId;
    
    	/** 集团id */
	@Column(name = "company_id",jdbcType="bigint(20)",notNull=true,def="0",comment="集团id")
	@ApiModelProperty("集团id")
	private Long companyId;
    
    	/** 角色名字 */
	@Column(name = "name",jdbcType="varchar(100)",notNull=true,def="''",comment="角色名字")
	@ApiModelProperty("角色名字")
	private String name;
    
    	/** 角色编码 */
	@Column(name = "code",jdbcType="varchar(100)",notNull=true,def="''",comment="角色编码")
	@ApiModelProperty("角色编码")
	private String code;
    
    	/** 系统管理员，默认拥有所有的菜单 */
	@Column(name = "is_admin",jdbcType="tinyint(3)",notNull=true,def="0",comment="系统管理员，默认拥有所有的菜单")
	@ApiModelProperty("系统管理员，默认拥有所有的菜单")
	private Byte isAdmin;
    
    	/** 是否能被修改 0否 1是 */
	@Column(name = "is_edit",jdbcType="tinyint(3)",notNull=true,def="1",comment="是否能被修改 0否 1是")
	@ApiModelProperty("是否能被修改 0否 1是")
	private Byte isEdit;
    
    	/** 删除标志位 0未删除 1删除 */
	@Column(name = "del_flag",jdbcType="tinyint(3)",notNull=true,def="0",comment="删除标志位 0未删除 1删除")
	@ApiModelProperty("删除标志位 0未删除 1删除")
	private Byte delFlag;
	
	@Column(name = "ext",jdbcType="varchar(2000)",notNull=true,def="''",comment="扩展字段")
	@VerifyField(value = "扩展字段",ignore = true)
	private String ext;
    
    	/** 创建人 */
	@Column(name = "create_name",jdbcType="varchar(50)",notNull=true,def="''",comment="创建人")
	@ApiModelProperty("创建人")
	private String createName;
    
    	/** 创建时间 */
	@Column(name = "create_time",jdbcType="datetime",notNull=true,def="",comment="创建时间")
	@ApiModelProperty("创建时间")
	private Date createTime;
    
    	/** 最后修改人 */
	@Column(name = "modify_name",jdbcType="varchar(50)",notNull=true,def="''",comment="最后修改人")
	@ApiModelProperty("最后修改人")
	private String modifyName;
    
    	/** 最后修改时间 */
	@Column(name = "modify_time",jdbcType="datetime",notNull=true,def="",comment="最后修改时间")
	@ApiModelProperty("最后修改时间")
	private Date modifyTime;
    
}
