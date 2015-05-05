package com.zzy.dao.dbtool;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import com.zzy.dao.impl.hibernate.GenericDAO;
import com.zzy.enums.GetShardType;

final public class DaoFactory {

	private static Map<Class<?>, Class<?>> daoMappings = new HashMap<Class<?>, Class<?>>();
	static {
		//daoMappings.put(PersonDao.class, PersonDaoImpl.class);
	}
	
	@SuppressWarnings("unchecked")
	public synchronized static <T extends GenericDAO<?, ?>> T getInstance(
			Class<T> daoInterface,GetShardType shardType,Long id) {
		try {
			Class<T> clz = (Class<T>) daoMappings.get(daoInterface);
			Constructor<T> constructor = clz.getConstructor(Long.class,GetShardType.class);
			return constructor.newInstance(id,shardType);
			
		} catch (Exception e) {
			return null;
		}
	}
	
}
