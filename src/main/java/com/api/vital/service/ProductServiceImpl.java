package com.api.vital.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.api.vital.models.entity.Product;
import com.api.vital.models.repository.ProductRepository;

@Service
public class ProductServiceImpl extends CommonServiceImpl<Product, ProductRepository> implements ProductService {
	
	@Value("${path}")
	private String pathProduct;
	
	@Override
	@Transactional(readOnly = true)
	public List<Product> findByName(String term) {
		return repository.findByName(term);
	}

	@Override
	public Optional<Product> findAllById(String id) {
		return repository.findById(id);
	}
	
	@Override
	@Transactional
	public void deleteById(String id) {
		super.deleteById(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Iterable<Product> findAll() {
		return repository.findAllByOrderByIdAsc();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<Product> findAll(Pageable pageable) {
		return repository.findAllByOrderByIdAsc(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Product> findByType(String term) {
		return repository.findByType(term);
	}

	@Override
	public Product update(Product model) {
		Optional<Product> o =repository.findById(model.getId());
		if(o.isEmpty()) {
			return null;
		}
		Product product = o.get();
		model.setCreateAt(product.getCreateAt());
		return repository.save(model);
	}
	
	public String saveFile(String id, MultipartFile file, String path) throws IllegalArgumentException, IOException {
		Product product = repository.findById(id).orElseThrow();
		String nameFile = super.saveFile(file, path);
		product.setPathFile(path + nameFile);
		repository.save(product);
		return nameFile;
	}

}
