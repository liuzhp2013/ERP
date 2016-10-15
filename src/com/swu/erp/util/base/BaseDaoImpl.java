package com.swu.erp.util.base;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.swu.erp.auth.dep.vo.DepModel;

public abstract class BaseDaoImpl<T> extends HibernateDaoSupport {
	private Class<T> entityClass; //模型的泛型类型
	
	//在构造方法中初始化entityClass
	public BaseDaoImpl() {
	    Type genType = getClass().getGenericSuperclass();   //获取子类的父类类型（包括泛型）
	    //得到由泛型构成的参数数组
	    Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
	    entityClass =  (Class)params[0];   
	}
	
	public void save(T t) {
		this.getHibernateTemplate().save(t);
	}

	public void delete(T t) {
		this.getHibernateTemplate().delete(t);
	}

	public void update(T t) {
		this.getHibernateTemplate().update(t);
	}
	
	public T get(Serializable uuid) {
		return this.getHibernateTemplate().get(entityClass, uuid);
	}

	public List<T> getAll() {
		//以下两步可以实现查询全部的功能
		DetachedCriteria dc = DetachedCriteria.forClass(entityClass);
		return this.getHibernateTemplate().findByCriteria(dc);
	}

	@SuppressWarnings("unchecked")
	public List<T> getAll(BaseQueryModel bqm, Integer curPageNum,
			Integer pageCount) {
		// 执行查询，动态链接dqm中的查询条件，完成查询(此处最好采用QBC的查询方式)
		DetachedCriteria dc = DetachedCriteria.forClass(entityClass);

		doQBC(dc,bqm);
		
		return this.getHibernateTemplate().findByCriteria(dc,
				(curPageNum - 1) * pageCount, pageCount);
	}
	
	public Integer getCount(BaseQueryModel bqm) {
		
		// 此处一定需要动态条件，
		DetachedCriteria dc = DetachedCriteria.forClass(entityClass);

		dc.setProjection(Projections.rowCount()); // 行计数

		doQBC(dc,bqm);

		List<Long> count = this.getHibernateTemplate().findByCriteria(dc);
		return count.get(0).intValue();
	}
	
	//提供自定义查询条件的抽象方法供子类实现
	public abstract void doQBC(DetachedCriteria dc,BaseQueryModel bqm);
}
