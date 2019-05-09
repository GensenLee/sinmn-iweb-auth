package com.sinmn.iweb.auth.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.sinmn.core.model.annotation.Column;
import com.sinmn.core.model.annotation.Table;
import com.sinmn.core.utils.util.MD5;
import com.sinmn.core.utils.verify.VerifyField;
import com.sinmn.core.utils.vo.BaseBean;
import com.sinmn.iweb.auth.util.PasswdUtil;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Table(value = "auth_user",create=true,comment="应用系统用户")
@Data
@EqualsAndHashCode(callSuper=false)
public class AuthUser extends BaseBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 流水号 */
	public static final String ID = "id";
	/** 集团id */
	public static final String COMPANY_ID = "company_id";
	/** 账号 */
	public static final String ACCOUNT = "account";
	/** 邮箱 */
	public static final String EMAIL = "email";
	/** 原始账号 */
	public static final String ORG_ACCOUNT = "org_account";
	/** 名字 */
	public static final String NAME = "name";
	/** 密码 */
	public static final String PASSWD = "passwd";
	/** 密码加权 */
	public static final String PASSWD_SUFFIX = "passwd_suffix";
	/** 是否管理员 0否 1是 */
	public static final String IS_ADMIN = "is_admin";
	/** 尝试次数 */
	public static final String TRY_COUNT = "try_count";
	/** 是否激活 0否 1是 */
	public static final String IS_ACTIVE = "is_active";
	/** 扩展字段 */
	public static final String EXT = "ext";
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
	
	@Column(name = "company_id",jdbcType="bigint(20)",notNull=true,def="0",comment="集团id")
	@VerifyField(ignore = true)
	private Long companyId;
	
	@Column(name = "account",jdbcType="varchar(200)",notNull=true,def="''",comment="账号")
	@VerifyField(ignore = true)
	private String account;
	
	@Column(name = "org_account",jdbcType="varchar(200)",notNull=true,def="''",comment="原始账号")
	@VerifyField("原始账号")
	private String orgAccount;
	
	@Column(name = "email",jdbcType="varchar(200)",notNull=true,def="''",comment="邮箱")
	@VerifyField("邮箱")
	private String email;
	
	@Column(name = "name",jdbcType="varchar(100)",notNull=true,def="''",comment="名字")
	@VerifyField("名字")
	private String name;
	
	@Column(name = "passwd",jdbcType="varchar(100)",notNull=true,def="''",comment="密码")
	@VerifyField(ignore = true)
	private String passwd;
	
	@Column(name = "passwd_suffix",jdbcType="varchar(100)",notNull=true,def="''",comment="密码加权")
	@VerifyField(ignore = true)
	private String passwdSuffix;
	
	@Column(name = "is_admin",jdbcType="tinyint(3)",notNull=true,def="1",comment="是否管理员 0否 1是")
	@VerifyField(ignore = true)
	private Integer isAdmin;
	
	@Column(name = "try_count",jdbcType="int(11)",notNull=true,def="0",comment="尝试次数")
	@VerifyField(ignore = true)
	private Integer tryCount;
	
	@Column(name = "is_active",jdbcType="tinyint(3)",notNull=true,def="1",comment="是否激活 0否 1是")
	@VerifyField(ignore = true)
	private Integer isActive;
	
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
	public static List<AuthUser> init(){
		final Date now = new Date();
		final String passwdSuffix = UUID.randomUUID().toString().replaceAll("-", "");
		return new ArrayList<AuthUser>(){{
			add(new AuthUser(-1L,"admin","admin","管理员",PasswdUtil.getPasswd("admin123456", passwdSuffix),passwdSuffix,1,0,1,"system",now,"system",now));
		}};
	}
	
	public AuthUser(){
		
	}
	
	

	public AuthUser(Long companyId, String orgAccount,String account, String name, String passwd,String passwdSuffix, Integer isAdmin, Integer tryCount,
			Integer isActive, String createName, Date createTime, String modifyName, Date modifyTime) {
		super();
		this.companyId = companyId;
		this.orgAccount = orgAccount;
		this.account = account;
		this.name = name;
		this.passwd = passwd;
		this.passwdSuffix = passwdSuffix;
		this.isAdmin = isAdmin;
		this.tryCount = tryCount;
		this.isActive = isActive;
		this.createName = createName;
		this.createTime = createTime;
		this.modifyName = modifyName;
		this.modifyTime = modifyTime;
	}
}
