package jz.demo.mongoreactive;

import jz.demo.mongoreactive.repository.Movie;
import jz.demo.mongoreactive.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.support.DefaultServerCodecConfigurer;

import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class MongoReactiveApplication {

	@Autowired
	private ApplicationContext applicationContext;


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

	@Bean
	@Order(-2)
	CustomErrorWebExceptionHandler globalErrorHandler() {
		return new CustomErrorWebExceptionHandler(applicationContext, new DefaultServerCodecConfigurer());
	}
}

