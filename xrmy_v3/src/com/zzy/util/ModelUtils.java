package com.zzy.util;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zzy.model.supermodel.ModelObject;


public class ModelUtils {
	private static final String[] PERCENT_FIELD = new String[] { "cvr0rate" };
	private static final Pattern domainPattern = Pattern
			.compile(
					"(?<=http[s]://|[\\w]*)([^.]*?)\\.(com|cn|net|org|biz|info|cc|tv)([^(/|?)]*)",
					Pattern.CASE_INSENSITIVE);
	

	/**
	 * get paging
	 * 
	 * @return
	 */
	public static Paging getPaging() {
		Paging paging = new Paging();
		paging.setCurrentPage(1);
		paging.setPageSize(10);
		return paging;
	}

	
	
	public static <T extends ModelObject<Long>> Long[] getIds(
			Collection<T> datas) {
		if (null == datas)
			throw new IllegalArgumentException("data is null");
		Long[] ids = new Long[datas.size()];
		int index = 0;
		for (T instance : datas) {
			ids[index] = instance.getId();
			index++;
		}
		return ids;
	}

	public static <T extends ModelObject<Long>> Long[] getIds(T[] datas) {
		if (null == datas)
			throw new IllegalArgumentException("datas is null.");
		Long[] ids = new Long[datas.length];
		int index = 0;
		for (T instance : datas) {
			ids[index] = instance.getId();
			index++;
		}
		return ids;
	}

	public String md5PassWord(String passWord) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(passWord.getBytes());
		byte b[] = md.digest();
		int i;
		StringBuffer buf = new StringBuffer("");
		for (int offset = 0; offset < b.length; offset++) {
			i = b[offset];
			if (i < 0)
				i += 256;
			if (i < 16)
				buf.append("0");
			buf.append(Integer.toHexString(i));
		}
		return buf.toString();
	}


	
	/**
	 * append(Here describes this method function with a few words)
	 * 
	 * 追加字符
	 * 
	 * @param bufferInfo
	 * @param info
	 * 
	 */
	public void append(StringBuffer bufferInfo, Object info) {
		if (null != bufferInfo) {
			if (StringUtils.hasLength(bufferInfo))
				bufferInfo.append(StringUtils.COMMA);
			bufferInfo.append(info);
		}
	}

	/**
	 * aesInfo
	 * 
	 * 使用Aes加密
	 * 
	 * @param secretKey
	 * @param info
	 * @return
	 * 
	 * String
	 */
	public String aesInfo(String info, String secretKey) {
		if (!StringUtils.hasLength(info))
			return info;
		else {
			if (!StringUtils.hasLength(secretKey))
				return info;
			else {
				return AES.encrypttoStr(info, secretKey);
			}
		}
	}

	
	// }

	/**
	 * 获取时间段内各个时间戳("yyyy-MM-dd")
	 */
	public List<String> getDateList(Calendar startTime, Calendar endTime) {
		if (startTime == null || endTime == null
				|| startTime.getTimeInMillis() > endTime.getTimeInMillis()) {
			return new ArrayList<String>();
		}
		List<String> datas = new ArrayList<String>();
		Long endTimeInMillus = endTime.getTimeInMillis();
		Long startTimeInMillus = startTime.getTimeInMillis();
		Long timeBuff = endTimeInMillus - startTimeInMillus;
		Integer dayInteger = Integer.valueOf(String
				.valueOf((timeBuff / (1000 * 60 * 60 * 24))));
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		for (Long i = 0L; i <= dayInteger; i++) {
			Long currentTimeInMillis = Long.valueOf(1000 * 60 * 60 * 24 * i);
			Calendar currentTimeCal = Calendar.getInstance();
			currentTimeCal.setTimeInMillis(startTimeInMillus
					+ currentTimeInMillis);
			datas.add(dateFormat.format(currentTimeCal.getTime()));
		}
		return datas;
	}

	/**
	 * 舍去多余小数位(保留两位小数)
	 * 
	 * @param d
	 * @return
	 */
	public static double getRoundValue(double d) {
		String value = String.valueOf(d);
		BigDecimal b = new BigDecimal(d);
		if (value.indexOf(".") != -1
				&& value.substring(value.indexOf(".") + 1).length() > 2) {
			// 四舍五入
			d = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		// else{
		// //直接舍去
		// d=b.setScale(2,BigDecimal.ROUND_DOWN).doubleValue();
		// }
		return d;
	}

	/**
	 * 获取主域名
	 * 
	 * @param domain
	 * @return
	 */
	public static String getMainDomain(String domain) {
		if (!StringUtils.hasLength(domain))
			return domain;
		try {
			Matcher matcher = domainPattern.matcher(domain);
			if (matcher.find()) {
				String value = matcher.group(1);
				if (StringUtils.hasLength(matcher.group(2))) {
					value += "." + matcher.group(2) + matcher.group(3);
				}
				return value.toLowerCase();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return domain;
	}

	/**
	 * 比较主域名是否相同
	 * 
	 * @param domain1
	 * @param domain2
	 * @return
	 */
	public static boolean isHasSameMainDomain(String domain1, String domain2) {
		if (StringUtils.hasLength(domain1) && StringUtils.hasLength(domain2)) {
			if (getMainDomain(domain1).equalsIgnoreCase(getMainDomain(domain2))) {
				return true;
			}
		}
		return false;
	}

	
}
