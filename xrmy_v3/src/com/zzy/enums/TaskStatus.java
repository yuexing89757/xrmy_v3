package com.zzy.enums;
public enum TaskStatus  implements IEnumCode {
	READY, COMPLETE, PENDING, ERROR;
	public static TaskStatus fromCode(String s) {
		return TaskStatus.valueOf(s.toUpperCase());
	}

	public String getCode() {
		return this.name();
	}

	public boolean equals(String s) {
		return this.name().equalsIgnoreCase(s);
	}
}