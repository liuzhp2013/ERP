package com.swu.erp.auth.role.business.ebi;

import org.springframework.transaction.annotation.Transactional;

import com.swu.erp.auth.role.vo.RoleModel;
import com.swu.erp.util.base.BaseEbi;

@Transactional
public interface RoleEbi extends BaseEbi<RoleModel>{

	public void update(RoleModel rm, Long[] resUuids, Long[] menuUuids);

	public void save(RoleModel rm, Long[] resUuids, Long[] menuUuids);

}
