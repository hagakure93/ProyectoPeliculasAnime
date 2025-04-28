package com.proyecto.peliculas.peliculas.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ResponseEntity<Movie> getMovieByIId(@PathVariable Long id) {
        Optional<Movie> movie = movieRepository.findById(id);
        return movie.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }



}
