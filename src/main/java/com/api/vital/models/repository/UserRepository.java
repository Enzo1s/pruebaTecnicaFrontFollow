package com.api.vital.models.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.api.vital.models.entity.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, String>{
	
	public User findByUsername(String term);

}
