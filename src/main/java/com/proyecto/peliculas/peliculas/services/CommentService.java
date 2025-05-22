package com.proyecto.peliculas.peliculas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.peliculas.peliculas.models.Comment;
import com.proyecto.peliculas.peliculas.models.Movie;
import com.proyecto.peliculas.peliculas.repositories.CommentRepository;
import com.proyecto.peliculas.peliculas.repositories.MovieRepository;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MovieRepository movieRepository;

    public Optional<Movie> findMovieById(Long movieId) {
        return movieRepository.findById(movieId);
    }

    public List<Comment> getCommentsByMovie(Long movieId) {
        return commentRepository.findByMovieId(movieId);
    }

    public Optional<Comment> createCommentForMovie(Long movieId, Comment comment) {
        Optional<Movie> optionalMovie = movieRepository.findById(movieId);
        if (optionalMovie.isEmpty()) {
            return Optional.empty();
        }
        Movie movie = optionalMovie.get();
        comment.setMovie(movie);
        Comment savedComment = commentRepository.save(comment);
        return Optional.of(savedComment);
    }

    public boolean deleteComment(Long movieId, Long commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isEmpty()) {
            return false;
        }
        Comment comment = optionalComment.get();
        if (!comment.getMovie().getId().equals(movieId)) {
            return false;
        }
        commentRepository.deleteById(commentId);
        return true;
    }
}
