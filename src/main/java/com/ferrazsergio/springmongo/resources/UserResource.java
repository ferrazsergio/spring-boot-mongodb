package com.ferrazsergio.springmongo.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ferrazsergio.springmongo.domain.User;
import com.ferrazsergio.springmongo.dto.UserDTO;
import com.ferrazsergio.springmongo.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

	UserService userService;

	@Autowired
	public UserResource(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public ResponseEntity<List<UserDTO>> findAll() {
		try {
			List<User> list = userService.findAll();
			List<UserDTO> listDto = list.stream().map(user -> new UserDTO(user)).toList();
			return ResponseEntity.ok().body(listDto);
		} catch (Exception e) {
			return ResponseEntity.status(500).body(null); 
		}
	}
}
