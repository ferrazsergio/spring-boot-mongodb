package com.ferrazsergio.springmongo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ferrazsergio.springmongo.domain.User;
import com.ferrazsergio.springmongo.repository.UserRepository;

@Service
public class UserService {
	
	UserRepository userRepository;
	
	@Autowired
	public UserService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}
	
	public List<User> findAll(){	
		return userRepository.findAll();
		
     }
	
	
}