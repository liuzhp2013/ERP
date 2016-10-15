package com.swu.erp.invoice.goods.dao.dao;

import java.util.List;

import com.swu.erp.invoice.goods.vo.GoodsModel;
import com.swu.erp.util.base.BaseDao;

public interface GoodsDao extends BaseDao<GoodsModel>{

	public List<GoodsModel> getAllByGtmUuid(Long uuid);

}
