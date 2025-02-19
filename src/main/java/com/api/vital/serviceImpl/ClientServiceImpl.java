package com.api.vital.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.api.vital.models.entity.Client;
import com.api.vital.models.repository.ClientRepository;
import com.api.vital.service.ClientService;

@Service
public class ClientServiceImpl extends CommonServiceImpl<Client, ClientRepository> implements ClientService {

	@Override
	public List<Client> findByName(String term) {
		return repository.findByName(term);
	}

	@Override
	public List<Client> findByProductsId(List<String> ids) {
		return repository.findAllByProductsId(ids);
	}

	@Override
	public List<Client> findByproductsName(List<String> term) {
		return repository.findAllByProductsName(term);
	}

}
