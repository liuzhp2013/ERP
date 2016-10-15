package com.swu.erp.invoice.goodstype.web;

import java.util.List;

import org.apache.struts2.json.JSONUtil;

import com.swu.erp.invoice.goodstype.business.ebi.GoodsTypeEbi;
import com.swu.erp.invoice.goodstype.vo.GoodsTypeModel;
import com.swu.erp.invoice.goodstype.vo.GoodsTypeQueryModel;
import com.swu.erp.invoice.supplier.business.ebi.SupplierEbi;
import com.swu.erp.invoice.supplier.vo.SupplierModel;
import com.swu.erp.util.base.BaseAction;

public class GoodsTypeAction extends BaseAction{ 
	public GoodsTypeModel gm = new GoodsTypeModel();
	public GoodsTypeQueryModel gqm = new GoodsTypeQueryModel();

	private GoodsTypeEbi goodsTypeEbi;
	private SupplierEbi supplierEbi;
	
	public void setGoodsTypeEbi(GoodsTypeEbi goodsTypeEbi) {
		this.goodsTypeEbi = goodsTypeEbi;
	}
	
	public void setSupplierEbi(SupplierEbi supplierEbi) {
		this.supplierEbi = supplierEbi;
	}

	public String input() {
		List<SupplierModel> supplierList = supplierEbi.getAll();
		put("supplierList", supplierList);
		if(gm.getUuid() != null) {
			gm = goodsTypeEbi.get(gm.getUuid());
		}
		return BaseAction.INPUT;
	}

	public String save() {
		if(gm.getUuid() == null) {
			goodsTypeEbi.save(gm);
		} else {
			goodsTypeEbi.update(gm);
		}

		return BaseAction.TO_LIST;
	}

	public String delete() {
		goodsTypeEbi.delete(gm);
		return BaseAction.TO_LIST;
	}

	public String list() {
		this.initPageDate(goodsTypeEbi.getCount(gqm));
		List<GoodsTypeModel> goodsTypeList = goodsTypeEbi.getAll(gqm,curPageNum,pageCount);
		this.put("goodsTypeList", goodsTypeList);
		
		List<SupplierModel> supplierList = supplierEbi.getAll();
		put("supplierList", supplierList);
 		return BaseAction.LIST;
	}

//===========ajax===========================================================
	//生成要返回页面的数据
	private List<GoodsTypeModel> gtmList = null;
	public List<GoodsTypeModel> getGtmList() {
		return gtmList;
	}

	public String ajaxGetBySm(){
		//根据供应商的uuid获取对应的商品类别信息
		gtmList = goodsTypeEbi.getAllBySm(gm.getSm().getUuid());
		//如何将获取到的数据以json格式返回页面
			//1，导入struts2-json-plugin-2.3.7.jar
			//2，在struts.xml中配置json类型的结果集
			//3，为需要返回的数据提供get方法
		return "ajaxGetBySm";
	}

}

