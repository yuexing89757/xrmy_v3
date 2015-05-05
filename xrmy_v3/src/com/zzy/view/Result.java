package com.zzy.view;

import com.zzy.util.Paging;



public class Result<T> {

	public enum Status {
		SUCCESS, FAILURE, PENDING,
	}

	private T data;
	private Paging paging;
	private String error;

	
	public T getData() {
		return this.data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public Paging getPaging() {
		return this.paging;
	}
	public void setPaging(Paging paging) {
		this.paging = paging;
	}
	public String getError() {
		return this.error;
	}
	public void setError(String error) {
		this.error = error;
	}

}
