package com.ferrazsergio.springmongo.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ferrazsergio.springmongo.domain.Post;
import com.ferrazsergio.springmongo.dto.PostDTO;
import com.ferrazsergio.springmongo.services.PostService;

@RestController
public class PostResource {

	PostService postService;

	@Autowired
	public PostResource(PostService postService) {
		this.postService = postService;
	}

	@GetMapping(value = "{id}")
	public ResponseEntity<PostDTO> findById(@PathVariable String id) {
		try {
			Post post = postService.findbyId(id);
			return ResponseEntity.ok().body(new PostDTO(post));
		} catch (Exception e) {
			return ResponseEntity.status(500).body(null);
		}
	}
}
