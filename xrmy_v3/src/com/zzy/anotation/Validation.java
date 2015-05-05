package com.zzy.anotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.zzy.validator.IValidator;

/**
 * object validation
 * 
 * @author kenliu
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Validation {
	/**
	 * the validator of target object
	 * 
	 * @return
	 */
	Class<? extends IValidator<?>> value();
}
