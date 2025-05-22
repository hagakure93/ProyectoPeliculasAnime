package com.proyecto.peliculas.peliculas.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.peliculas.peliculas.models.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findByTitle(String title);

    // buscar titulos que contengan el texto (no solo coincidan exactamente)
    List<Movie> findByTitleContainingIgnoreCase(String title);

}
