package com.zzy.dao.impl.hibernate;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.Criterion;

public interface GenericDAO<T , ID extends Serializable> {

	T findById(ID id, boolean lock);

	List<T> findAll();

	List<T> findByExample(T exampleInstance, String[] excludeProperty);

	boolean save(T entity);
	
	boolean update(T entity);

	/**    
	 * maketransient(Here describes this method function with a few words)   
	 * 
	 * TODO(Here describes this method to be suitable the condition - to be possible to elect)
	 *
	 * @param idList 1L,2L,3L,3L
	 * 
	 */
	boolean delete(String idList);
	
	T makePersistent(T entity);
    int getTotalRecords(Criterion... criteria);
   
	void makeTransient(T entity);
	/**    
	 * maketransient(Here describes this method function with a few words)   
	 * 
	 * TODO(Here describes this method to be suitable the condition - to be possible to elect)
	 *
	 * @param idList 1L,2L,3L,3L
	 * 
	 */
	void makeTransient(String idList);
	void flush();
	@SuppressWarnings("rawtypes")
	List selectBySQL(String sql);
	void clear();
	
}