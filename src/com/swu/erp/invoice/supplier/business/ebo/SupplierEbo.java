package com.swu.erp.invoice.supplier.business.ebo;

import java.io.Serializable;
import java.util.List;
import com.swu.erp.invoice.supplier.business.ebi.SupplierEbi;
import com.swu.erp.invoice.supplier.dao.dao.SupplierDao;
import com.swu.erp.invoice.supplier.vo.SupplierModel;
import com.swu.erp.util.base.BaseQueryModel;

public class SupplierEbo implements SupplierEbi {
	private SupplierDao supplierDao;
	public void setSupplierDao(SupplierDao supplierDao) {
		this.supplierDao = supplierDao;
	}

	public void save(SupplierModel sm) {
		supplierDao.save(sm);
	}

	public void delete(SupplierModel sm) {
		supplierDao.delete(sm);
	}
	public void update(SupplierModel sm) {
		supplierDao.update(sm);
	}

	public SupplierModel get(Serializable uuid) {
		return supplierDao.get(uuid);
	}

	public List<SupplierModel> getAll() {
		return supplierDao.getAll();
	}

	public List<SupplierModel> getAll(BaseQueryModel bqm, Integer curPageNum,
			Integer pageCount) {
		return supplierDao.getAll(bqm,curPageNum,pageCount);
	}

	public Integer getCount(BaseQueryModel bqm) {
		return supplierDao.getCount(bqm);
	}

	public List<SupplierModel> getAllUnion() {
		return supplierDao.getAllUnion();
	}
	
	public List<SupplierModel> getAllUnionTwo() {
		return supplierDao.getAllUnionTwo();
	}

}
