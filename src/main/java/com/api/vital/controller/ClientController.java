package com.api.vital.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.vital.models.entity.Client;
import com.api.vital.models.entity.User;
import com.api.vital.service.ClientService;
import com.api.vital.service.jwt.JWTService;

@RestController
@RequestMapping(path = "client")
public class ClientController extends CommonController<Client, ClientService>{

	@Autowired
	private JWTService jwt;
	
	@GetMapping("filter/name")
	public ResponseEntity<?> filterName(@RequestParam String term, 
			@RequestHeader(value = "Authorization") String token) {
		User user = jwt.getUser(token);
		if(user == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		return ResponseEntity.ok(service.findByName(term));
	}
	
	@GetMapping("filter/product/ids")
	public ResponseEntity<?> filterByProductsId(@RequestParam List<String> ids, 
			@RequestHeader(value = "Authorization") String token) {
		User user = jwt.getUser(token);
		if(user == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		List<Client> clients = service.findByProductsId(ids);
		return ResponseEntity.ok().body(clients);
	}
	
	@GetMapping("filter/product")
	public ResponseEntity<?> filterByProductsName(@RequestParam List<String> term, 
			@RequestHeader(value = "Authorization") String token) {
		User user = jwt.getUser(token);
		if(user == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		List<Client> clients = service.findByproductsName(term);
		return ResponseEntity.ok().body(clients);
	}
}
