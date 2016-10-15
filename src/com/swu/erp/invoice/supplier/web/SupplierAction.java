package com.swu.erp.invoice.supplier.web;

import java.util.List;
import com.swu.erp.invoice.supplier.business.ebi.SupplierEbi;
import com.swu.erp.invoice.supplier.vo.SupplierModel;
import com.swu.erp.invoice.supplier.vo.SupplierQueryModel;
import com.swu.erp.util.base.BaseAction;

public class SupplierAction extends BaseAction{ 
	public SupplierModel sm = new SupplierModel();
	public SupplierQueryModel sqm = new SupplierQueryModel();

	private SupplierEbi supplierEbi;
	public void setSupplierEbi(SupplierEbi supplierEbi) {
		this.supplierEbi = supplierEbi;
	}

	public String input() {
		if(sm.getUuid() != null) {
			sm = supplierEbi.get(sm.getUuid());
		}
		return BaseAction.INPUT;
	}

	public String save() {
		if(sm.getUuid() == null) {
			supplierEbi.save(sm);
		} else {
			supplierEbi.update(sm);
		}

		return BaseAction.TO_LIST;
	}

	public String delete() {
		supplierEbi.delete(sm);
		return BaseAction.TO_LIST;
	}

	public String list() {
		this.initPageDate(supplierEbi.getCount(sqm));
		List<SupplierModel> supplierList = supplierEbi.getAll(sqm,curPageNum,pageCount);
		this.put("supplierList", supplierList);
 		return BaseAction.LIST;
	}
}
