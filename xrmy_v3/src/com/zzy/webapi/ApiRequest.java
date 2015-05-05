package com.zzy.webapi;



import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.zzy.anotation.ApiPath;
import com.zzy.anotation.IAuthRequest;
import com.zzy.enums.AdNetwork;
import com.zzy.enums.DataFormat;
import com.zzy.enums.GetShardType;
import com.zzy.util.Log;
import com.zzy.webapi.json.JsonApiRequest;

/**
 * 
 * @author kenliu
 * 
 */
abstract public class ApiRequest implements IAuthRequest {
	
	static {
		Register(DataFormat.JSON, JsonApiRequest.class);
		//Register(DataFormat.HTML, HtmlApiRequest.class);
	}

	public static final String LABEL_NETWORK = "network";
	public static final String LABEL_LOCALE = "locale";
	public static final String LABEL_PARAMS = "params";
	public static final String LABEL_DB="SIID";
	
	private static final Log log = Log.getLogger(ApiRequest.class);

	private static Map<DataFormat, Class<? extends ApiRequest>> factory;

	public static void Register(DataFormat format,
			Class<? extends ApiRequest> clz) {
		if (factory==null)
			factory = new HashMap<DataFormat, Class<? extends ApiRequest>>();
		factory.put(format, clz);
	}

	public static ApiRequest getInstance(DataFormat dataType, ApiPath path, String request) {
		Class<? extends ApiRequest> clz = factory.get(dataType);
		if (clz != null) {
			try {
				ApiRequest req = clz.newInstance();
				req.path = (path == null ? null : path.value());
				req.parse(request);
				return req;
			} catch (Exception ex) {
				log.error("ApiRequest  path : {0} , request :{1}",path !=null ? path.value() : null,request);
				log.error(ex);
			}
		}
		throw new IllegalArgumentException("unsupport data format");
	}
	
	private String path;
	public String getPath() {
		return path;
	}
	
	
	/**
	 * parse the request data
	 * 
	 * @param data
	 */
	abstract public void parse(String data);

	/**
	 * the request contains which {@link AdNetwork} is targeted
	 * 
	 * @return targeted network
	 */
	abstract public AdNetwork getNetwork();

	/**
	 * the request contains the expected {@link DataFormat}
	 * 
	 * @return data format
	 */
	abstract public DataFormat getFormat();

	/**
	 * the request may contain the {@link Locale}, or use default locale
	 * 
	 * @return target locale
	 */
	abstract public Locale getLocale();

	/**
	 * all the parameters keys
	 * 
	 * @return keys of parameter
	 */
	abstract public String[] getParamKeys();
	
	/**
	 * the specific parameter object of the key
	 * 
	 * @param <T>
	 *            define the expected result type
	 * @param key
	 * @param t
	 * @return the parameter object
	 */
	abstract public <T extends Object> T getParam(String key, Class<T> t);
	
	abstract public Long getAdvertiserId();
	
//	abstract public Long getAccountId();
	
	abstract public GetShardType getShardType();
	
	abstract public Long getId();
	
	/**
	 * the specific parameter object of the key
	 * 
	 * @param key
	 * @return the parameter object
	 */
	public Object getParam(String key) {
		return getParam(key, Object.class);
	}
}
