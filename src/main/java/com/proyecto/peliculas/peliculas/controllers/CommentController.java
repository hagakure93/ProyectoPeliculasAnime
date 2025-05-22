package com.proyecto.peliculas.peliculas.controllers;

import java.util.List;

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
import com.proyecto.peliculas.peliculas.services.CommentService;

@RestController
@RequestMapping("/api/movies/{movieId}/comments")
@CrossOrigin
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping
    public ResponseEntity<List<Comment>> getCommentsByMovie(@PathVariable Long movieId) {
        if (commentService.findMovieById(movieId).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<Comment> comments = commentService.getCommentsByMovie(movieId);
        return ResponseEntity.ok(comments);
    }

    @PostMapping
    public ResponseEntity<Comment> createCommentForMovie(@PathVariable Long movieId, @RequestBody Comment comment) {
        return commentService.createCommentForMovie(movieId, comment)
                .map(savedComment -> ResponseEntity.status(HttpStatus.CREATED).body(savedComment))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long movieId, @PathVariable Long commentId) {
        boolean deleted = commentService.deleteComment(movieId, commentId);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

}
