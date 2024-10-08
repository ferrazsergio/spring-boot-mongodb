package com.ferrazsergio.springmongo.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ferrazsergio.springmongo.domain.User;
import com.ferrazsergio.springmongo.dto.UserDTO;
import com.ferrazsergio.springmongo.repository.UserRepository;
import com.ferrazsergio.springmongo.services.exception.ObjectNotFoundException;

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
	
	public User findById(String id) {
			Optional<User> obj = userRepository.findById(id);
			return obj.orElseThrow(() -> new ObjectNotFoundException("User with ID " + id + " not found", obj));
	}
	
	public User insert (User obj) {
		return userRepository.insert(obj);
	}
	
	public void deleteById(String id) {
		findById(id);
		userRepository.deleteById(id);
	}
	
	public User update(User newObj) {
		try {
			Optional<User> newUser = userRepository.findById(newObj.getId());
			User user = newUser.get();
			updateData(user, newObj);
			return userRepository.save(user);
		} catch (NoSuchElementException e) {
			throw new ObjectNotFoundException("Object not found.", newObj);
		}
	}
	
	// AUXILIAR METHOD FOR USER UPDATE
	private void updateData(User user, User newObj) {
		user.setName(newObj.getName());
		user.setEmail(newObj.getEmail());
		
	}

	public User fromDTO(UserDTO objDto) {
		return new User(objDto.getId(),objDto.getName(),objDto.getEmail());
	}
}
