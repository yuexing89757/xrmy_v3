package com.zzy.enums;

public interface IEnumCode {
	/**
	 * code of enum type
	 * @return
	 */
	public String getCode();
	/**
	 * check the enum code equality
	 * @param code
	 * @return
	 */
	public boolean equals(String code);
}