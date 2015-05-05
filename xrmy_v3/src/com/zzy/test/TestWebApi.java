/**    
 * Project name:ads-emaila1
 *
 * Copyright Pzoomtech.com 2011, All Rights Reserved.
 *  
 */
package com.zzy.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
	public void testMethod() { // 测试发邮件
		this.setUp(this.getUrl("/test/ping"));
		this.addParam("classify", "8");
		this.testResonse(this.createApiRequest());
	}

	@Test
	public void testMethod2() { // 测试发邮件
		this.setUp(this.getUrl("/email/filePath/send"));
		this.addParam("title", "找回密码");
		this.addParam("toMail", new String[] { "243860327@qq.com" });
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		this.addParam(
				"mailContent",
				"<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">"
						+ "<HTML>"
						+ "<HEAD><TITLE>会员密码找回</TITLE></HEAD>"
						+ "<BODY>"
						+ "<p align='left'>亲爱的用户:</p>"
						+ "<p align='left'>&nbsp;&nbsp;&nbsp;&nbsp;请点击冒号后面的链接;如果不能点击,请将冒号后面的链接复制并粘帖到浏览器的地址输入框,然后敲回车即可。（链接有效时间为1个小时）：</p>"
						+ "<p align='left'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;http://sempro-qa.pzoom.com/pzoom_forgetpassword.html</p>"
						+ "<br/><br/><p align='left'>品种互动团队</p> <p align='left'>"
						+ sdf.format(Calendar.getInstance().getTime()) + "</p>"
						+ "</BODY></HTML>");
		this.addParam("mailType", "HTML");
		this.addParam("classify", "0");
		this.testResonse(this.createApiRequest());
	}

	

	@Test
	// 修改邮箱状态
	public void testUpdateMailUserState() {
		this.setUp(this.getUrl("/email/updateMailUserState"));
		this.addParam("emailAddress", "243860327@qq.com");
		this.addParam("state", "DELETED");
		this.addParam("id", (long) 721);
		this.testResonse(this.createApiRequest());
	}

	@Test
	public void testDelMailUser() { // 删除邮箱
		this.setUp(this.getUrl("/email/delMailUser"));
		this.addParam("id", "721");
		this.testResonse(this.createApiRequest());
	}

	/*@Test
	public void testSelectMailUser() { // 获取全部邮箱
		this.setUp(this.getUrl("/email/selectMailUser"));
		this.addParam("emailAddress", "");
		this.addParam("iDisplayStart", 0);
		this.addParam("iDisplayLength", 20);
		this.addParam("sortColumns", "classify");
		this.addParam("sSortDir_0", "desc");
		this.testResonse(this.createApiRequest());
	}*/

	@Test
	public void testSelectMailUserByMailAddress() { // 通过邮箱地址获取
		this.setUp(this.getUrl("/email/selectMailUserByMailAddress"));
		this.addParam("emailAddress", "777777777@qq.com");
		this.addParam("isV2", true);
		this.testResonse(this.createApiRequest());
	}

	
	@Test
	public void testSelectMailUserStatus() { // 获取全部邮箱  status    同邮箱地址获取
		this.setUp(this.getUrl("/email/selectMailUser/status"));
		this.addParam("emailAddress", "r");
		//this.addParam("userStatus", "1");   //1 adapter  
		this.addParam("iDisplayStart", 0);
		this.addParam("iDisplayLength", 5);
		this.addParam("sortColumns", "classify");
		this.addParam("sSortDir_0", "desc");
		this.testResonse(this.createApiRequest());
	}
	
	
	@Test
	public void testSelectMailTask() { // 获取全部task
		this.setUp(this.getUrl("/email/select/EmailTask"));
		this.addParam("iDisplayStart", 0);
		this.addParam("iDisplayLength", 20);
		this.addParam("status", "READY");
		this.addParam("sortColumns", "status");
		this.addParam("sSortDir_0", "desc");
		this.testResonse(this.createApiRequest());
	}
	
	@Test
	public void testMethodInsert() { // 测试存数据
		this.setUp(this.getUrl("/email/filePath/Insert"));
		this.addParam("title", "找回密码");
		this.addParam("toMail", new String[] { "243860327@qq.com","kaige8312@163.com" });
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		this.addParam(
				"mailContent",
				"<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">"
						+ "<HTML>"
						+ "<HEAD><TITLE>会员密码找回</TITLE></HEAD>"
						+ "<BODY>"
						+ "<p align='left'>亲爱的用户:</p>"
						+ "<p align='left'>&nbsp;&nbsp;&nbsp;&nbsp;请点击冒号后面的链接;如果不能点击,请将冒号后面的链接复制并粘帖到浏览器的地址输入框,然后敲回车即可。（链接有效时间为1个小时）：</p>"
						+ "<p align='left'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;http://sempro-qa.pzoom.com/pzoom_forgetpassword.html</p>"
						+ "<br/><br/><p align='left'>品种互动团队</p> <p align='left'>"
						+ sdf.format(Calendar.getInstance().getTime()) + "</p>"
						+ "</BODY></HTML>");
		this.addParam("mailType", "HTML");
		this.addParam("classify", "7");
		this.addParam("planTime",  new Date().getTime()+"");
		this.testResonse(this.createApiRequest());
	}
	
	@Test
	public void testDelMailUser11() { // 删除邮箱
      String str="[243860327@qq.com, kaige8312@163.com]";
     // String abc="[243860327@qq.com, kaige8312@163.com]";
  	Gson gson = new Gson();
  	List<String> ps =
  			 gson.fromJson(str, new TypeToken<List<String>>(){}.getType());
  	 System.out.println(ps.size());
      
	}
	
	
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
