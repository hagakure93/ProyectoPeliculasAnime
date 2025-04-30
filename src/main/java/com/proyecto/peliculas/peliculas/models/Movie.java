package com.proyecto.peliculas.peliculas.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private int year;
    private int votes;
    private Double rating;

    @Column(name = "image_url")
    private String imageUrl;

    
    // Una pelicula tiene muchos comentarios
    @OneToMany(mappedBy = "movie", 
            cascade = CascadeType.ALL, //  Si borras la pelicula, se borran los comentarios
            orphanRemoval = true, //Si quitas un comentario de la lista, se borra
            fetch = FetchType.LAZY) // Carga perezosa
    @JsonManagedReference // Anotacion de Jackson: Este es el lado "manager" de la relacion para JSON
    private List<Comment> comments = new ArrayList<>(); // Inicializar la lista (buena practica)


    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setMovie(this); // Asegurar la relacion inversa
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setMovie(null); // Romper la relacion inversa
    }

}
