package com.swu.erp.invoice.goodstype.business.ebo;

import java.io.Serializable;
import java.util.List;

import com.swu.erp.invoice.goodstype.business.ebi.GoodsTypeEbi;
import com.swu.erp.invoice.goodstype.dao.dao.GoodsTypeDao;
import com.swu.erp.invoice.goodstype.vo.GoodsTypeModel;
import com.swu.erp.util.base.BaseQueryModel;

public class GoodsTypeEbo implements GoodsTypeEbi {
	private GoodsTypeDao goodsTypeDao;
	public void setGoodsTypeDao(GoodsTypeDao goodsTypeDao) {
		this.goodsTypeDao = goodsTypeDao;
	}

	public void save(GoodsTypeModel gm) {
		goodsTypeDao.save(gm);
	}

	public void delete(GoodsTypeModel gm) {
		goodsTypeDao.delete(gm);
	}
	public void update(GoodsTypeModel gm) {
		goodsTypeDao.update(gm);
	}

	public GoodsTypeModel get(Serializable uuid) {
		return goodsTypeDao.get(uuid);
	}

	public List<GoodsTypeModel> getAll() {
		return goodsTypeDao.getAll();
	}

	public List<GoodsTypeModel> getAll(BaseQueryModel bqm, Integer curPageNum,
			Integer pageCount) {
		return goodsTypeDao.getAll(bqm,curPageNum,pageCount);
	}

	public Integer getCount(BaseQueryModel bqm) {
		return goodsTypeDao.getCount(bqm);
	}

	public List<GoodsTypeModel> getAllBySm(Long uuid) {
		return goodsTypeDao.getAllBySmUuid(uuid);
	}

	public List<GoodsTypeModel> getAllUnionBySm(Long uuid) {
		return goodsTypeDao.getAllUnionBySmUuid(uuid);
	}
}
