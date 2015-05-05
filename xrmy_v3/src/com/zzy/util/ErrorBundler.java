package com.zzy.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Map.Entry;
import java.util.Properties;

import com.zzy.view.Result;

public class ErrorBundler {

	protected Properties errorBundle = null;

	public ErrorBundler(String language){
	
		 InputStream  is = ErrorBundler.class.getClassLoader().getResourceAsStream("error_cn.properties");
		 errorBundle =  new  Properties();
		 try {
			errorBundle.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getErrorInfo(String errorKey,String... errorDesc) {
		String errorInfo = this.getErrorInfo(errorKey);
		if (null != errorInfo) {
			if (!ObjectUtils.isEmpty(errorDesc)) {
				StringBuffer messageInfo = new StringBuffer();
				for (String info : errorDesc) {
					if(messageInfo.length()>0)
						messageInfo.append(StringUtils.COMMA);
					messageInfo.append(info);
				}
				errorInfo = MessageFormat.format(errorInfo, messageInfo.toString());
			}
			return errorInfo;
		}
		return null;
	}
	public String getErrorInfo(BusinessException bizException) {
		if (null == bizException)
			return null;
		else if (!bizException.isThrow())
			return null;
		else {
			StringBuffer messageBuffer = new StringBuffer();
			for (Entry<String, String> entry : bizException.getErrorMap().entrySet()) {
				if(messageBuffer.length()>0)
					messageBuffer.append(StringUtils.COMMA);
				if (errorBundle.containsKey(entry.getKey())) {
					String errorInfo = (String) errorBundle.get(entry.getKey());
					errorInfo = MessageFormat.format(errorInfo, null==entry.getValue()?"":entry.getValue());
					messageBuffer.append(errorInfo);
				}else{
					messageBuffer.append(entry.getKey());
				}
			}
			return messageBuffer.toString();
		}
	}

	public String getErrorInfo(UncheckedException businessEx) {
		if (null == businessEx)
			return null;
		else {
			String errorCode = businessEx.getCode();
			if (StringUtils.hasLength(errorCode)) {
				String message = getErrorInfo(errorCode);
				message = MessageFormat.format(message, null == businessEx
						.getErrorField() ? "" : businessEx.getErrorField());
				return message;
			}
			return null;
		}
	}

	public String getErrorInfo(String errorPropertiesKey) {
		if (null != errorBundle && StringUtils.hasLength(errorPropertiesKey)) {
			String[] erroInfoArray = errorPropertiesKey.split(StringUtils.COMMA);
			StringBuffer errorBuffer = new StringBuffer();
			for (String info : erroInfoArray) {
				errorBuffer = errorBuffer.length() > 0 ? errorBuffer.append(StringUtils.COMMA) : errorBuffer;
				String errorInfo = (String) (errorBundle.containsKey(info) ? errorBundle.get(info) : info);
				errorBuffer.append(errorInfo);
			}
			return errorBuffer.toString();
		}
		return errorPropertiesKey;
	}
	


	public void setErrorInfo(Result<? extends Object> result,BusinessException bizBusinessException) {
		String errorInfo = this.getErrorInfo(bizBusinessException);
		if (null != result && null != errorInfo) {
			result.setError(errorInfo);
		}
	}
	


	public void setErrorInfo(Result<? extends Object> result,UncheckedException exception) {
		String errorInfo = this.getErrorInfo(exception);
		if (null != result && null != errorInfo) {
			result.setError(errorInfo);
		}
	}

	public void setErrorInfo(Result<? extends Object> result, String errorPropertiesKey) {
		String message = this.getErrorInfo(errorPropertiesKey);
		if (null != result) {
			result.setError(message);
		}
	}

	public void setErrorInfo(Result<? extends Object> result, String errorKey,String... errorDesc) {
		String errorInfo = this.getErrorInfo(errorKey);
		if (null != errorInfo) {
			if (!ObjectUtils.isEmpty(errorDesc)) {
				StringBuffer messageInfo = new StringBuffer();
				for (String info : errorDesc) {
					if(messageInfo.length()>0)
						messageInfo.append(StringUtils.COMMA);
					messageInfo.append(info);
				}
				errorInfo = MessageFormat.format(errorInfo, messageInfo.toString());
			}
			result.setError(errorInfo);
		}
	}
	

}
