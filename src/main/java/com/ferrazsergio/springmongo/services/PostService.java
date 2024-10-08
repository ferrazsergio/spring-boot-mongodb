package com.ferrazsergio.springmongo.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ferrazsergio.springmongo.domain.Post;
import com.ferrazsergio.springmongo.domain.User;
import com.ferrazsergio.springmongo.repository.PostRepository;
import com.ferrazsergio.springmongo.services.exception.ObjectNotFoundException;

@Service
public class PostService {

	PostRepository postRepository;

	@Autowired
	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	public Post findbyId(String id) {
		Optional<Post> obj = postRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Post with ID " + id + " not found", obj));
	}
	
	public List<Post> findByTitle(String text){
		return postRepository.searchTitle(text);
	}
	
	public List<Post> fullSearch(String text, Date minDate, Date maxDate) {
		maxDate = new Date(maxDate.getTime() + 24 * 60 * 60 * 1000);
		return postRepository.fullSearch(text, minDate, maxDate);
	}
}

