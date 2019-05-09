package com.sinmn.iweb.auth.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sinmn.core.model.annotation.Column;
import com.sinmn.core.model.annotation.Table;
import com.sinmn.core.utils.vo.BaseBean;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Table(value = "auth_menu",create=true,comment="系统菜单")
@Data
@EqualsAndHashCode(callSuper=false)
public class AuthMenu extends BaseBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 流水号 */
	public static final String ID = "id";
	/** 系统id */
	public static final String APP_ID = "app_id";
	/** 菜单名字 */
	public static final String NAME = "name";
	/** 菜单链接 */
	public static final String URL = "url";
	/** 父节点id */
	public static final String PARENT_ID = "parent_id";
	/** 路径，前后都有逗号 */
	public static final String PATH = "path";
	/** 资源，前后都有逗号 */
	public static final String RESOURCES = "resources";
	/** 图标 */
	public static final String ICON = "icon";
	/** 是否激活 0否 1是 */
	public static final String IS_ACTIVE = "is_active";
	/** 删除标志位 0未删除 1删除 */
	public static final String DEL_FLAG = "del_flag";
	/** 扩展字段 */
	public static final String EXT = "ext";
	/** 显示对应的表达式 */
	public static final String SHOW_REG = "show_reg";
	/** 是否系统默认 0否 1是 */
	public static final String IS_SYSTEM = "is_system";
	/** 排序，从小到大 */
	public static final String SORT = "sort";
	/** 创建人 */
	public static final String CREATE_NAME = "create_name";
	/** 创建时间 */
	public static final String CREATE_TIME = "create_time";
	/** 最后修改人 */
	public static final String MODIFY_NAME = "modify_name";
	/** 最后修改时间 */
	public static final String MODIFY_TIME = "modify_time";
	

	@Column(name = "id",jdbcType="bigint(20)",priKey=true,autoIncrement=true,comment="流水号")
	private Long id;
	
	@Column(name = "app_id",jdbcType="bigint(20)",notNull=true,def="0",comment="系统id")
	private Long appId;
	
	@Column(name = "name",jdbcType="varchar(100)",notNull=true,def="''",comment="菜单名字")
	private String name;
	
	@Column(name = "url",jdbcType="varchar(500)",notNull=true,def="''",comment="菜单链接")
	private String url;
	
	@Column(name = "parent_id",jdbcType="bigint(20)",notNull=true,def="0",comment="父节点id")
	private Long parentId;
	
	@Column(name = "path",jdbcType="varchar(500)",notNull=true,def="''",comment="路径，前后都有逗号")
	private String path;
	
	@Column(name = "ext",jdbcType="varchar(2000)",notNull=true,def="''",comment="扩展字段")
	private String ext;
	
	@Column(name = "show_reg",jdbcType="varchar(1000)",notNull=true,def="''",comment="显示对应的表达式")
	private String showReg;
	
	@Column(name = "resources",jdbcType="varchar(500)",notNull=true,def="''",comment="资源，前后都有逗号")
	private String resources;
	
	@Column(name = "icon",jdbcType="varchar(50)",notNull=true,def="''",comment="图标")
	private String icon;
	
	@Column(name = "is_active",jdbcType="tinyint(3)",notNull=true,def="1",comment="是否激活 0否 1是")
	private Integer isActive;
	
	@Column(name = "del_flag",jdbcType="tinyint(3)",notNull=true,def="0",comment="删除标志位 0未删除 1删除")
	private Integer delFlag;
	
	@Column(name = "is_system",jdbcType="tinyint(3)",notNull=true,def="1",comment="是否系统默认 0否 1是")
	private Integer isSystem;
	
	@Column(name = "sort",jdbcType="float",notNull=true,def="0",comment="排序，从小到大")
	private Float sort;
	
	@Column(name = "create_name",jdbcType="varchar(50)",notNull=true,def="''",comment="创建人")
	private String createName;
	
	@Column(name = "create_time",jdbcType="datetime",notNull=true,def="",comment="创建时间")
	private Date createTime;
	
	@Column(name = "modify_name",jdbcType="varchar(50)",notNull=true,def="''",comment="最后修改人")
	private String modifyName;
	
	@Column(name = "modify_time",jdbcType="datetime",notNull=true,def="",comment="最后修改时间")
	private Date modifyTime;
	
	@SuppressWarnings("serial")
	public static List<AuthMenu> init(){
		final Date now = new Date();
		return new ArrayList<AuthMenu>(){{
			//1
			add(new AuthMenu(1L,"权限设置","/auth-app/list",0L,"","default",1,0,1,0f,"system",now,"system",now));
			
			//2
			add(new AuthMenu(1L,"权限设置","#",1L,",1,","default",1,0,1,0f,"system",now,"system",now));
			
			//3
			add(new AuthMenu(1L,"系统列表","/auth-app/list",2L,",1,2,","default",1,0,1,0f,"system",now,"system",now));
			
			//4
			add(new AuthMenu(1L,"系统实例","/auth-app-instance/list",2L,",1,2,","default",1,0,1,0f,"system",now,"system",now));
			
			//5
			add(new AuthMenu(1L,"菜单列表","/auth-menu/list",2L,",1,2,","default",1,0,1,0f,"system",now,"system",now));
			
			//6
			add(new AuthMenu(1L,"资源列表","/auth-resources/list",2L,",1,2,","default",1,0,1,0f,"system",now,"system",now));
			
			//7
			add(new AuthMenu(1L,"用户列表","/auth-user/list",2L,",1,2,","default",1,0,1,0f,"system",now,"system",now));
			
			//8
			add(new AuthMenu(1L,"角色列表","/auth-role/list",2L,",1,2,","default",1,0,1,0f,"system",now,"system",now));
			
			//9
			add(new AuthMenu(1L,"集团列表","/auth-company/list",2L,",1,2,","default",1,0,1,0f,"system",now,"system",now));
			
			//10
			add(new AuthMenu(1L,"登录日志","/auth-login-log/list",2L,",1,2,","default",1,0,1,0f,"system",now,"system",now));

			//11
			add(new AuthMenu(1L,"参数设置","/auth-config/list",0L,"","default",1,0,1,0f,"system",now,"system",now));
			//12
			add(new AuthMenu(1L,"参数设置","#",11L,",11,","default",1,0,1,0f,"system",now,"system",now));
			//13
			add(new AuthMenu(1L,"参数列表","/auth-config/list",12L,",11,12，","default",1,0,1,0f,"system",now,"system",now));
			

			
		}};
	}
	
	public AuthMenu(){
		
	}
	
	

	public AuthMenu(Long appId, String name, String url, Long parentId, String path, String icon, Integer isActive,
			Integer delFlag, Integer isSystem, Float sort, String createName, Date createTime, String modifyName,
			Date modifyTime) {
		super();
		this.appId = appId;
		this.name = name;
		this.url = url;
		this.parentId = parentId;
		this.path = path;
		this.icon = icon;
		this.isActive = isActive;
		this.delFlag = delFlag;
		this.isSystem = isSystem;
		this.sort = sort;
		this.createName = createName;
		this.createTime = createTime;
		this.modifyName = modifyName;
		this.modifyTime = modifyTime;
	}
}
