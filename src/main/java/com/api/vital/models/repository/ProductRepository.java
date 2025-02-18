package com.api.vital.models.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.api.vital.models.entity.Product;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, String>{

	public List<Product> findByName(String term);
	
	public List<Product> findByType(String term);
	
	public Optional<Product> findById(String id);
	
	public Iterable<Product> findAllByOrderByIdAsc();
	
	public Page<Product> findAllByOrderByIdAsc(Pageable pageable);
}
