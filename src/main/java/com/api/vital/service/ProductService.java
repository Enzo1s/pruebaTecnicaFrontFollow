package com.api.vital.service;

import java.util.List;
import java.util.Optional;

import com.api.vital.models.entity.Product;

public interface ProductService extends CommonService<Product> {
	
	public List<Product> findByName(String term);
	
	public List<Product> findByType(String term);
		
	public Optional<Product> findAllById(String id);
	
	public Product update(Product product);

}
