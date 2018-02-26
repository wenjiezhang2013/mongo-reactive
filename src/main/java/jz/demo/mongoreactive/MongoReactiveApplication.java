package jz.demo.mongoreactive;

import jz.demo.mongoreactive.repository.Movie;
import jz.demo.mongoreactive.repository.MovieRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class MongoReactiveApplication {


	public static void main(String[] args) {
		SpringApplication.run(MongoReactiveApplication.class, args);
	}

	@Bean
	CommandLineRunner demo(MovieRepository repository) {
		return args -> {
			repository.deleteAll().subscribe(null, null, () ->
					Stream.of("Test1, Test2, Test3".split(","))
							.map(name -> new Movie(UUID.randomUUID().toString(), name))
							.forEach(m -> repository.save(m).subscribe(System.out::println)));
		};
	}
}

