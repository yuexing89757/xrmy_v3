package com.zzy.anotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * use <code>@Authorization(scopeCheck=IAuthScope.class)</code> to check the
 * authorization
 * 
 * @author kenliu
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Authorization {
	/**
	 * the authorization and scope validation class, this class must implement
	 * {@link IAuthScope} interface
	 * 
	 * @return
	 */
	Class<? extends IAuthScope<?, ?>> value();
	
	String paramKey() default "";
	
	
}
