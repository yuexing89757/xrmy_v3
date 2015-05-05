package com.zzy.webapi;


public interface ApiFilter {

	public boolean doPrefix(Long advertiserId,Long accountId);
	
	public boolean doPostfix(Long advertiserId,Long accountId);
	
	public boolean doPostfix();
}
