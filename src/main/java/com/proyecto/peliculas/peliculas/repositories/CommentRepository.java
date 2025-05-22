// Ejemplo de CommentRepository
package com.proyecto.peliculas.peliculas.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.peliculas.peliculas.models.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByMovieId(Long movieId);
}
