package com.api.vital.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.vital.models.entity.User;
import com.api.vital.models.repository.UserRepository;

@Service
public class UserServiceImpl extends CommonServiceImpl<User, UserRepository> implements UserService {

	@Override
	@Transactional(readOnly = true)
	public User findByUsername(String term) {
		return repository.findByUsername(term);
	}
	
	@Override
	public List<User> findAll() {
		return (List<User>) repository.findAll();
	}

	@Override
	public Optional<User> findById(String id) {
		return repository.findById(id);
	}

	@Override
	public User save(User user) {
		User exist = repository.findByUsername(user.getUsername());
		if(exist != null)
			return null;
		return repository.save(user);
	}

}
