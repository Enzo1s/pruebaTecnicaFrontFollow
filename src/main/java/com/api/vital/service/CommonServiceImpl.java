package com.api.vital.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

public class CommonServiceImpl<E, R extends PagingAndSortingRepository<E, String>> implements CommonService<E> {

	@Autowired
	protected R repository;
	
	@Value("${path}")
	public static String path;
	
	@Override
	@Transactional(readOnly = true)
	public Iterable<E> findAll() {
		return repository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<E> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	@Transactional (readOnly = true)
	public Optional<E> findById(String id) {
		return repository.findById(id);
	}

	@Override
	@Transactional
	public E save(E entity) {
		return repository.save(entity);
	}

	@Override
	@Transactional
	public void deleteById(String id) {
		repository.deleteById(id);
		
	}

	@Override
	public String saveFile(MultipartFile file, String type) throws IllegalArgumentException, IOException {
		if (file.isEmpty()) 
			throw new IllegalArgumentException("File is empty.");

	    Path directoryPath = Paths.get(path + type);
	    if (!Files.exists(directoryPath))
	    	Files.createDirectories(directoryPath);

	    String namefile = file.getOriginalFilename();
	    Path filePath = directoryPath.resolve(namefile);

	    Files.copy(file.getInputStream(), filePath);

	    return namefile;
	}

}
