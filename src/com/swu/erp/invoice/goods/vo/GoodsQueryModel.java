package com.swu.erp.invoice.goods.vo;

import com.swu.erp.util.base.BaseQueryModel;
import com.swu.erp.util.format.FormatUtil;

public class GoodsQueryModel extends GoodsModel implements BaseQueryModel{ 
	//添加按价格查询的最大值
	private Double inPrice2;
	private Double outPrice2;
	//视图值
	private String inPrice2View;
	private String outPrice2View;
	
	public String getInPrice2View() {
		return inPrice2View;
	}

	public String getOutPrice2View() {
		return outPrice2View;
	}
	
	public Double getInPrice2() {
		return inPrice2;
	}
	
	public void setInPrice2(Double inPrice2) {
		this.inPrice2 = inPrice2;
		this.inPrice2View = FormatUtil.formatMoney(inPrice2);
	}
	
	public Double getOutPrice2() {
		return outPrice2;
	}
	
	public void setOutPrice2(Double outPrice2) {
		this.outPrice2 = outPrice2;
		this.outPrice2View = FormatUtil.formatMoney(outPrice2);
	}

	//TODO 添加自定义查询条件
}
