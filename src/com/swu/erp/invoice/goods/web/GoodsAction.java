package com.swu.erp.invoice.goods.web;

import java.util.List;

import com.swu.erp.invoice.goods.business.ebi.GoodsEbi;
import com.swu.erp.invoice.goods.vo.GoodsModel;
import com.swu.erp.invoice.goods.vo.GoodsQueryModel;
import com.swu.erp.invoice.goodstype.business.ebi.GoodsTypeEbi;
import com.swu.erp.invoice.goodstype.vo.GoodsTypeModel;
import com.swu.erp.invoice.supplier.business.ebi.SupplierEbi;
import com.swu.erp.invoice.supplier.vo.SupplierModel;
import com.swu.erp.util.base.BaseAction;

public class GoodsAction extends BaseAction{ 
	public GoodsModel gm = new GoodsModel();
	public GoodsQueryModel gqm = new GoodsQueryModel();

	private GoodsEbi goodsEbi;
	private SupplierEbi supplierEbi;
	private GoodsTypeEbi goodsTypeEbi;
	public void setGoodsEbi(GoodsEbi goodsEbi) {
		this.goodsEbi = goodsEbi;
	}

	public void setSupplierEbi(SupplierEbi supplierEbi) {
		this.supplierEbi = supplierEbi;
	}

	public void setGoodsTypeEbi(GoodsTypeEbi goodsTypeEbi) {
		this.goodsTypeEbi = goodsTypeEbi;
	}

	public String input() {
		//加载所有供应商信息
		List<SupplierModel> supplierList = supplierEbi.getAllUnion();
		put("supplierList", supplierList);
		Long supplierUuid = null;
		if(gm.getUuid() != null) {
			gm = goodsEbi.get(gm.getUuid());
			//获取当前供应商的uuid
			supplierUuid = gm.getGtm().getSm().getUuid();
		} else {
			//获取第一个供应商的uuid
			supplierUuid = supplierList.get(0).getUuid();
		}
		List<GoodsTypeModel> gtmList = goodsTypeEbi.getAllBySm(supplierList.get(0).getUuid());
		put("gtmList", gtmList);
		return BaseAction.INPUT;
	}

	public String save() {
		if(gm.getUuid() == null) {
			goodsEbi.save(gm);
		} else {
			goodsEbi.update(gm);
		}

		return BaseAction.TO_LIST;
	}

	public String delete() {
		goodsEbi.delete(gm);
		return BaseAction.TO_LIST;
	}

	public String list() {
		List<SupplierModel> supplierList = supplierEbi.getAll();
		put("supplierList", supplierList);
		
		this.initPageDate(goodsEbi.getCount(gqm));
		List<GoodsModel> goodsList = goodsEbi.getAll(gqm,curPageNum,pageCount);
		put("goodsList", goodsList);
 		return LIST;
	}
}
