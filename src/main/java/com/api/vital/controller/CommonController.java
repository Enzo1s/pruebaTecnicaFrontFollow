package com.api.vital.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.api.vital.service.CommonService;

@CrossOrigin(origins = "*")
public class CommonController<E, S extends CommonService<E>> {
	
	@Autowired
	protected S service;
	
	@GetMapping
	public ResponseEntity<?> list() {
		return ResponseEntity.ok().body(service.findAll());
	}
	
	@GetMapping("/paginated")
	public ResponseEntity<?> list(Pageable pageable) {
		return ResponseEntity.ok().body(service.findAll(pageable));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> get(@PathVariable String id){
		Optional<E> o = service.findById(id);
		if(o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(o.get());
	}

	@PostMapping("/create")
	public ResponseEntity<?> create( @RequestBody E entity) {
		E entityDb = service.save(entity);
		return ResponseEntity.status(HttpStatus.CREATED).body(entityDb);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable String id) {
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping("/file")
	private ResponseEntity<?> saveFile(@RequestParam MultipartFile file, @RequestParam String type){
		String nameFile;
		try {
			nameFile = service.saveFile(file, type);
			return ResponseEntity.status(HttpStatus.CREATED).body(nameFile);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("server error");
		}
	}
	
	protected ResponseEntity<?> validar(BindingResult result) {
		Map<String, Object> errores = new HashMap<>();
		result.getFieldErrors().forEach(err -> {
			errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
		});
		return ResponseEntity.badRequest().body(errores);
	}
}
