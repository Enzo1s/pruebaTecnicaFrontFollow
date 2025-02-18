package com.api.vital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.vital.models.entity.User;
import com.api.vital.service.UserService;
import com.api.vital.service.jwt.JWTService;

@RestController
@RequestMapping(path = "user")
public class UserController extends CommonController<User, UserService>{
	
	@Autowired
	private JWTService jwtService;
	
	@PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User model) {

        String token;
		try {
			token = jwtService.createToken(model);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);			
		}
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

}
