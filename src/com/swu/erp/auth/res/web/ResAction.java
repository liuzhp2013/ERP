package com.swu.erp.auth.res.web;

import java.util.List;
import com.swu.erp.auth.res.business.ebi.ResEbi;
import com.swu.erp.auth.res.vo.ResModel;
import com.swu.erp.auth.res.vo.ResQueryModel;
import com.swu.erp.util.base.BaseAction;

public class ResAction extends BaseAction{ 
	public ResModel rm = new ResModel();
	public ResQueryModel rqm = new ResQueryModel();

	private ResEbi resEbi;
	public void setResEbi(ResEbi resEbi) {
		this.resEbi = resEbi;
	}

	public String input() {
		if(rm.getUuid() != null) {
			rm = resEbi.get(rm.getUuid());
		}
		return BaseAction.INPUT;
	}

	public String save() {
		if(rm.getUuid() == null) {
			resEbi.save(rm);
		} else {
			resEbi.update(rm);
		}

		return BaseAction.TO_LIST;
	}

	public String delete() {
		resEbi.delete(rm);
		return BaseAction.TO_LIST;
	}

	public String list() {
		this.initPageDate(resEbi.getCount(rqm));
		List<ResModel> resList = resEbi.getAll(rqm,curPageNum,pageCount);
		this.put("resList", resList);
 		return BaseAction.LIST;
	}
}
