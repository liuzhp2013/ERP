package com.swu.erp.auth.emp.dao.dao;

import java.util.List;

import com.swu.erp.auth.emp.vo.EmpModel;
import com.swu.erp.util.base.BaseDao;

public interface EmpDao extends BaseDao<EmpModel>{
	public EmpModel getByUserNameAndPwd(String userName, String pwd);

	public boolean updatePwdByUserNameAndPwd(String userName, String pwd, String newPwd);

	public List<EmpModel> getAllByDepUuid(Long uuid);
}
