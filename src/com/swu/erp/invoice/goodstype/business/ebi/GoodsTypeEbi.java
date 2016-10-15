package com.swu.erp.invoice.goodstype.business.ebi;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.swu.erp.invoice.goodstype.vo.GoodsTypeModel;
import com.swu.erp.util.base.BaseEbi;

@Transactional
public interface GoodsTypeEbi extends BaseEbi<GoodsTypeModel>{
	/**
	 * 根据供应商的id查询对应的商品类别
	 * @param uuid 供应商uuid
	 * @return
	 */
	public List<GoodsTypeModel> getAllBySm(Long uuid);

	/**
	 * 查询当前供应商下包含商品信息的所有商品类别
	 * @param uuid 供应商uuid
	 * @return
	 */
	public List<GoodsTypeModel> getAllUnionBySm(Long uuid);

}
