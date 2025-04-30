package com.proyecto.peliculas.peliculas.models;

import java.time.LocalDateTime; // Para evitar bucles infinitos en JSON

import com.fasterxml.jackson.annotation.JsonBackReference; // O org.springframework.data.annotation si usas Spring Data sin JPA

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity 
@Table(name = "Comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;
    private String text;
    private String author; 
    private LocalDateTime timestamp;

    // Relacion Muchos-a-Uno con Movie
    @ManyToOne(fetch = FetchType.LAZY) // Muchos comentarios a una pelicula
    @JoinColumn(name = "movie_id") // La columna en la tabla 'comment' que guarda el ID de la pelicula
    @JsonBackReference // Importante para serializacion JSON, evita que la Movie referencie de vuelta a todos los Comments
    private Movie movie;

    // Getters y Setters, constructores...
    public Comment() {
        this.timestamp = LocalDateTime.now(); 
    }

    
}
