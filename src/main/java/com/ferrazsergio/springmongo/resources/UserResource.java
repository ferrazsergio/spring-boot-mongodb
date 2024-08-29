package com.ferrazsergio.springmongo.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable String id) {
		try {
			User obj = userService.findById(id);
			return ResponseEntity.ok().body(new UserDTO(obj));
		} catch (Exception e) {
			return ResponseEntity.status(500).body(null); 
		}
	}
	
	@PostMapping(value = "/insert")
	public ResponseEntity<UserDTO> insertUser(@RequestBody UserDTO userDto) {
		try {
			User obj = userService.fromDTO(userDto);
			obj = userService.insert(obj);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id})").buildAndExpand(obj.getId()).toUri();
			return ResponseEntity.created(uri).build();
		} catch (Exception e) {
			return ResponseEntity.status(500).body(null); 
		}
	}
}
