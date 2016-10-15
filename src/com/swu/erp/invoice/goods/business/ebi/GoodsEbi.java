package com.swu.erp.invoice.goods.business.ebi;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.swu.erp.invoice.goods.vo.GoodsModel;
import com.swu.erp.util.base.BaseEbi;

@Transactional
public interface GoodsEbi extends BaseEbi<GoodsModel>{

	public List<GoodsModel> getAllByGtm(Long uuid);

}
