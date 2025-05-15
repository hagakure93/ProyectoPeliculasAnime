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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.peliculas.peliculas.models.Comment;
import com.proyecto.peliculas.peliculas.models.Movie;
import com.proyecto.peliculas.peliculas.repositories.CommentRepository;
import com.proyecto.peliculas.peliculas.repositories.MovieRepository;

@RestController
@RequestMapping("/api/movies/{movieId}/comments")
@CrossOrigin
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MovieRepository movieRepository;

    // --- Metodos del Controlador (Endpoints) ---

    /**
     * GET /api/movies/{movieId}/comments
     * Obtiene todos los comentarios para una pelicula especifica.
     * Devuelve 404 Not Found si la pelicula no existe.
     * Devuelve 200 OK si la pelicula existe.
     */
    @GetMapping
    public ResponseEntity<List<Comment>> getCommentsByMovie(@PathVariable Long movieId) {

        Optional<Movie> optionalMovie = movieRepository.findById(movieId);

        if (optionalMovie.isEmpty()) {
            return ResponseEntity.notFound().build(); // Devuelve 404 Not Found

        }

        List<Comment> comments = commentRepository.findByMovieId(movieId);

        return ResponseEntity.ok(comments);
    }

    /**
     * POST /api/movies/{movieId}/comments
     * Añade un nuevo comentario a una pelicula especifica.
     * Devuelve 404 Not Found si la pelicula no existe.
     * Devuelve 201 Created si el comentario se crea exitosamente
     */
    @PostMapping
    // @RequestBody le dice a Spring que lea el cuerpo de la peticion (el JSON) y lo
    // convierta a un objeto Comment
    public ResponseEntity<Comment> createCommentForMovie(@PathVariable Long movieId, @RequestBody Comment comment) {

        Optional<Movie> optionalMovie = movieRepository.findById(movieId);

        if (optionalMovie.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Movie movie = optionalMovie.get();

        // 3. Asocia el comentario a la pelicula encontrada

        comment.setMovie(movie);

        Comment savedComment = commentRepository.save(comment);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedComment); // Devuelve 201 Created
    }

    /**
     * DELETE /api/movies/{movieId}/comments/{commentId}
     * Elimina un comentario especifico asociado a una pelicula.
     * Devuelve 404 Not Found si la pelicula no existe O si el comentario no existe
     * O si el comentario existe pero no pertenece a esa pelicula.
     * Devuelve 204 No Content si el comentario se elimina exitosamente.
     */
    @DeleteMapping("/{commentId}") // Mapea a DELETE /api/movies/{movieId}/comments/ID_COMENTARIO
    public ResponseEntity<Void> deleteComment(@PathVariable Long movieId, @PathVariable Long commentId) {

        // 2. Busca el comentario por su ID
        Optional<Comment> optionalComment = commentRepository.findById(commentId);

        if (optionalComment.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Comment comment = optionalComment.get();

        // Comprueba si la pelicula asociada al comentario NO TIENE el ID de la pelicula
        // de la URL.
        if (!comment.getMovie().getId().equals(movieId)) {

            return ResponseEntity.notFound().build(); // Devuelve 404 Not Found
        }

        // 5. Si el comentario existe Y pertenece a la pelicula correcta, procede a
        // eliminarlo.
        commentRepository.deleteById(commentId);

        return ResponseEntity.noContent().build();
    }

}
