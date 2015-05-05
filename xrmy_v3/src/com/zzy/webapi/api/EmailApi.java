package com.zzy.webapi.api;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.mail.EmailException;
import org.xml.sax.SAXException;

import com.zzy.anotation.ApiPath;
import com.zzy.biz.EmailTaskBiz;
import com.zzy.biz.IMailUserInfoBiz;
import com.zzy.biz.INewsBiz;
import com.zzy.biz.IProductBiz;
import com.zzy.biz.ISendMail;
import com.zzy.biz.impl.EmailTaskBizImpl;
import com.zzy.biz.impl.MailUserInfoBizImpl;
import com.zzy.biz.impl.NewsBizImpl;
import com.zzy.biz.impl.ProductBizImpl;
import com.zzy.biz.impl.SendCloudMail;
import com.zzy.biz.impl.SendMail;
import com.zzy.enums.NewsType;
import com.zzy.enums.ProductType;
import com.zzy.enums.StatusType;
import com.zzy.model.MailUserInfo;
import com.zzy.model.News;
import com.zzy.model.Product;
import com.zzy.util.Log;
import com.zzy.util.Paging;
import com.zzy.view.EmailTaskView;
import com.zzy.view.Result;
import com.zzy.webapi.ApiRequest;
import com.zzy.webapi.ApiResult;
import com.zzy.webapi.ApiService;
import com.zzy.webapi.ApiUser;




public class EmailApi extends BaseWebApi implements ApiService {
	private static Log log = Log.getLogger(EmailApi.class);

	/**
	 * ApiUser自动获取
	 */
	@ApiPath("/email/filePath/send")    //发送邮件
	public static ApiResult sendFilePathEmail(ApiRequest request, ApiUser user) {
		Map<String, Object> map = new HashMap<String, Object>();
		ApiResult apiresult = new ApiResult();
		String[] toMail = request.getParam("toMail", String[].class);   //收件人们
		String title = request.getParam("title", String.class);     //主题
		String mailConent = request.getParam("mailContent", String.class);   //邮件内容
		String[] attachName = request.getParam("attachName", String[].class);   //附件名字
		String[] attachDescription = request.getParam("attachDescription", String[].class);  //附件描述
		String[] attachPath = request.getParam("attachPath", String[].class);       //附件url
		String[] cc = request.getParam("cc", String[].class);    //抄送
		String[] bcc = request.getParam("bcc", String[].class);    //密送
		String mailtype = request.getParam("mailType", String.class);       //文本格式   或者html格式  发送classify7的邮件用到
		String classify = request.getParam("classify", String.class);    //邮件分类
		boolean result = false;
		try {
			ISendMail sendmail = new SendCloudMail();
			//如果是易尔通邮件服务，则用原来的common-mail组件发送
			if ("7".equals(classify)) {
				sendmail = new SendMail();
			}
			log.info("--------sendMail start--------");
			result =sendmail.sendMail(toMail, title, mailConent, attachPath, attachDescription, attachName, cc, bcc,
						 	         mailtype, classify);   //发送邮件
			log.debug("-------sendMail end-------- " + Calendar.getInstance().getTime());
		} catch (EmailException e) {
			log.error(e.getMessage());
			apiresult.setError(e.getMessage());
		} catch (SAXException e) {
			log.error(e.getMessage());
			apiresult.setError(e.getMessage());
		} catch (ParserConfigurationException e) {
			log.error(e.getMessage());
			apiresult.setError(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
			apiresult.setError(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage());
			apiresult.setError(e.getMessage());
		}
		map.put("data", result);
		ApiResult apiResult = new ApiResult();
		apiResult.setResult(map);  
		return apiResult;
	}

	
	/**
	 * ApiUser自动获取
	 */
	@ApiPath("/email/filePath/insert")    //发送邮件
	public static ApiResult InsertEmailTask(ApiRequest request, ApiUser user) {
		Map<String, Object> map = new HashMap<String, Object>();
		ApiResult apiresult = new ApiResult();
		String[] toMail = request.getParam("toMail", String[].class);   //收件人们
		String title = request.getParam("title", String.class);     //主题
		String mailConent = request.getParam("mailContent", String.class);   //邮件内容
		String[] attachName = request.getParam("attachName", String[].class);   //附件名字
		String[] attachDescription = request.getParam("attachDescription", String[].class);  //附件描述
		String[] attachPath = request.getParam("attachPath", String[].class);       //附件url
		String[] cc = request.getParam("cc", String[].class);    //抄送
		String[] bcc = request.getParam("bcc", String[].class);    //密送
	//	String htmlContext = request.getParam("htmlContext", String.class);     //html代码
		String mailtype = request.getParam("mailType", String.class);       //文本格式   或者html格式  发送classify7的邮件用到
		String classify = request.getParam("classify", String.class);    //邮件分类
		String planTime = request.getParam("planTime", String.class);    //定时发送
	
		EmailTaskBiz emailTaskBiz = new EmailTaskBizImpl();
			log.info("--------insertMail start--------");
		Result<Boolean> result=emailTaskBiz.addEmailTask(toMail, title, mailConent, attachPath, attachDescription, attachName, cc, bcc,
						 	          mailtype, classify,planTime);   //发送邮件
			log.debug("-------insertMail end-------- " + Calendar.getInstance().getTime());
		
		/*map.put("data", result);
		ApiResult apiResult = new ApiResult();
		apiResult.setResult(map);  
		return apiResult;*/
		return getResult(result);
	}



	@ApiPath("/email/addMailUser")   //添加邮箱
	public static ApiResult addEmail(ApiRequest request, ApiUser user) {
		log.info("EmailApi --- >  addEmail");
		MailUserInfo userInfo = request.getParam("mailUserInfo", MailUserInfo.class);
		Boolean isV2 = request.getParam("isV2", Boolean.class);
		IMailUserInfoBiz userInfoBiz = new MailUserInfoBizImpl();
		Boolean result = userInfoBiz.addMailUserInfo(userInfo);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", result);
		ApiResult apiresult = new ApiResult();
		if (null != isV2 && isV2)
			apiresult.setResult(result);
		else
			apiresult.setResult(map);
		return apiresult;
	}


	@ApiPath("/email/updateMailUser")   //更新邮箱
	public static ApiResult updateEmail(ApiRequest request, ApiUser user) {
		IMailUserInfoBiz userInfoBiz = new MailUserInfoBizImpl();
		MailUserInfo userInfo = request.getParam("mailUserInfo", MailUserInfo.class);
		Boolean isV2 = request.getParam("isV2", Boolean.class);
		long id = userInfo.getId();  //id查询数据库
		String password = userInfo.getPassword();//原密码
		String newPassword = userInfo.getNewPassword();//新密码
		ApiResult apiresult = new ApiResult();
		Boolean result = false;
		MailUserInfo srcUserInfo = userInfoBiz.getMailUserInfoByID(id);  //数据库对象
		String oldPassword =srcUserInfo.getPassword(); //数据库老密码;
		if (srcUserInfo != null) {  
			if (!StringUtils.isEmpty(password)) { //老密码如果不为空
					if (StringUtils.isEmpty(newPassword)) {  //新密码为空
						apiresult.setError("请输入新密码!");
						return apiresult;
					} else {  //新密码如果不为空
						if (oldPassword.equals(password)) {
							userInfo.setPassword(newPassword);
						} else {
							apiresult.setError("原密码错误!");
							return apiresult;
						}
					}
			}else{
				userInfo.setPassword(oldPassword); //老密码为空,把密码改为数据库密码
			}
			result = userInfoBiz.updateMailUserInfo(userInfo);
			if (!result) {
				apiresult.setError("UPDATE FAILD!");
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", result);
		if (null != isV2 && isV2)
			apiresult.setResult(result);
		else
			apiresult.setResult(map);
		return apiresult;
	}

	@ApiPath("/email/updateMailUserState")   //修改邮箱状态
	public static ApiResult updateMailUserState(ApiRequest request, ApiUser user) {
		String state = request.getParam("state", String.class);
		String ID = request.getParam("id", String.class);
		Boolean isV2 = request.getParam("isV2", Boolean.class);
		IMailUserInfoBiz userInfoBiz = new MailUserInfoBizImpl();
		Boolean result = userInfoBiz.updateMailUserStatebyID(ID.split(","), state);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", result);
		ApiResult apiResult = new ApiResult();
		if (null != isV2 && isV2)
			apiResult.setResult(result);
		else
			apiResult.setResult(map);
		return apiResult;
	}

	@ApiPath("/email/delMailUser")   //删除邮箱
	public static ApiResult delEmail(ApiRequest request, ApiUser user) {
		String id = request.getParam("id", String.class);
		IMailUserInfoBiz userInfoBiz = new MailUserInfoBizImpl();
		Boolean result = userInfoBiz.deleteMailUserInfo(id);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", result);

		ApiResult apiResult = new ApiResult();
		apiResult.setResult(map);
		return apiResult;
	}

	@ApiPath("/email/selectMailUser")     //获取全部邮箱
	public static ApiResult selectMailUser(ApiRequest request, ApiUser user) {
		IMailUserInfoBiz userInfoBiz = new MailUserInfoBizImpl();
		String emailAddress = request.getParam("emailAddress", String.class);
		// 判断是否是V2版本的邮件管理,如果是V2版本则返回直接List结果集
		Boolean isV2 = request.getParam("isV2", Boolean.class);
		String sortColumns = request.getParam("sortColumns", String.class);
		String sortDir = request.getParam("sSortDir_0", String.class);
		Paging paging = getPaging(request);
		List<MailUserInfo> list = userInfoBiz.getMailUserInfo(emailAddress, paging, sortColumns, sortDir);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", list);
		ApiResult apiResult = new ApiResult();
		apiResult.setPaging(paging);
		if (null != isV2 && isV2)
			apiResult.setResult(list);
		else
			apiResult.setResult(map);
		return apiResult;
	}
	
	
	@ApiPath("/email/selectMailUser/status")     //获取全部邮箱 通过身份
	public static ApiResult selectMailUserStatus(ApiRequest request, ApiUser user) {
		IMailUserInfoBiz userInfoBiz = new MailUserInfoBizImpl();
		String emailAddress = request.getParam("emailAddress", String.class);
		String userStatus = request.getParam("userStatus", String.class);
		// 判断是否是V2版本的邮件管理,如果是V2版本则返回直接List结果集
		Boolean isV2 = request.getParam("isV2", Boolean.class);
		String sortColumns = request.getParam("sortColumns", String.class);
		String sortDir = request.getParam("sSortDir_0", String.class);
		Paging paging = getPaging(request);
		List<MailUserInfo> list = userInfoBiz.getMailUserInfo(emailAddress, paging, sortColumns, sortDir,userStatus);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", list);
		ApiResult apiResult = new ApiResult();
		apiResult.setPaging(paging);
		if (null != isV2 && isV2)
			apiResult.setResult(list);
		else
			apiResult.setResult(map);
		return apiResult;
	}
	
	
	

	@ApiPath("/email/selectMailUserByMailAddress")   //通过邮箱地址获取
	public static ApiResult selectMailUserByMailAddress(ApiRequest request, ApiUser user) {
		String emailAddress = request.getParam("emailAddress", String.class);
		Boolean isV2 = request.getParam("isV2", Boolean.class);
		IMailUserInfoBiz userInfoBiz = new MailUserInfoBizImpl();
		MailUserInfo userInfo = userInfoBiz.getMailUserInfoByAddress(emailAddress);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", userInfo);
		ApiResult apiResult = new ApiResult();
		if (null != isV2 && isV2)
			apiResult.setResult(userInfo);
		else
			apiResult.setResult(map);
		return apiResult;
	}
	
//分页
	public static Paging getPaging(ApiRequest request) {
		Paging paging = new Paging();
		Integer iDisplayLength = request.getParam("iDisplayLength", Integer.class);
		Integer iDisplayStart = request.getParam("iDisplayStart", Integer.class);
		if (iDisplayLength == null || iDisplayStart == null) {
			return null;
		}
		paging.setPageSize(iDisplayLength);
		paging.setCurrentPage((iDisplayLength + iDisplayStart) / iDisplayLength);
		return paging;
	}
	
	
	
	@ApiPath("/email/select/EmailTask")     //获取全部邮箱
	public static ApiResult selectEmailTask(ApiRequest request, ApiUser user) {
		EmailTaskBiz emailTaskBiz = new EmailTaskBizImpl();
		StatusType status = request.getParam("status", StatusType.class);
		// 判断是否是V2版本的邮件管理,如果是V2版本则返回直接List结果集
		String sortColumns = request.getParam("sortColumns", String.class);
		String sortDir = request.getParam("sSortDir_0", String.class);
		Paging paging = getPaging(request);
		List<EmailTaskView> list = emailTaskBiz.getEmailTask(status,sortColumns, sortDir, paging);
		//System.out.println(list.get(0).getMailUserInfo().getUserName());
		ApiResult apiResult = new ApiResult();
		apiResult.setPaging(paging);
		apiResult.setResult(list);
		return apiResult;
	}
	
//------------------------------------------------------------------------------------//
	
	@ApiPath("/email/select/product")     //获取全部商品
	public static ApiResult selectProduct(ApiRequest request, ApiUser user) {
		IProductBiz productBiz = new ProductBizImpl();
		ProductType productType = request.getParam("productType", ProductType.class);
		Paging paging = getPaging(request);
		List<Product> list = productBiz.findProduct(productType, paging);
		return getResult(list);
	}
	
	@ApiPath("/email/select/news")     //获取全部商品
	public static ApiResult selectNews(ApiRequest request, ApiUser user) {
		INewsBiz newsBiz = new NewsBizImpl();
		NewsType newsType = request.getParam("newsType", NewsType.class);
		Paging paging = getPaging(request);
		List<News> list = newsBiz.findProduct(newsType, paging);
		return getResult(list);
	}
	
	
}
