package com.swu.erp.auth.emp.vo;

import com.swu.erp.util.base.BaseQueryModel;
import com.swu.erp.util.format.FormatUtil;

public class EmpQueryModel extends EmpModel implements BaseQueryModel{ 
	//添加最后登录时间最为按登录时间查询的最大值
	private Long lastLoginTime2;
	
	//视图值
	private String lastLoginTime2View;
	
	public String getLastLoginTime2View() {
		return lastLoginTime2View;
	}

	public Long getLastLoginTime2() {
		return lastLoginTime2;
	}

	public void setLastLoginTime2(Long lastLoginTime2) {
		this.lastLoginTime2 = lastLoginTime2;
		this.lastLoginTime2View = FormatUtil.formatDate(lastLoginTime2);
	}
	
	//TODO 添加自定义查询条件
}
