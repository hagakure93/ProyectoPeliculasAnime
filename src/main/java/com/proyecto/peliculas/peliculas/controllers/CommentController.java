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

    // Inyecta los repositorios necesarios
    @Autowired
    private CommentRepository commentRepository;

    @Autowired // Necesitas el repositorio de peliculas para asociar los comentarios
    private MovieRepository movieRepository;

     // --- Metodos del Controlador (Endpoints) ---

    /**
     * GET /api/movies/{movieId}/comments
     * Obtiene todos los comentarios para una pelicula especifica.
     * Devuelve 404 Not Found si la pelicula no existe.
     * Devuelve 200 OK con una lista (posiblemente vacia) de comentarios si la pelicula existe.
     */
    @GetMapping
    public ResponseEntity<List<Comment>> getCommentsByMovie(@PathVariable Long movieId) {
        // 1. Verifica si la pelicula con el ID dado existe en la base de datos
        Optional<Movie> optionalMovie = movieRepository.findById(movieId);

        // 2. Si la pelicula no se encuentra, devuelve una respuesta 404 Not Found
        if (optionalMovie.isEmpty()) {
            return ResponseEntity.notFound().build(); // Devuelve 404 Not Found
        }

        // 3. Si la pelicula existe, busca todos los comentarios asociados a ese ID de pelicula
        List<Comment> comments = commentRepository.findByMovieId(movieId);

        // 4. Devuelve una respuesta 200 OK con la lista de comentarios encontrados
        // La lista puede estar vacia si la pelicula no tiene comentarios, lo cual es una respuesta valida 200 OK.
        return ResponseEntity.ok(comments); // Devuelve 200 OK con el cuerpo de la respuesta (la lista de comentarios)
    }

    /**
     * POST /api/movies/{movieId}/comments
     * Añade un nuevo comentario a una pelicula especifica.
     * Devuelve 404 Not Found si la pelicula no existe.
     * Devuelve 201 Created si el comentario se crea exitosamente.
     */
    @PostMapping
    // @RequestBody le dice a Spring que lea el cuerpo de la peticion (el JSON) y lo convierta a un objeto Comment
    public ResponseEntity<Comment> createCommentForMovie(@PathVariable Long movieId, @RequestBody Comment comment) {
        // 1. Busca la pelicula a la que se añadira el comentario
        Optional<Movie> optionalMovie = movieRepository.findById(movieId);

        // 2. Si la pelicula no se encuentra, devuelve una respuesta 404 Not Found
        if (optionalMovie.isEmpty()) {
            return ResponseEntity.notFound().build(); // Devuelve 404 Not Found
        }

        Movie movie = optionalMovie.get(); // Obtiene el objeto Movie si esta presente

        // 3. Asocia el comentario a la pelicula encontrada
        // Esto establece la relacion ManyToOne en el lado del comentario
        comment.setMovie(movie);

        // Opcional pero recomendado: Establecer la fecha/hora del comentario en el servidor
        // para asegurar que es la hora de creacion y no depende del cliente.
        // Asegurate de importar java.time.LocalDateTime y manejarlo en tu entidad Comment.
        // comment.setTimestamp(LocalDateTime.now());

        // 4. Guarda el comentario en la base de datos utilizando el repositorio de comentarios
        Comment savedComment = commentRepository.save(comment);

        // 5. Devuelve una respuesta 201 Created con el comentario guardado en el cuerpo.
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
        // 1. No necesitamos buscar la pelicula directamente aqui para la eliminacion,
        // pero si necesitamos buscar el comentario y verificar su relacion.

        // 2. Busca el comentario por su ID
        Optional<Comment> optionalComment = commentRepository.findById(commentId);

        // 3. Si el comentario no se encuentra, devuelve 404 Not Found.
        if (optionalComment.isEmpty()) {
             return ResponseEntity.notFound().build(); // Devuelve 404 Not Found
        }

        Comment comment = optionalComment.get(); // Obtiene el objeto Comment si esta presente

        // 4. Verifica que el comentario encontrado realmente pertenece a la pelicula especificada en la URL.
        // Esto es una capa extra de seguridad y validacion de la URL.
        // Comprueba si la pelicula asociada al comentario NO TIENE el ID de la pelicula de la URL.
        if (!comment.getMovie().getId().equals(movieId)) {
             // Si no pertenece a esa pelicula, lo tratamos como si el recurso (comentario bajo esa peli) no existiera.
             // Podrias considerar un 403 Forbidden si el comentario existe pero el usuario no tiene permiso o no pertenece a esa peli.
             return ResponseEntity.notFound().build(); // Devuelve 404 Not Found
        }

        // 5. Si el comentario existe Y pertenece a la pelicula correcta, procede a eliminarlo.
        commentRepository.deleteById(commentId);

        // 6. Devuelve una respuesta 204 No Content para indicar que la eliminacion fue exitosa y no hay contenido que devolver.
        return ResponseEntity.noContent().build(); // Devuelve 204 No Content
    }

    // Opcional: Podrias añadir aqui los metodos GET /{commentId} para obtener un comentario individual
    // y PUT /{commentId} para actualizar un comentario especifico.
    // Estos tambien deberian verificar que el comentario existe y pertenece a la pelicula correcta.
}
