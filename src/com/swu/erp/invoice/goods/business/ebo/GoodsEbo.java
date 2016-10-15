package com.swu.erp.invoice.goods.business.ebo;

import java.io.Serializable;
import java.util.List;

import com.swu.erp.invoice.goods.business.ebi.GoodsEbi;
import com.swu.erp.invoice.goods.dao.dao.GoodsDao;
import com.swu.erp.invoice.goods.vo.GoodsModel;
import com.swu.erp.util.base.BaseQueryModel;

public class GoodsEbo implements GoodsEbi {
	private GoodsDao goodsDao;
	public void setGoodsDao(GoodsDao goodsDao) {
		this.goodsDao = goodsDao;
	}

	public void save(GoodsModel gm) {
		goodsDao.save(gm);
	}

	public void delete(GoodsModel gm) {
		goodsDao.delete(gm);
	}
	public void update(GoodsModel gm) {
		goodsDao.update(gm);
	}

	public GoodsModel get(Serializable uuid) {
		return goodsDao.get(uuid);
	}

	public List<GoodsModel> getAll() {
		return goodsDao.getAll();
	}

	public List<GoodsModel> getAll(BaseQueryModel bqm, Integer curPageNum,
			Integer pageCount) {
		return goodsDao.getAll(bqm,curPageNum,pageCount);
	}

	public Integer getCount(BaseQueryModel bqm) {
		return goodsDao.getCount(bqm);
	}

	public List<GoodsModel> getAllByGtm(Long uuid) {
		return goodsDao.getAllByGtmUuid(uuid);
	}
}
