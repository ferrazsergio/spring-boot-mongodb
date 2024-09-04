package com.ferrazsergio.springmongo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ferrazsergio.springmongo.domain.Post;

@Repository
public interface PostRepository extends MongoRepository<Post,String>{
		List<Post> findByTitleContaingIgnoreCase(String text);
}
