package com.api.vital.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.api.vital.models.entity.Client;

@Repository
public interface ClientRepository extends PagingAndSortingRepository<Client, String>{
	
	public List<Client> findByName(String term);
	
	@Query("SELECT c FROM Client c, Product p WHERE "
			+ "c.products IS NOT NULL AND "
			+ "p.id IN :ids AND "
			+ "p IN ELEMENTS(c.products)")
	public List<Client> findAllByProductsId(List<String> ids);
	
	@Query("SELECT c FROM Client c, Product p WHERE "
			+ "c.products IS NOT NULL AND "
			+ "p.name IN :names AND "
			+ "p IN ELEMENTS(c.products)")
	public List<Client> findAllByProductsName(List<String> names);

}
