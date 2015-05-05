package com.zzy.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigManager {
	public static String commonsFile = "commons.properties";
	private static String[] fileNames = new String[]{commonsFile};
	private final static Log log = Log.getLogger(ConfigManager.class);
	private static Map<String,ConfigManager> configMap =  new HashMap<String,ConfigManager>();
	private File file = null;
	private long lastModifiedTime = 0;
	private Properties property = null;
	private String fileName = null;
	private ConfigManager(String fileName){
		this.fileName = fileName;
		URL url = ConfigManager.class.getClassLoader().getResource(fileName);
		file = new File(url.getPath());
		lastModifiedTime = file.lastModified();
		if(lastModifiedTime == 0){
			log.error("{0} | file does not exit.",fileName );
		}
		property = new Properties();
		try {
			property.load(new FileInputStream(file));
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
	}
	
	//initialize config file.
	static {
		for(String fileName : fileNames){
			ConfigManager cm = new ConfigManager(fileName);
			configMap.put(fileName, cm);
		}
	}
	
	//get a file 
	public synchronized  static ConfigManager  getInstance(String fileName){
		if(configMap.containsKey(fileName)){
			return configMap.get(fileName);
		}else{
			ConfigManager cm = new ConfigManager(fileName);
			configMap.put(fileName, cm);
			return configMap.get(fileName);
		}
	}
	
	// get value from input key. if key is not exists then return null.
	public Object getConfigItem(String key,String defaultValue){
		Long newTime = this.file.lastModified();
		if(newTime == 0){
			log.error("file is not exists");
		}else if(newTime > this.lastModifiedTime){
			this.property.clear();
			try {
				URL url = ConfigManager.class.getClassLoader().getResource(fileName);
				this.property.load(new FileInputStream(url.getPath()));
			} catch (Exception e) {
				log.error(e);
			}
		}
		this.lastModifiedTime = newTime;
		Object obj = this.property.get(key);
		if(obj == null){
			return defaultValue;
		}else{
			return obj;
		}
	}
	
	//set properties value.
	public void putConfigItem(String key,String value){
		if(!StringUtils.hasText(key)){
			return;
		}
		this.property.setProperty(key, value);
		try {
			this.property.store(new FileOutputStream(file), null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// get value from input key. if key is not exists then return null.
	public Object getConfigItem(String key){
		Long newTime = this.file.lastModified();
		if(newTime == 0){
			log.error("file is not exists");
		}else if(newTime > this.lastModifiedTime){
			this.property.clear();
			try {
				URL url = ConfigManager.class.getClassLoader().getResource(fileName);
				this.property.load(new FileInputStream(url.getPath()));
			} catch (Exception e) {
				log.error(e);
			}
		}
		this.lastModifiedTime = newTime;
		Object obj = this.property.get(key);
		if(obj == null){
			return null;
		}else{
			return obj;
		}
	}
	
	public static String readAes() throws Exception{
		String fileName = (String)ConfigManager.getInstance(ConfigManager.commonsFile).getConfigItem("AES_FILE");
		if(!StringUtils.hasLength(fileName)){
			throw new Exception("AES_FILE not found.");
		}
		
		File file = new File(fileName);
		if(!file.exists()){
			throw new Exception(fileName +" not exist.");
		}
		
		if(!file.canRead()){
			throw new Exception(fileName +" can not read.");
		}
		
		FileReader reader = null;
		try {
			reader = new FileReader(file);
			BufferedReader br = new BufferedReader(reader);
			String s1 = null;
			StringBuffer result = new StringBuffer();
			boolean isHas = true;
			while(isHas){
				s1 = br.readLine();
				if(s1==null){
					isHas = false;
					break;
				}
				result.append(s1);
			}
//				return s1;
			br.close();
			reader.close();
			return result.toString();
		} catch (FileNotFoundException e) {
			log.error(e);
			throw e;
		} catch (IOException e) {
			log.error(e);
			throw e;
		}
	}
	
	public static void main(String[] args){
		ConfigManager cm = ConfigManager.getInstance(commonsFile);
		System.out.println(cm.getConfigItem("name", "ss"));
		System.out.println();
		System.out.println(cm.getConfigItem("name", ""));
	}
	
}
