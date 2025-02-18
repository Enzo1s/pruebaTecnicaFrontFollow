package com.api.vital.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;


public interface CommonService<E> {

	public Iterable<E> findAll();
	
	public Page<E> findAll(Pageable pageable);
	
	public Optional<E> findById(String id);
	
	public E save (E entity);
	
	public void deleteById (String id);
	
	public String saveFile(MultipartFile file, String path) throws IOException;
	
}
