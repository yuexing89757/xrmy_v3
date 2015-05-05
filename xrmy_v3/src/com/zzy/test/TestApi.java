package com.zzy.test;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import com.zzy.anotation.ApiPath;
import com.zzy.webapi.ApiMaps;
import com.zzy.webapi.ApiRequest;
import com.zzy.webapi.ApiResult;
import com.zzy.webapi.ApiService;
import com.zzy.webapi.ApiUser;

public class TestApi implements ApiService {
	
	@ApiPath("/test/ping")
	public static ApiResult ping(ApiRequest request, ApiUser user) {
		ApiResult result = new ApiResult();
		result.setResult("ping: " + Calendar.getInstance().getTimeInMillis());
		return result;
	}

	@ApiPath("/test/trace")
	public static ApiResult api(ApiRequest request, ApiUser user) {
		String path = (String) request.getParam("path", String.class);
		ApiResult result = new ApiResult();
		result.setResult(ApiMaps.findApiMethod(path).getMethod().toGenericString());
		return result;
	}

	@ApiPath("/test/dump/path")
	public static ApiResult dumpPaths(ApiRequest request, ApiUser user) {
		
		Set<String> paths = ApiMaps.getApiPaths();
		
		ApiResult result = new ApiResult();
		result.setResult(paths.toArray());
		return result;
	}

	@ApiPath("/test/dump/task")
	public static ApiResult dumpTasks(ApiRequest request, ApiUser user) {
		
		Set<String> tasks = new HashSet<String>();
		ApiResult result = new ApiResult();
		result.setResult(tasks.toArray());
		return result;
	}

}
