package com.proyecto.peliculas.peliculas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.peliculas.peliculas.models.Movie;

// Repository created with this
public interface MovieRepository extends JpaRepository<Movie, Long> {

}
