package com.api.vital.service;

import java.util.List;

import com.api.vital.models.entity.Client;

public interface ClientService extends CommonService<Client> {
	
	public List<Client> findByName(String term);
	
	public List<Client> findByProductsId(List<String> ids);
	
	public List<Client> findByproductsName(List<String> term);
	
}
