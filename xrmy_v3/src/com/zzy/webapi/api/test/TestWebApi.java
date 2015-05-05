/**    
 * Project name:ads-emaila1
 *
 * Copyright Pzoomtech.com 2011, All Rights Reserved.
 *  
 */
package com.zzy.webapi.api.test;

import org.junit.Test;

import com.zzy.webapi.api.EmailApi;

/**
 * @name TestWebApi
 * 
 * @description CLASS_DESCRIPTION
 * 
 *              MORE_INFORMATION
 * 
 * @author lijing
 * 
 * @since 2011-4-29
 * 
 * @version 1.0
 */
public class TestWebApi extends WebSendMailTest<EmailApi> {


	
	@Test
	public void testSelectProduct() { // 获取全部task
		this.setUp(this.getUrl("/email/select/product"));
		this.addParam("productType", "ACTIVE");
		this.addParam("iDisplayStart", 0);
		this.addParam("iDisplayLength", 20);
		this.testResonse(this.createApiRequest());
	}
	
	@Test
	public void testSelectNews() { // 获取全部task
		this.setUp(this.getUrl("/email/select/news"));
		this.addParam("newsType", "COMPANY");
		this.addParam("iDisplayStart", 0);
		this.addParam("iDisplayLength", 20);
		this.testResonse(this.createApiRequest());
	}
}
