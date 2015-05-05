package com.zzy.validator;

import java.util.Map;
import java.util.Set;

/**
 * define the interface of a validator for particular object type
 * 
 * @author kenliu
 * 
 * @param <T>
 *            the type of target object
 */
public interface IValidator<T> {
	/**
	 * validate the object
	 * 
	 * @param object
	 * @return check if the target object is valid
	 */
	boolean validate(T object);
	
	/**
	 * validate the object all property
	 * @param object
	 * @return
	 * @author  wushuaifei
	 * @date  2014-9-5
	 * @reWriter  
	 * @reWriteTime
	 */
	Set<String> validateForUpload(T object);

	/**
	 * validate the object in specific level, level should be defined in current
	 * validator
	 * 
	 * @param object
	 * @param level
	 * @return check if the target object is valid
	 */
	boolean validate(T object, String level);

	/**
	 * get error messages
	 * 
	 * @return error messages
	 */
	String[] getMessages();
	
	Map<String, Object> getValueMap();
}
