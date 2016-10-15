package com.swu.erp.auth.res.business.ebi;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.swu.erp.auth.res.vo.ResModel;
import com.swu.erp.util.base.BaseEbi;

@Transactional
public interface ResEbi extends BaseEbi<ResModel>{

	public List<ResModel> getAllResByEmp(Long uuid);

}
