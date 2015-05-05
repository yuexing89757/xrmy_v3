package com.zzy.webapi.api;

import java.util.Calendar;

import com.zzy.util.Log;
import com.zzy.util.Paging;
import com.zzy.util.StringUtils;
import com.zzy.view.Result;
import com.zzy.webapi.ApiRequest;
import com.zzy.webapi.ApiResult;



abstract public class BaseWebApi {

	public static final String DATE_FORMAT = "yyyy/MM/dd";
	protected static final String TASK_DATE_FORMAT = "yyyy/MM/dd hh:mm:ss";
	protected static final String TASK_DATE_24_FORMAT = "yyyy/MM/dd HH:mm:ss";

	private static final Log log = Log.getLogger(BaseWebApi.class);

		
	protected static ApiResult getResult(Object value) {
		if (null == value) {
			log.error("value is null");
			throw new IllegalArgumentException("value is null");
		}
		if (value instanceof Result<?>) {
			Result<?> r = (Result<?>) value;
			return getResult(r);
		}

		ApiResult result = new ApiResult();
		if (value.getClass().isArray()) {
			result.setResult(value);
		} else {
			result.setResult(new Object[] { value });
		}
		return result;
	}

	protected static ApiResult getResult(Result<?> result) {
		if (null == result) {
			log.error("result is null");
			throw new IllegalArgumentException("result is null");
		}
		ApiResult apiResult = new ApiResult();
		if (StringUtils.hasLength(result.getError())) {
			apiResult.setError(result.getError());
		} else {
			apiResult.setResult(null == result.getData() ? new Object[] {}
					: result.getData());
			apiResult.setPaging(result.getPaging());
		}
		return apiResult;
	}



	
	/**
	 * 获取DataTable的分页信息
	 * 
	 * @param request
	 * @return
	 */
	public static Paging getPaging(ApiRequest request) {
		Paging paging = new Paging();
		Integer iDisplayLength = request.getParam("iDisplayLength",
				Integer.class);
		Integer iDisplayStart = request
				.getParam("iDisplayStart", Integer.class);
		if (iDisplayLength == null || iDisplayStart == null) {
			return null;
		}
		paging.setPageSize(iDisplayLength);
		paging
				.setCurrentPage((iDisplayLength + iDisplayStart)
						/ iDisplayLength);
		return paging;
	}

	/**
	 * getLastMoment(Here describes this method function with a few words)
	 * 
	 * 获取结束日期的最大日期（2010/11/10 23:59:59）
	 * 
	 * 
	 * @param endTime
	 * @return
	 * 
	 * Calendar
	 */
	protected static Calendar getLastMoment(Calendar endTime) {
		if (null != endTime) {
			endTime.set(Calendar.HOUR_OF_DAY, 23);
			endTime.set(Calendar.MINUTE, 59);
			endTime.set(Calendar.SECOND, 59);
		}
		return endTime;
	}

	protected static Calendar getBeforeMoment(Calendar startTime) {
		if (null != startTime) {
			startTime.set(Calendar.HOUR_OF_DAY, 0);
			startTime.set(Calendar.MINUTE, 0);
			startTime.set(Calendar.SECOND, 0);
		}
		return startTime;
	}

	

}