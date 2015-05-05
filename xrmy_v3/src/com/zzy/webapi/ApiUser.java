package com.zzy.webapi;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.zzy.anotation.IAuthUser;

public class ApiUser implements IAuthUser {

	private String uniqueId = null;
	private Serializable dataObject = null;
	private String verificationCode =null;
	private String ua_id;
	/**
	 * key = 'currentUserSource' value='QHOO' or 'pzoom'
	 */
	private Map<String,String> map;
	public ApiUser(String userUniqueId) {
		uniqueId = userUniqueId;
	}
	public ApiUser(String userUniqueId,Map<String,String> map){
		this.uniqueId = userUniqueId;
		this.map = map;
	}
	public ApiUser(String userUniqueId, String userAccountId)
	  {
	    this.uniqueId = null;
	    this.dataObject = null;
	    this.ua_id = null;
	    this.map = new HashMap<String,String>();
	    this.uniqueId = userUniqueId;
	    this.ua_id = userAccountId;
	  }
	public String getUserId() {
		return uniqueId;
	}

	public Serializable getData() {
		return this.dataObject;
	}

	public void setData(Serializable dataObject) {
		this.dataObject = dataObject;
	}

	public void setVerificationCode(String valueOf) {
		this.verificationCode =valueOf;
		
	}

	public String getVerificationCode() {
		return this.verificationCode;
	}
	public String getUniqueId() {
		return uniqueId;
	}
	public String getUa_id() {
		return ua_id;
	}
	public Map<String,String> getMap() {
		return map;
	}
	
}
