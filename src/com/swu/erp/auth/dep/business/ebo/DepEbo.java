package com.swu.erp.auth.dep.business.ebo;

import java.io.Serializable;
import java.util.List;
import com.swu.erp.auth.dep.business.ebi.DepEbi;
import com.swu.erp.auth.dep.dao.dao.DepDao;
import com.swu.erp.auth.dep.vo.DepModel;
import com.swu.erp.util.base.BaseQueryModel;
/**
 * Title:部门模块业务层实现类
 * Description:
 * Company:swu
 * @author 刘长平
 * @date 2016年7月19日
 */
public class DepEbo implements DepEbi {
	//注入Dao层接口
	private DepDao depDao;
	public void setDepDao(DepDao depDao) {
		this.depDao = depDao;
	}
	
	public void save(DepModel dm) {
		//����Depģ����ݲ�־û�����
		depDao.save(dm);
	}
	
	public void delete(DepModel dm) {
		depDao.delete(dm);
	}

	public void update(DepModel dm) {
		depDao.update(dm);
	}
	
	public DepModel get(Serializable uuid) {
		return depDao.get(uuid);
	}
	
	public List<DepModel> getAll() {
		return depDao.getAll();
	}

	public List<DepModel> getAll(BaseQueryModel bqm, Integer curPageNum,
			Integer pageCount) {
		return depDao.getAll(bqm,curPageNum,pageCount);
	}

	public Integer getCount(BaseQueryModel bqm) {
		return depDao.getCount(bqm);
	}
}
