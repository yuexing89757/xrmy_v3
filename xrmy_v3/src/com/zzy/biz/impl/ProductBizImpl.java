package com.zzy.biz.impl;

import java.util.List;

import com.zzy.biz.IProductBiz;
import com.zzy.dao.ProductDao;
import com.zzy.dao.impl.ProductDaoImpl;
import com.zzy.enums.ProductType;
import com.zzy.model.Product;
import com.zzy.util.Paging;



public class ProductBizImpl implements IProductBiz{

	ProductDao  productDao=null;
	
	
	public ProductBizImpl() {
		super();
		productDao=new ProductDaoImpl();
		
	}

	public List<Product> findProduct(ProductType productType,Paging paging) {
		List<Product> list=productDao.findByPage(productType, paging);
		return list;
	}

}
