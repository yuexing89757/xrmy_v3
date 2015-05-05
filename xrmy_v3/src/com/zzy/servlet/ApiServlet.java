
package com.zzy.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zzy.anotation.Authorization;
import com.zzy.anotation.IAuthScope;
import com.zzy.enums.DataFormat;
import com.zzy.enums.DataLabel;
import com.zzy.util.Log;
import com.zzy.webapi.ApiError;
import com.zzy.webapi.ApiFilter;
import com.zzy.webapi.ApiMaps;
import com.zzy.webapi.ApiMaps.ApiMethod;
import com.zzy.webapi.ApiRequest;
import com.zzy.webapi.ApiResponse;
import com.zzy.webapi.ApiResult;
import com.zzy.webapi.ApiUser;

public class ApiServlet extends HttpServlet implements Servlet {

	private static final long serialVersionUID = 1L;

	private static final Log log = Log.getLogger(ApiServlet.class);

	private static final String CHARSET_UTF8 = "UTF-8";
	private static final String SERVLET_CONFIG_FILTER = "filter";
	private static final String SERVLET_CONFIG_PATH = "path";
	private static final String SERVLET_CONFIG_CLASS_PATH = "classpath";
	private static ApiFilter filterInstance = null;
	private static ApiFilter logFilterInstance = null;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		if (ApiMaps.getApiPaths().size() <= 0) {
			// initialize apiMaps
			String classpath = config.getInitParameter(SERVLET_CONFIG_CLASS_PATH);
			if (classpath != null) {
				String[] paths = classpath.split(",");
				ApiMaps.RegisterAll(paths);
			}
			log.info("initialized ApiMaps[{0}]", ApiMaps.getApiPaths().size());
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		execute(request, response);
	}

	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (filterInstance == null) {
			filterInstance = getFilterInstance(getServletConfig().getInitParameter(SERVLET_CONFIG_FILTER));
		}
		// Set the char encoding to UTF-8
		request.setCharacterEncoding(CHARSET_UTF8);
		ApiResult result = null;
		ApiMethod apiMethod = null;
		DataFormat apiDataType = null;
		//根据request获取当前登陆的用户信息
	//	ApiUser apiUser = getApiUser(request);
		String apiAction = "";
		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		try{
			if (requestURI.contains(".")) {
				// parse data type
				String ext = requestURI.substring(requestURI.lastIndexOf(".") + 1);
				apiDataType = DataFormat.valueOf(ext.toUpperCase());
				// parse action path
				apiAction = requestURI.substring(contextPath.length(), requestURI.lastIndexOf("."));
	
				apiAction = apiAction.substring(getServletConfig().getInitParameter(SERVLET_CONFIG_PATH).length()); // "/api/*"
	
				apiMethod = ApiMaps.findApiMethod(apiAction);
				/*//apiUser为null并且@Authorization没有配置 刚登陆的用户
				if (apiUser == null && apiMethod.getAuth() == null) {//new login permit it enter...
					log.debug("new login user,let it go-----contation:(apiUser == null && apiMethod.getAuth() == null)");
				} else if ((apiUser == null || "null".equals(apiUser.getUserId())) && apiMethod.getAuth() != null) {
					result = new ApiResult();
					result.setError(ApiError.INVALID_USER, null);
				}*/
			}else{
				log.error("request url format is error.");
				return;
			}
	
			if (result == null) {
				// check if it is a validate request URL
				if (apiMethod == null || apiDataType == null) {
					throw new IllegalArgumentException("invalid request");
				}
				
				ApiRequest apiRequest =ApiRequest.getInstance(apiDataType, apiMethod.getPath(), decodeHttpRequestPayload(request));
				filterInstance.doPrefix(apiRequest.getAdvertiserId(), null);
				IAuthScope<ApiUser, ApiRequest> scope = null;
				String paramKey = "";
				try {
					//存在@Authorization的注解
					if (apiMethod.getAuth() != null) {
						Authorization auth = apiMethod.getAuth();
						paramKey = auth.paramKey();
						scope = (IAuthScope<ApiUser, ApiRequest>) auth.value().newInstance();
					}
					//TODO(hf.fu) 如果checkscope进行检查的话，会开启static session，这样的话，每次请求都要开启shard+static session
					if (scope == null || scope.checkScope(null, apiRequest, paramKey)) {
						String ip = getRemoteAddress(request);
						// OK, execute the apiMethod
						Method m = apiMethod.getMethod();
						result = (ApiResult) m.invoke(null, apiRequest, null);
						System.out.println("ip:"+ip+"调用"+m.getName());
					} else {
						result = new ApiResult();
						result.setError(ApiError.INVALID_AUTH, null);
					}
				} catch (Exception e) {
					log.error(e);
					throw new ServletException(e);
				}
			}
			setSessionObjects(request, result.getSessionObjects());
		}catch(Exception ex){
			result = new ApiResult();
			log.error("ApiServlet INVALID_REQUEST:");
			log.error(ex);
			result.setError(ApiError.INVALID_REQUEST, null);
		}
		
		
		
		// format result and output
		ApiResponse apiResponse = ApiResponse.getInstance(apiDataType, result);
		response.setStatus(HttpServletResponse.SC_OK);
		response.setCharacterEncoding(CHARSET_UTF8);
		response.setContentType(apiResponse.getContentType());
		apiResponse.write(response.getWriter());
		if (filterInstance != null && filterInstance.doPostfix() == false) {
			log.debug("ApiFilter.doPostfix() checking failure");
			response.resetBuffer();
			return;
		}
		if (null != logFilterInstance) {
			logFilterInstance.doPostfix();
		}
		response.flushBuffer();
	}
	
	
	private void setSessionObjects(HttpServletRequest request, Map<String, Object> sessionObjects) {
		if (sessionObjects != null) {
			for (String key : sessionObjects.keySet()) {
				request.getSession().setAttribute(key, sessionObjects.get(key));
			}
		}
	}

	private ApiUser getApiUser(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		log.debug("ApiServlet getApiUser(HttpServletRequest request) session:"+session);
		//表示非法用户登录
		if (session == null || session.isNew()) {
			return null;
		}
		//此处主要是获取登陆后设置在session中的userId
		Object sessionUserId = session.getAttribute(DataLabel.SESSION_USERID);
		log.debug("ApiServlet getApiUser(HttpServletRequest request) sessionUserId:"+sessionUserId);
		ApiUser user = new ApiUser(String.valueOf(sessionUserId));
		user.setVerificationCode(String.valueOf(session.getAttribute(DataLabel.SESSION_CHECK_CODE)));
		Object userdata = session.getAttribute(DataLabel.SESSION_USERDATA);
		if (userdata != null && userdata instanceof Serializable)
			user.setData((Serializable) userdata);
		return user;
	}

	private ApiFilter getFilterInstance(String className) {
		if (className != null) {
			try {
				Class<?> clz = Class.forName(className);
				if (ApiFilter.class.isAssignableFrom(clz)) {
					ApiFilter filter = (ApiFilter) clz.newInstance();
					return filter;
				}
			} catch (Exception ex) {
				log.error("Fail to initialize ApiFilter: {0}", className);
			}
		}
		return null;
	}

	public static String decodeHttpRequestPayload(HttpServletRequest request) {
		InputStream inputStream = null;
		int contentLength = request.getContentLength();
		if (contentLength == -1)
			throw new RuntimeException("invalidate content length in request");

		try {
			int offset = 0;
			int length = contentLength;
			int byteCount;

			inputStream = request.getInputStream();

			byte[] payload = new byte[contentLength];

			while (offset < contentLength) {
				byteCount = inputStream.read(payload, offset, length);
				if (byteCount == -1)
					throw new RuntimeException("invalidate content length in request");

				offset += byteCount;
				length -= byteCount;
			}
			String res = new String(payload, request.getCharacterEncoding());
			return res;
		} catch (IOException ioe) {
			log.error(ioe);
			throw new RuntimeException(ioe);
		} finally {
			try {
				if (inputStream != null)
					inputStream.close();
			} catch (IOException e) {
				// do nothing
			}
		}
	}

	public String getRemoteAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown"))
			ip = request.getHeader("Proxy-Client-IP");
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown"))
			ip = request.getHeader("WL-Proxy-Client-IP");
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown"))
			ip = request.getRemoteAddr();
		return ip;
	}

}
