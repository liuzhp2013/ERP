package com.swu.erp.auth.res.business.ebo;

import java.io.Serializable;
import java.util.List;

import com.swu.erp.auth.res.business.ebi.ResEbi;
import com.swu.erp.auth.res.dao.dao.ResDao;
import com.swu.erp.auth.res.vo.ResModel;
import com.swu.erp.util.base.BaseQueryModel;

public class ResEbo implements ResEbi {
	private ResDao resDao;
	public void setResDao(ResDao resDao) {
		this.resDao = resDao;
	}

	public void save(ResModel rm) {
		resDao.save(rm);
	}

	public void delete(ResModel rm) {
		resDao.delete(rm);
	}
	public void update(ResModel rm) {
		resDao.update(rm);
	}

	public ResModel get(Serializable uuid) {
		return resDao.get(uuid);
	}

	public List<ResModel> getAll() {
		return resDao.getAll();
	}

	public List<ResModel> getAll(BaseQueryModel bqm, Integer curPageNum,
			Integer pageCount) {
		return resDao.getAll(bqm,curPageNum,pageCount);
	}

	public Integer getCount(BaseQueryModel bqm) {
		return resDao.getCount(bqm);
	}

	public List<ResModel> getAllResByEmp(Long uuid) {
		return resDao.getAllResByEmpUuid(uuid);
	}
}
