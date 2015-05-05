package com.zzy.enums;



public enum GetShardType implements IEnumCode {
	ADVERTISER, ACCOUNT;

	public static GetShardType fromCode(String s) {
		return GetShardType.valueOf(s.toUpperCase());
	}

	public String getCode() {
		return this.name();
	}

	public boolean equals(String s) {
		return this.name().equalsIgnoreCase(s);
	}

}
