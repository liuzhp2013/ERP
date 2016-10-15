package com.swu.erp.util.base;

import java.io.Serializable;
import java.util.List;

public interface BaseDao<T> {
	public void save(T t);
	
	public void delete(T t);

	public void update(T t);
	
	public T get(Serializable uuid);

	public List<T> getAll();

	public List<T> getAll(BaseQueryModel bqm, Integer curPageNum,
			Integer pageCount);

	public Integer getCount(BaseQueryModel dqm);
}
