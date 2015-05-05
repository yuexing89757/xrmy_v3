package com.zzy.anotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * declare which field need to be compared
 * 
 * @author kenliu
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.FIELD, ElementType.METHOD })
public @interface ChangeComparison {

	boolean value() default false;

	String proxyMethod() default "";
}
