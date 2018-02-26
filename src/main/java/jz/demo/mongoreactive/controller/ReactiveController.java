package jz.demo.mongoreactive.controller;

import javafx.scene.media.Media;
import jz.demo.mongoreactive.repository.Movie;
import jz.demo.mongoreactive.repository.MovieEvent;
import jz.demo.mongoreactive.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.Date;
import java.util.stream.Stream;


@RestController
public class ReactiveController {

    @Autowired
    private MovieRepository movieRepository;

    @RequestMapping("/movie")
    Flux<Movie> findAll() {
        return movieRepository.findAll();

    }

    @RequestMapping("/movie/{id}")
    Mono<Movie> findId(@PathVariable String id) {
        return movieRepository.findById(id);
    }


    @GetMapping(value = "/movie/{id}/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<MovieEvent> stream(@PathVariable String id) {
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));

        Flux<MovieEvent> events =  Flux.fromStream(Stream.generate(() -> new MovieEvent(movieRepository.findById(id).block(), new Date(), "jack")));

        return Flux.zip(interval, events).map(Tuple2::getT2);
    }

}
