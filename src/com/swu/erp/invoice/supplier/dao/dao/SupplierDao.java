package com.swu.erp.invoice.supplier.dao.dao;

import java.util.List;

import com.swu.erp.invoice.supplier.vo.SupplierModel;
import com.swu.erp.util.base.BaseDao;

public interface SupplierDao extends BaseDao<SupplierModel>{

	public List<SupplierModel> getAllUnion();
	
	public List<SupplierModel> getAllUnionTwo();

}
