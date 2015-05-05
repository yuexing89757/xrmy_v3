package com.zzy.util;


import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.zzy.enums.AdNetwork;
import com.zzy.util.gson.GsonHelper;
import com.zzy.util.gson.GsonObject;
import com.zzy.webapi.ApiError;

public class JsonHttpConnector {

	private static final Log log = Log.getLogger(JsonHttpConnector.class);

	private HttpRequestSimulator simulator;

	public JsonHttpConnector(String url) {
		simulator = new HttpRequestSimulator(url, null);
	}

	public GsonObject send(AdNetwork network, Locale locale, Map<String, Object> params) {
		Map<String, Object> jsonRequest = new HashMap<String, Object>();
		jsonRequest.put("locale", locale);
		jsonRequest.put("network", network);
		jsonRequest.put("params", params);
		Object id = params.get("advertiser_id");
		if(null != id){
			jsonRequest.put("SIID", id + ",at");
		}
		//
		String requestData = GsonHelper.buildGson().toJson(jsonRequest);
		log.debug("Request: {0}", requestData);
		String respData = simulator.postData(requestData, null);
		log.debug("Response: {0}", respData);
		//
		GsonObject gson = GsonHelper.buildGson().fromJson(respData, GsonObject.class);
		//
		ApiError err = gson.get("error", ApiError.class);
		if (err != null) {
			throw new RuntimeException(err.getCode() + ":" + err.getMessage());
		}
		return gson.get("result");
	}
	
	
	public GsonObject sendRequest(AdNetwork network, Locale locale, Map<String, Object> params) {
		Map<String, Object> jsonRequest = new HashMap<String, Object>();
		jsonRequest.put("locale", locale);
		jsonRequest.put("network", network);
		jsonRequest.put("params", params);
		//
		String requestData = GsonHelper.buildGson().toJson(jsonRequest);
		log.debug("Request: {0}", requestData);
		String respData = simulator.postData(requestData, null);
		log.debug("Response: {0}", respData);
		//
		GsonObject gson = GsonHelper.buildGson().fromJson(respData, GsonObject.class);
		//
		ApiError err = gson.get("error", ApiError.class);
		if (err != null) {
			throw new RuntimeException(err.getCode() + ":" + err.getMessage());
		}
		return gson;
	}
	/**
	 * send api request with oauth 
	 * @param network
	 * @param locale
	 * @param key
	 * @param secret 
	 * @param params
	 * @return
	 */
	public GsonObject send(AdNetwork network, Locale locale, String key, String secret, Map<String, Object> params) {
		Map<String, Object> jsonRequest = new HashMap<String, Object>();
		jsonRequest.put("locale", locale);
		jsonRequest.put("network", network);
		jsonRequest.put("key", key);
		jsonRequest.put("secret", secret);
		jsonRequest.put("params", params);
		String requestData = GsonHelper.buildGson().toJson(jsonRequest);
		log.debug("Request: {0}", requestData);
		String respData = simulator.postData(requestData, null);
		log.debug("Response: {0}", respData);
		GsonObject gson = GsonHelper.buildGson().fromJson(respData, GsonObject.class);
		//
		ApiError err = gson.get("error", ApiError.class);
		if (err != null) {
			throw new RuntimeException(err.getCode() + ":" + err.getMessage());
		}
		return gson.get("result");
	}
}
