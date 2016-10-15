package com.swu.erp.invoice.orderdetail.business.ebi;

import org.springframework.transaction.annotation.Transactional;
import com.swu.erp.invoice.orderdetail.vo.OrderDetailModel;
import com.swu.erp.util.base.BaseEbi;

@Transactional
public interface OrderDetailEbi extends BaseEbi<OrderDetailModel>{

}
