package com.zzy.webapi.json;



import java.util.Locale;

import com.zzy.enums.AdNetwork;
import com.zzy.enums.DataFormat;
import com.zzy.enums.GetShardType;
import com.zzy.util.CoreExceptionCode;
import com.zzy.util.UncheckedException;
import com.zzy.util.gson.GsonHelper;
import com.zzy.util.gson.GsonObject;
import com.zzy.webapi.ApiRequest;

/**
 * handle the JSON request
 * 
 * JSON request format: <code>
 * {
 *   network: AdNetwork,
 *   locale: Locale,
 *   params:
 *   {
 *     key1: value1,
 *     key2: value2,
 *   }
 * }
 * </code>
 * 
 * @author kenliu
 * 
 */
public class JsonApiRequest extends ApiRequest {

	private AdNetwork network = null;
	private Locale locale = null;
	private GsonObject rawParams = null;
//	private String SIID = null;
	private Long advertiserId = null;
	private Long accountId = null;
	public JsonApiRequest() {
	}

	public JsonApiRequest(String jsonData) {
		parse(jsonData);
	}

	@Override
	public void parse(String data) {
		GsonObject o = GsonHelper.buildGson().fromJson(data, GsonObject.class);
		network = AdNetwork.valueOf(o.get(LABEL_NETWORK, String.class).toUpperCase());
		locale = new Locale(o.get(LABEL_LOCALE, String.class));
		//Long[] ls = this.parseSIID(o.get(LABEL_DB,String.class));
	    //	this.advertiserId = ls[0];
	   //	this.accountId = ls[1];
		rawParams = o.get(LABEL_PARAMS);
	}

	@Override
	public DataFormat getFormat() {
		return DataFormat.JSON;
	}

	@Override
	public Locale getLocale() {
		return locale;
	}

	@Override
	public AdNetwork getNetwork() {
		return network;
	}

	@Override
	public String[] getParamKeys() {
		return rawParams.keys().toArray(new String[] {});
	}

	@Override
	public <T> T getParam(String key, Class<T> t) {
		return rawParams.get(key, t);
	}

	
	@Override
	public Long getAdvertiserId() {
		if(null!=this.advertiserId){
			return this.advertiserId;
		}else if(null!=this.accountId){
			return  null;
		}else {
			return -1l;
		}
	}

	/**
	 * 这个方法的调用是有问题的,除非存在很极端的情况下
	 */
//	@Override
//	public Long getAccountId() {
//		if(null != this.accountId){
//			return accountId;
//		}else {
//			return null;
//		}
//	}
	
	@Override
	public GetShardType getShardType(){
		if(null != advertiserId){
			return GetShardType.ADVERTISER;
		}else if (null!=accountId) {
			return GetShardType.ACCOUNT;
		}else {
			return GetShardType.ADVERTISER;
		}
	}
	
	@Override
	public Long getId(){
		if(null != advertiserId){
			return advertiserId;
		}else if (null!=accountId) {
			return accountId;
		}else {
			return -1l;
		}
	}

	private Long[] parseSIID(String SIID){
		if(SIID == null|| SIID.indexOf(",") <= 0){
			throw new UncheckedException("SIID format is error",CoreExceptionCode.SYSTEM_ERROR);
		}
		//SIID decode.
		
		Long[] ls = new Long[2];
		String[] d = SIID.split(",");
		if("at".equalsIgnoreCase(d[1])){
			ls[0] = Long.valueOf(d[0]);
		}else if("ad".equalsIgnoreCase(d[1])){
			ls[1] = Long.valueOf(d[0]);
		}else{
			throw new UncheckedException("api's request is error.",CoreExceptionCode.SYSTEM_ERROR);
		}
		return ls;
	}

}
