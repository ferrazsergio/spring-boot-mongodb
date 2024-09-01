package com.ferrazsergio.springmongo.config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.ferrazsergio.springmongo.domain.Post;
import com.ferrazsergio.springmongo.domain.User;
import com.ferrazsergio.springmongo.dto.CommentDTO;
import com.ferrazsergio.springmongo.dto.AuthorDTO;
import com.ferrazsergio.springmongo.repository.PostRepository;
import com.ferrazsergio.springmongo.repository.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner {

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

	static {
		DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT"));
	}

	private final UserRepository userRepository;
	private final PostRepository postRepository;

	@Autowired
	public Instantiation(UserRepository userRepository, PostRepository postRepository) {
		this.userRepository = userRepository;
		this.postRepository = postRepository;
	}

	@Override
	public void run(String... args) {
		try {
			initializeDatabase();
		} catch (ParseException e) {
			e.printStackTrace(); 
		}
	}

	private void initializeDatabase() throws ParseException {
		userRepository.deleteAll();
		postRepository.deleteAll();

		User maria = new User(null, "Maria Brown", "maria@gmail.com");
		User alex = new User(null, "Alex Green", "alex@gmail.com");
		User bob = new User(null, "Bob Grey", "bob@gmail.com");

		userRepository.saveAll(Arrays.asList(maria, alex, bob));

		Post post1 = createPost("21/03/2018", "Partiu viagem", "Vou viajar para São Paulo. Abraços !", maria);
		Post post2 = createPost("23/03/2018", "Bom dia", "Acordei feliz hoje !", maria);

		CommentDTO c1 = createComment("Boa viagem mano!", "21/03/2018", alex);
		CommentDTO c2 = createComment("Aproveite", "22/03/2018", bob);
		CommentDTO c3 = createComment("Tenha um ótimo dia!", "23/03/2018", alex);

		post1.addComments(Arrays.asList(c1, c2));
		post2.addComments(Arrays.asList(c3));

		postRepository.saveAll(Arrays.asList(post1, post2));

		maria.addPosts(Arrays.asList(post1, post2));
		userRepository.save(maria);
	}

	private Post createPost(String date, String title, String body, User author) throws ParseException {
		return new Post(null, DATE_FORMAT.parse(date), title, body, new AuthorDTO(author));
	}

	private CommentDTO createComment(String text, String date, User author) throws ParseException {
		return new CommentDTO(text, DATE_FORMAT.parse(date), new AuthorDTO(author));
	}
}
