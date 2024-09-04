package com.ferrazsergio.springmongo.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ferrazsergio.springmongo.domain.Post;
import com.ferrazsergio.springmongo.dto.PostDTO;
import com.ferrazsergio.springmongo.resources.util.URL;
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
	

	@GetMapping(value = "/titlesearch")
	public ResponseEntity<List<Post>> findByTitle(@RequestParam(value = "text", defaultValue = "") String text) {
		try {
			text = URL.decodeParam(text);
			List<Post> list = postService.findByTitle(text);
			return ResponseEntity.ok().body(list);
		} catch (Exception e) {
			return ResponseEntity.status(500).body(null);
		}
	
}
}