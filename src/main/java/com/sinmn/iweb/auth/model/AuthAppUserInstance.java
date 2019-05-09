package com.sinmn.iweb.auth.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sinmn.core.model.annotation.Column;
import com.sinmn.core.model.annotation.Table;
import com.sinmn.core.utils.verify.VerifyField;
import com.sinmn.core.utils.vo.BaseBean;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Table(value = "auth_app_user_instance",create=true,comment="用户应用实例")
@Data
@EqualsAndHashCode(callSuper=false)
public class AuthAppUserInstance extends BaseBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 流水号 */
	public static final String ID = "id";
	/** 应用id */
	public static final String APP_ID = "app_id";
	/** 应用实例id */
	public static final String APP_INSTANCE_ID = "app_instance_id";
	/** 集团id */
	public static final String COMPANY_ID = "company_id";
	/** 用户id */
	public static final String USER_ID = "user_id";
	/** 角色ID */
	public static final String ROLE_ID = "role_id";
	/** 系统管理员，默认拥有所有的菜单 */
	public static final String IS_ADMIN = "is_admin";
	/** 是否能被修改 */
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

	@Column(name = "id",jdbcType="bigint(20)",priKey=true,autoIncrement=true,comment="流水号")
	@VerifyField(ignore = true)
	private Long id;
	
	@Column(name = "app_id",jdbcType="bigint(20)",notNull=true,def="0",comment="应用id")
	private Long appId;
	
	@Column(name = "app_instance_id",jdbcType="bigint(20)",notNull=true,def="0",comment="应用实例id")
	@VerifyField("应用实例id")
	private Long appInstanceId;
	
	@Column(name = "company_id",jdbcType="bigint(20)",notNull=true,def="0",comment="集团id")
	private Long companyId;
	
	@Column(name = "user_id",jdbcType="bigint(20)",notNull=true,def="0",comment="用户id")
	@VerifyField("用户id")
	private Long userId;
	
	@Column(name = "role_id",jdbcType="bigint(20)",notNull=true,def="0",comment="角色id")
	@VerifyField("角色id")
	private Long roleId;
	
	@Column(name = "is_admin",jdbcType="tinyint(3)",notNull=true,def="0",comment="系统管理员，默认拥有所有的菜单")
	private Integer isAdmin;
	
	@Column(name = "is_edit",jdbcType="tinyint(3)",notNull=true,def="1",comment="是否能被修改 0否 1是")
	private Integer isEdit;
	
	@Column(name = "del_flag",jdbcType="tinyint(3)",notNull=true,def="0",comment="删除标志位 0未删除 1删除")
	private Integer delFlag;
	
	@Column(name = "ext",jdbcType="varchar(2000)",notNull=true,def="''",comment="扩展字段")
	@VerifyField(value = "扩展字段",ignore = true)
	private String ext;
	
	@Column(name = "create_name",jdbcType="varchar(50)",notNull=true,def="''",comment="创建人")
	@VerifyField(ignore = true)
	private String createName;
	
	@Column(name = "create_time",jdbcType="datetime",notNull=true,def="",comment="创建时间")
	@VerifyField(ignore = true)
	private Date createTime;
	
	@Column(name = "modify_name",jdbcType="varchar(50)",notNull=true,def="''",comment="最后修改人")
	@VerifyField(ignore = true)
	private String modifyName;
	
	@Column(name = "modify_time",jdbcType="datetime",notNull=true,def="",comment="最后修改时间")
	@VerifyField(ignore = true)
	private Date modifyTime;
	
	@SuppressWarnings("serial")
	public static List<AuthAppUserInstance> init(){
		final Date now = new Date();
		return new ArrayList<AuthAppUserInstance>(){{
			add(new AuthAppUserInstance(1L,1L,-1L,1L,1,0,0,"system",now,"system",now));
		}};
	}
	public AuthAppUserInstance(){
		
	}
	
	public AuthAppUserInstance(Long appId, Long appInstanceId, Long companyId, Long userId, Integer isAdmin,
			Integer isEdit,Integer delFlag, String createName, Date createTime, String modifyName, Date modifyTime) {
		super();
		this.appId = appId;
		this.appInstanceId = appInstanceId;
		this.companyId = companyId;
		this.userId = userId;
		this.isAdmin = isAdmin;
		this.isEdit = isEdit;
		this.delFlag = delFlag;
		this.createName = createName;
		this.createTime = createTime;
		this.modifyName = modifyName;
		this.modifyTime = modifyTime;
	}
	
}
