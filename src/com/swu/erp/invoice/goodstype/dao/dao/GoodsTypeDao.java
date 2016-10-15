package com.swu.erp.invoice.goodstype.dao.dao;

import java.util.List;

import com.swu.erp.invoice.goodstype.vo.GoodsTypeModel;
import com.swu.erp.util.base.BaseDao;

public interface GoodsTypeDao extends BaseDao<GoodsTypeModel>{

	public List<GoodsTypeModel> getAllBySmUuid(Long uuid);

	public List<GoodsTypeModel> getAllUnionBySmUuid(Long uuid);

}
