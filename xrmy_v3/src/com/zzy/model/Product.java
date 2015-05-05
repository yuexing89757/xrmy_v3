package com.zzy.model;

import com.zzy.enums.ProductType;
import com.zzy.model.supermodel.ModelObject;



public class Product extends ModelObject<Long>{

	private String name;
	private Double price;
	private String photo;
	private String describtion;
	private ProductType productType;
	private String videoTitle;
	private String videoURL;
	private String videoPhoto;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getDescribtion() {
		return describtion;
	}
	public void setDescribtion(String describtion) {
		this.describtion = describtion;
	}
	public ProductType getProductType() {
		return productType;
	}
	public void setProductType(ProductType productType) {
		this.productType = productType;
	}
	public String getVideoTitle() {
		return videoTitle;
	}
	public void setVideoTitle(String videoTitle) {
		this.videoTitle = videoTitle;
	}
	public String getVideoURL() {
		return videoURL;
	}
	public void setVideoURL(String videoURL) {
		this.videoURL = videoURL;
	}
	public String getVideoPhoto() {
		return videoPhoto;
	}
	public void setVideoPhoto(String videoPhoto) {
		this.videoPhoto = videoPhoto;
	}
	
	
	
	
}
