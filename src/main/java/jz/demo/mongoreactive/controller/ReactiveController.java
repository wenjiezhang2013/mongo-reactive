package jz.demo.mongoreactive.controller;

import jz.demo.mongoreactive.repository.Movie;
import jz.demo.mongoreactive.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;


@RestController
public class ReactiveController {

    @Autowired
    private MovieRepository movieRepository;

    @RequestMapping("/")
    Flux<Movie> findAll() {
        return movieRepository.findAll();

    }
}
