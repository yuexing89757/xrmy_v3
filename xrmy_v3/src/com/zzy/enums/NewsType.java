package com.zzy.enums;


public enum NewsType {
	COMPANY,ADVANCE,TREND;
	public static NewsType get(String val) {
		for (NewsType item : NewsType.values()) {
			if (item.toString().equalsIgnoreCase(val)) {
				return item;
			}
		}
		return null;
	}
}
