package com.zzy.enums;

/**
 * 
 * @author liuxiaoping
 * @date 2012-02-08
 */
public enum MailType {
	TEXT, HTML;
	public static MailType get(String val) {
		for (MailType item : MailType.values()) {
			if (item.toString().equalsIgnoreCase(val)) {
				return item;
			}
		}
		return null;
	}
}
