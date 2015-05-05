package com.zzy.webapi.json;

import java.io.Reader;
import java.io.StringReader;
import java.util.Calendar;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.zzy.enums.DataFormat;
import com.zzy.util.Log;
import com.zzy.util.gson.GsonHelper;
import com.zzy.webapi.ApiResponse;
import com.zzy.webapi.ApiResult;


public class JsonApiResponse extends ApiResponse {

	private static final Log log = Log.getLogger(JsonApiResponse.class);
	
	private static final String CONTENT_TYPE_APPLICATION_JSON_UTF8 = "application/json; charset=utf-8";

	public JsonApiResponse() {
	}

	@Override
	public String getContentType() {
		return CONTENT_TYPE_APPLICATION_JSON_UTF8;
	}

	@Override
	public DataFormat getFormat() {
		return DataFormat.JSON;
	}

	@Override
	public Reader getReader() {
		return new StringReader(formatResult(getResult()));
	}

	public String formatResult(ApiResult result){
		long start = System.currentTimeMillis();
        Class resultType = null;
        Object jsonResult = result.getResult();
        if(jsonResult == null)
        {
            log.debug("result.getResult() is null");
            Object array[] = result.getResults();
            if(array == null)
                log.debug("result.getResults() is null");
            else
                resultType = ((Object) (array)).getClass().getComponentType();
            jsonResult = ((Object) (array));
        } else
        {
            resultType = jsonResult.getClass();
            log.debug("formatResult with type: {0} - {1}", new Object[] {
                resultType, TypeToken.get(resultType).getType()
            });
        }
        Gson gson = GsonHelper.buildGson();
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.add("timestamp", gson.toJsonTree(Long.valueOf(Calendar.getInstance().getTime().getTime())));
        jsonResponse.add("error", gson.toJsonTree(result.getError()));
        jsonResponse.add("paging", gson.toJsonTree(result.getPaging()));
        jsonResponse.add("result", gson.toJsonTree(jsonResult));
        String jsonData = GsonHelper.buildGson().toJson(jsonResponse);
        log.debug("jsonData: {0}", new Object[] {
            jsonData
        });
        log.debug("format use time : {0}",(System.currentTimeMillis()-start));
        return jsonData;
    }

}
