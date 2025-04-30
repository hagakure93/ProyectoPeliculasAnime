// Ejemplo de CommentRepository
package com.proyecto.peliculas.peliculas.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository; 

import com.proyecto.peliculas.peliculas.models.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // Método personalizado para encontrar comentarios por el ID de la película
    List<Comment> findByMovieId(Long movieId);
}
