package com.zzy.dao;

import java.util.List;

import com.zzy.enums.ProductType;
import com.zzy.model.Product;
import com.zzy.util.Paging;



public interface ProductDao {
	public List<Product> findByPage(ProductType productType,Paging paging);

}
