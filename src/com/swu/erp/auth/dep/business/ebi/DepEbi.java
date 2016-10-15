package com.swu.erp.auth.dep.business.ebi;

import org.springframework.transaction.annotation.Transactional;
import com.swu.erp.auth.dep.vo.DepModel;
import com.swu.erp.util.base.BaseEbi;

@Transactional
/**
 * Title:部门模块业务层接口
 * Description:
 * Company:swu
 * @author 刘长平
 * @date 2016年7月19日
 */
public interface DepEbi extends BaseEbi<DepModel>{
	
}
