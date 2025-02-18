package com.api.vital.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.api.vital.models.entity.Product;
import com.api.vital.models.entity.User;
import com.api.vital.service.ProductService;
import com.api.vital.service.jwt.JWTService;

@RestController
@RequestMapping(path = "product")
public class ProductController extends CommonController<Product, ProductService> {

	@Autowired
	private JWTService jwt;
	
	@GetMapping("/type")
	public ResponseEntity<?> getForType(@RequestParam String type,
			@RequestHeader(value = "Authorization") String token) {
		User user = jwt.getUser(token);
		if(user == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		return ResponseEntity.ok(service.findByType(type));
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> update(@Valid @RequestBody Product model, 
			BindingResult result,
			@RequestHeader(value = "Authorization") String token) {
		User user = jwt.getUser(token);
		if(user == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		if(result.hasErrors()) {
			return this.validar(result);
		}
		Product product =service.update(model);
		if(product != null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(product));
	}
	
	@GetMapping("filter/name")
	public ResponseEntity<?> filterName(@RequestParam String term, 
			@RequestHeader(value = "Authorization") String token) {
		User user = jwt.getUser(token);
		if(user == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		return ResponseEntity.ok(service.findByName(term));
	}
	
	@GetMapping("filter/type")
	public ResponseEntity<?> filterType(@RequestParam String term, 
			@RequestHeader(value = "Authorization") String token) {
		User user = jwt.getUser(token);
		if(user == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		List<Product> products = service.findByType(term);
		return ResponseEntity.ok().body(products);
	}
	
	@PostMapping("/save/file")
	public ResponseEntity<?> saveFileProduct(@RequestParam MultipartFile file, @RequestParam String type){
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
	
}
