package com.api.vital.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.vital.models.entity.Usuario;
import com.api.vital.service.UsuarioService;
import com.api.vital.service.jwt.JWTService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "usuario")
public class UsuarioController {
	
	@Autowired
	protected UsuarioService service;
	
	@Autowired
	private JWTService jwtService;
	
	@PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Usuario model,
                                        HttpServletResponse response) {

        String token;
		try {
			token = jwtService.crearToken(model);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
	
	@GetMapping
	public ResponseEntity<?> listar() {
		return ResponseEntity.ok().body(service.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> ver(@PathVariable String id){
		Optional<Usuario> o = service.findById(id);
		if(o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(o.get());
	}

	@PostMapping("/crear")
	public ResponseEntity<?> crear( @RequestBody Usuario entity) {
	
		Usuario entityDb = service.save(entity);
		if(entityDb == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario existente");
		return ResponseEntity.status(HttpStatus.CREATED).body(entityDb);
	}
	

}
