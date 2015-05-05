package com.zzy.util;

import java.util.Locale;

import com.zzy.webapi.ApiRequest;


public class ApiRequestAnalysisHelper {
	public static Locale getLocale(ApiRequest request) {
		Locale locale = request.getLocale();
		if (null == locale) {
			return Locale.CHINA;
		}
		return locale;
	}
}
