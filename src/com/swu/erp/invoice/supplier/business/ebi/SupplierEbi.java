package com.swu.erp.invoice.supplier.business.ebi;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.swu.erp.invoice.supplier.vo.SupplierModel;
import com.swu.erp.util.base.BaseEbi;

@Transactional
public interface SupplierEbi extends BaseEbi<SupplierModel>{
	/**
	 * 获取所有具有商品类别的供应商信息
	 * @return
	 */
	public List<SupplierModel> getAllUnion();
	
	/**
	 * 获取所有包含商品类别(商品类别需包含商品信息)的供应商
	 * @return
	 */
	public List<SupplierModel> getAllUnionTwo();

}
