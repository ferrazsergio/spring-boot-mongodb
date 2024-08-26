package com.ferrazsergio.springmongo.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.ferrazsergio.springmongo.domain.User;
import com.ferrazsergio.springmongo.repository.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner{
	
	UserRepository userRepository;
	
	@Autowired
	public Instantiation(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		
		User maria = new User(null, "Maria Brown", "maria@gmail.com");
		User alex = new User(null, "Alex Green", "alex@gmail.com");
		User bob = new User(null, "Bob Grey", "bob@gmail.com");	
		
		userRepository.saveAll(Arrays.asList(maria, alex, bob));

	}

}
