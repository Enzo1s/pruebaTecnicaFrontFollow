package com.api.vital.service;

import java.util.List;
import java.util.Optional;

import com.api.vital.models.entity.User;

public interface UserService extends CommonService<User>{
	
	public User save(User user);
	
	public User findByUsername(String term);
	
	public List<User> findAll();
	
	public Optional<User> findById(String id);

}
