package com.zzy.enums;

/**
 * 
 * @author guokaige
 * @date 2014-12-24
 */
public enum StatusType {
	READY,FUNCTION,FINISH,ERROR;
	public static StatusType get(String val) {
		for (StatusType item : StatusType.values()) {
			if (item.toString().equals(val)) {
				return item;
			}
		}
		return null;
	}
}
