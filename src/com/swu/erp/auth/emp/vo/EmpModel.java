package com.swu.erp.auth.emp.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
   
import com.swu.erp.auth.dep.vo.DepModel;
import com.swu.erp.auth.res.vo.ResModel;
import com.swu.erp.auth.role.vo.RoleModel;
import com.swu.erp.util.format.FormatUtil;

public class EmpModel {
	public static final String EMP_LOGIN_USER_OBJECT_NAME = "loginEm";
	
	//定义视图值
	public static final Integer EMP_GENDER_OF_MAN = 1;
	public static final Integer EMP_GENDER_OF_WOMAN = 0;
	
	public static final String EMP_GENDER_OF_MAN_VIEW = "男";
	public static final String EMP_GENDER_OF_WOMAN_VIEW = "女";
	
	public static final Map<Integer, String> genderMap = new HashMap<Integer, String>();
	static{
		genderMap.put(EMP_GENDER_OF_MAN, EMP_GENDER_OF_MAN_VIEW);
		genderMap.put(EMP_GENDER_OF_WOMAN, EMP_GENDER_OF_WOMAN_VIEW);
	}
	
	private Long uuid;
	private String userName;
	private String pwd;
	private String name;
	private String email;
	private String tele;
	private String address;
	private String lastLoginIp;
	private Integer loginTimes;
	private Long lastLoginTime;
	
	private Integer gender;
	private Long birthday;

	//设置视图值
	private String birthdayView;
	private String genderView;
	private String lastLoginTimeView;
	//辅助值
	private String reses;
	
	public String getReses() {
		return reses;
	}

	public void setReses(String reses) {
		this.reses = reses;
	}

	private DepModel dm;
	private Set<RoleModel> roles;
	
	public Set<RoleModel> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleModel> roles) {
		this.roles = roles;
	}

	public String getLastLoginTimeView() {
		return lastLoginTimeView;
	}
	
	public String getLastLoginIp() {
		return lastLoginIp;
	}
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	public Integer getLoginTimes() {
		return loginTimes;
	}
	public void setLoginTimes(Integer loginTimes) {
		this.loginTimes = loginTimes;
	}
	public Long getLastLoginTime() {
		return lastLoginTime;
	}
	
	//初始化视图值
	public void setLastLoginTime(Long lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
		this.lastLoginTimeView = FormatUtil.formatDate(lastLoginTime);
	}
	public String getGenderView() {
		return genderView;
	}
	
	public String getBirthdayView() {
		return birthdayView;
	}
	
	public Long getUuid() {
		return uuid;
	}

	public void setUuid(Long uuid) {
		this.uuid = uuid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTele() {
		return tele;
	}

	public void setTele(String tele) {
		this.tele = tele;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
		this.genderView = genderMap.get(gender);
	}

	public Long getBirthday() {
		return birthday;
	}

	public void setBirthday(Long birthday) {
		this.birthday = birthday;
		this.birthdayView = FormatUtil.formatDate(birthday);
	}

	public DepModel getDm() {
		return dm;
	}

	public void setDm(DepModel dm) {
		this.dm = dm;
	}

}
