package com.swu.erp.auth.res.dao.dao;

import java.util.List;

import com.swu.erp.auth.res.vo.ResModel;
import com.swu.erp.util.base.BaseDao;

public interface ResDao extends BaseDao<ResModel>{

	public List<ResModel> getAllResByEmpUuid(Long uuid);

}
