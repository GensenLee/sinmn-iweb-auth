package com.sinmn.iweb.auth.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sinmn.core.model.annotation.Column;
import com.sinmn.core.model.annotation.Table;
import com.sinmn.core.utils.vo.BaseBean;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Table(table="auth_login_log",create=true,comment="登录日志")
@Data
@EqualsAndHashCode(callSuper=false)
public class AuthLoginLog extends BaseBean{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 流水�? */
	public static final String ID = "id";
	/** 用户 */
	public static final String USER_ID = "user_id";
	/** 账号*/
	public static final String ACCOUNT = "account";
	/** ip地址 */
	public static final String IP = "ip";
	/** 目标ip地址 */
	public static final String TARGET_IP = "target_ip";
	/** 地区 */
	public static final String ADDRESS = "address";
	/** 状态 */
	public static final String STATUS = "status";
	/** 原因 */
	public static final String REASON = "reason";
	/** 创建时间 */
	public static final String CREATE_TIME = "create_time";
	/** 集团ID */
	public static final String COMPANY_ID = "company_id";

	@SuppressWarnings("serial")
	public static List<AuthLoginLog> init(){
		return new ArrayList<AuthLoginLog>(){{
		}};
	}

    /** 流水号 */
	@Column(name = "id",jdbcType="bigint(20)",priKey=true,autoIncrement=true, comment="流水号")
	private Long id;
	
	/** 集团ID */
	@Column(name = "company_id",jdbcType="bigint(20)",notNull=true,def="0",comment="集团ID")
	private Long companyId;
    
    /** 用户 */
	@Column(name = "user_id",jdbcType="bigint(20)",notNull=true,def="0",comment="用户")
	private Long userId;
	
	@Column(name = "account",jdbcType="varchar(100)",notNull=true,def="''",comment="登录账号")
	private String account;
    
    /** ip地址 */
	@Column(name = "ip",jdbcType="varchar(50)",notNull=true,def="''",comment="ip地址")
	private String ip;
	
	/** 目标ip地址 */
	@Column(name = "target_ip",jdbcType="varchar(50)",notNull=true,def="''",comment="ip地址")
	private String targetIp;
	
	/** 状态*/
	@Column(name = "status",jdbcType="tinyint(4)",notNull=true,def="0",comment="是否成功 (0登录失败，1登录成功)")
	private Integer status;
	
	/** 原因*/
	@Column(name = "reason",jdbcType="varchar(300)",notNull=true,def="''",comment="原因")
	private String reason;
    
    /** 地区 */
	@Column(name = "address",jdbcType="varchar(200)",notNull=true,def="''",comment="地区")
	private String address;
	
	/** 创建时间 */
	@Column(name = "create_time",jdbcType="datetime",notNull=true,def="",comment="创建时间")
	private Date createTime;
	
}

