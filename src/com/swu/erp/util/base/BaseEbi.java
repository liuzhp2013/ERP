package com.swu.erp.util.base;

import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BaseEbi<T> {
	public void save(T t);
	
	public void delete(T t);

	public void update(T t);
	
	public T get(Serializable uuid);

	public List<T> getAll();

	public List<T> getAll(BaseQueryModel bqm, Integer curPageNum,
			Integer pageCount);

	public Integer getCount(BaseQueryModel bqm);
}
