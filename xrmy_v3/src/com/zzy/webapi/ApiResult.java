package com.zzy.webapi;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.zzy.util.ExceptionUtil;
import com.zzy.util.Paging;

public class ApiResult {
	
	private ApiError error = null;
	private com.zzy.util.Paging paging = null;
	private Object singleObject = null;
	private Object[] arrayObjects = null;
	private Map<String, Object> sessionObjects = new HashMap<String, Object>();
	
	public void addSessionObject(String key, Object value) {
		sessionObjects.put(key, value);
	}
	public void removeSessionObject(String key) {
		sessionObjects.remove(key);
	}
	public void clearSessionObjects() {
		sessionObjects.clear();
	}
	public Map<String, Object> getSessionObjects() {
		return Collections.unmodifiableMap(sessionObjects);
	}
	
	public void setError(String msg) {
		error = ApiError.UNKNOWN;
		error.setMessage(msg);
	}
	
	public void setError(String code,String msg){
		error = new ApiError(code);
		error.setMessage(msg);
	}
	
	public void setError(ApiError err, Throwable t) {
		error = err;
		if (t!=null) {
			error.setMessage(t.getMessage());
			//TODO(ken): fill the details
			error.setDetails(new String[] { ExceptionUtil.getStackTrace(t) });
		}
	}

	public void setResult(Object object) {
		if (arrayObjects!=null)
			throw new IllegalArgumentException("result already set to Array");
		singleObject = object;
	}
	
	public void setResult(Object[] objects) {
		if (singleObject!=null)
			throw new IllegalArgumentException("result already set to Object");
		arrayObjects = objects;
	}
	
	public ApiError getError() {
		return error;
	}
	
	public Object getResult() {
		return singleObject;
	}
	
	public Object[] getResults() {
		return arrayObjects;
	}
	
	public Paging getPaging() {
		return this.paging;
	}
	public void setPaging(Paging paging) {
		this.paging = paging;
	}
	public void setError(ApiError error) {
		this.error = error;
	}
	
}
