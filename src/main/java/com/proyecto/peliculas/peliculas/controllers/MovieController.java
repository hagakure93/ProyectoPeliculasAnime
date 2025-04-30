package com.proyecto.peliculas.peliculas.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.peliculas.peliculas.models.Movie;
import com.proyecto.peliculas.peliculas.repositories.MovieRepository;

@RestController
@RequestMapping("api/movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    // El getMapping no lleva ninguna URL así que el get es api/movies
    @CrossOrigin
    @GetMapping
    public List<Movie> getAllMovies() {

        /*
         * Gracias al ORM de Hibernate tengo todos los métodos para trabajar con la BD
         * sin utilizar consultas SQL
         */
        return movieRepository.findAll();

    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        Optional<Movie> movie = movieRepository.findById(id);
        return movie.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        Movie savedMovie = movieRepository.save(movie);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMovie);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        if (!movieRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        } else
            movieRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @CrossOrigin
    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody Movie updatedMovie) {
        if (!movieRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        } else
            updatedMovie.setId(id);
        Movie savedMovie = movieRepository.save(updatedMovie);
        return ResponseEntity.ok(savedMovie);
    }

    @CrossOrigin
    @PutMapping("/vote/{id}/{rating}")
    public ResponseEntity<Movie> voteMovie(@PathVariable Long id, @PathVariable double rating) {
        if (!movieRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        Optional<Movie> optional = movieRepository.findById(id);
        Movie movie = optional.get();
        double newRating = ((movie.getVotes() * movie.getRating()) / (movie.getVotes() + 1));

        movie.setVotes(movie.getVotes() + 1);
        movie.setRating(newRating);

        Movie savedMovie = movieRepository.save(movie);
        return ResponseEntity.ok(savedMovie);

    }

    @CrossOrigin 
    @GetMapping("/search") 
    // @RequestParam extrae el valor del parametro de la URL (ej: ?title=valor)
    public ResponseEntity<List<Movie>> searchMoviesByTitle(@RequestParam String title) {

        // Llama al nuevo metodo del repositorio para buscar por titulo
        List<Movie> movies = movieRepository.findByTitle(title);
        // Si usaste la opcion 'ContainingIgnoreCase':
        // List<Movie> movies = movieRepository.findByTitleContainingIgnoreCase(title);

        // Devuelve la lista de peliculas encontradas.
        // Si no se encuentra ninguna, la lista estara vacia, lo cual es un 200 OK
        // valido con cuerpo vacio [].
        return ResponseEntity.ok(movies); // Devuelve 200 OK con la lista de peliculas (puede estar vacia)
    }

}
