package com.zzy.util.gson;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.CustomizedMapTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.LongSerializationPolicy;

public class GsonHelper {
	
	public static boolean QUOTE_LONG = false;

	public static Gson buildGson() {
		Gson gson = new GsonBuilder()
				.excludeFieldsWithModifiers(Modifier.STATIC,
						Modifier.TRANSIENT, Modifier.VOLATILE)
				.registerTypeAdapter(Double.class, new DoubleSerializer())
				.registerTypeAdapter(Long.class, new LongSerializer())
				.registerTypeAdapter(Date.class, new DateTimeSerializer())
				.registerTypeAdapter(Calendar.class, new CalendarSerializer())
				.registerTypeAdapter(GregorianCalendar.class,
						new CalendarSerializer())
				.registerTypeAdapter(GsonObject.class,
						new GsonObjectStringSerializer())
				.registerTypeAdapter(Map.class, new CustomizedMapTypeAdapter())
				.registerTypeAdapter(HashMap.class,
						new CustomizedMapTypeAdapter())
				.setLongSerializationPolicy(LongSerializationPolicy.DEFAULT)
				.serializeNulls().setPrettyPrinting().create();
		return gson;
		/*
		 * excludes.add("hibernateLazyInitializer");
		 * excludes.add("getHibernateLazyInitializer");
		 */
	}

	private static class GsonObjectStringSerializer implements
			JsonSerializer<GsonObject>, JsonDeserializer<GsonObject> {
		public JsonElement serialize(GsonObject src, Type typeOfSrc,
				JsonSerializationContext context) {
			return src.getElement();
		}

		public GsonObject deserialize(JsonElement element, Type typeOfSrc,
				JsonDeserializationContext context) throws JsonParseException {
			return new GsonObject(element.getAsJsonObject());
		}
	}

	private static class DoubleSerializer implements JsonSerializer<Double>,
			JsonDeserializer<Double> {
		public JsonElement serialize(Double src, Type typeOfSrc,
				JsonSerializationContext context) {
			BigDecimal dec = new BigDecimal(src.doubleValue());
			return new JsonPrimitive(dec.toString());
		}

		public Double deserialize(JsonElement element, Type typeOfSrc,
				JsonDeserializationContext context) throws JsonParseException {
			return Double.valueOf(element.getAsString());
		}
	}

	private static class LongSerializer implements JsonSerializer<Long>,
			JsonDeserializer<Long> {
		public JsonElement serialize(Long src, Type typeOfSrc,
				JsonSerializationContext context) {
			JsonPrimitive prim = null;
			if (QUOTE_LONG) {
				BigDecimal dec = new BigDecimal(src.longValue());
				prim = new JsonPrimitive(dec.toString());
			} else {
				prim = new JsonPrimitive(src);
			}
			return prim;
		}

		public Long deserialize(JsonElement element, Type typeOfSrc,
				JsonDeserializationContext context) throws JsonParseException {
			return Long.valueOf(element.getAsString());
		}
	}

	private static class DateTimeSerializer implements JsonSerializer<Date>,
			JsonDeserializer<Date> {
		public JsonElement serialize(Date src, Type typeOfSrc,
				JsonSerializationContext context) {
			JsonPrimitive prim = null;
			if (QUOTE_LONG) {
				BigDecimal dec = new BigDecimal(src.getTime());
				prim = new JsonPrimitive(dec.toString());
			} else {
				prim = new JsonPrimitive(src.getTime());
			}
			return prim;
		}

		public Date deserialize(JsonElement element, Type typeOfSrc,
				JsonDeserializationContext context) throws JsonParseException {
			return new Date(element.getAsLong());
		}
	}

	private static class CalendarSerializer implements
			JsonSerializer<Calendar>, JsonDeserializer<Calendar> {
		public JsonElement serialize(Calendar src, Type typeOfSrc,
				JsonSerializationContext context) {
			JsonPrimitive prim = null;
			if (QUOTE_LONG) {
				BigDecimal dec = new BigDecimal(src.getTimeInMillis());
				prim = new JsonPrimitive(dec.toString());
			} else {
				prim = new JsonPrimitive(src.getTimeInMillis());
			}
			return prim;
		}

		public Calendar deserialize(JsonElement element, Type typeOfSrc,
				JsonDeserializationContext context) throws JsonParseException {
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(element.getAsLong());
			return cal;
		}
	}
}
