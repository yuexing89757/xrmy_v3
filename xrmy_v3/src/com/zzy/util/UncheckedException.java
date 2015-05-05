package com.zzy.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.UUID;


/**
 * 
 * @name CustomException
 * 
 * @description CLASS_DESCRIPTION
 * 
 *              MORE_INFORMATION
 * 
 * @author lijing
 * 
 * @since 2011-5-26
 * 
 * @version 1.0
 */
public class UncheckedException extends RuntimeException {

	/**
	 * @since Ver 1.1
	 */

	private static final long serialVersionUID = -6879298763723247455L;

	private static final Log log = Log.getLogger(UncheckedException.class);

	// exception message
	private String message;

	// uuid
	private String tag;

	// error code
	private String code;

	// root exception
	private Exception exception;

	// error field
	private String errorField;

	public UncheckedException() {
	}

	public UncheckedException(String message, String code) {
		this(message, code, null);
	}

//	public UncheckedException(String message, String code, Exception exception) {
//		tag = UUID.randomUUID().toString();
//		this.message = message;
//		this.code = code;
//		this.exception = exception;
//		log.error("[{0}] ({1}) {2}", tag.toString(), code, message);
//	}

	
	public UncheckedException(String message, String code, Exception exception) {
		tag = UUID.randomUUID().toString();
		this.message = message;
		this.code = code;
		this.exception = exception;
//		log.error(exception,message);
         if(null !=exception){
        	 StringWriter sw = new StringWriter();  
        	 PrintWriter pw = new PrintWriter(sw);  
        	 exception.printStackTrace(pw);  
        	 log.error("[{0}] ({1}) {2} \r\n StackTrace:{3}", tag.toString(), code, message,sw.toString());
         }else{
        	 log.error("[{0}] ({1}) {2}", tag.toString(), code, message);
         }
	}

	public UncheckedException(String message, String code, String errorField,
			Exception exception) {
		this(message, code, exception);
		this.errorField = errorField;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public String getErrorField() {
		return errorField;
	}

	public void setErrorField(String errorField) {
		this.errorField = errorField;
	}
}
