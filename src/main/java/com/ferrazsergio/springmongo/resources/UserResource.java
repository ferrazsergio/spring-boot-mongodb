package com.ferrazsergio.springmongo.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ferrazsergio.springmongo.domain.Post;
import com.ferrazsergio.springmongo.domain.User;
import com.ferrazsergio.springmongo.dto.PostDTO;
import com.ferrazsergio.springmongo.dto.UserDTO;
import com.ferrazsergio.springmongo.services.UserService;
import com.ferrazsergio.springmongo.services.exception.ObjectNotFoundException;

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
			List<UserDTO> listDto = list.stream().map(UserDTO::new).toList();
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
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable String id) {
	    try {
	        userService.deleteById(id);
	        return ResponseEntity.noContent().build();
	    } catch (ObjectNotFoundException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@RequestBody UserDTO userDto, @PathVariable String id) {
	    try {
	        User obj = userService.fromDTO(userDto); // Converte DTO para entidade
	        obj.setId(id); // Define o ID para garantir que o objeto correto será atualizado
	        userService.update(obj); // Atualiza o objeto no banco de dados
	        return ResponseEntity.noContent().build(); // Retorna 204 No Content se bem-sucedido
	    } catch (ObjectNotFoundException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Retorna 404 se o objeto não for encontrado
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Retorna 500 se houver outro erro
	    }
	}
	
	@GetMapping(value = "/{id}/posts")
	public ResponseEntity<List<PostDTO>> findPosts(@PathVariable String id) {
	    try {
	        User obj = userService.findById(id);
	        List<Post> posts = obj.getPosts(); // Recupera os posts do usuário
	        List<PostDTO> postDTOs = posts.stream().map(PostDTO::new).toList(); // Converte os posts para DTOs
	        return ResponseEntity.ok().body(postDTOs);
	    } catch (Exception e) {
	        return ResponseEntity.status(500).body(null); 
	    }
	}



}
