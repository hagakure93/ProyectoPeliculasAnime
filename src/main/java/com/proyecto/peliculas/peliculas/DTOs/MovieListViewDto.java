package com.proyecto.peliculas.peliculas.DTOs;

import com.proyecto.peliculas.peliculas.models.Movie;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieListViewDto {
    // Incluye solo los campos que quieres mostrar en la lista de peliculas
    private Long id;
    private String title;
    private String description;
    private int year;
    private Double rating;
    private String imageUrl;

    // Constructor vacio (necesario para Jackson y Spring)
    public MovieListViewDto() {
    }

    // Constructor o metodo estatico para convertir una entidad Movie a este DTO
    public static MovieListViewDto fromEntity(Movie movie) {
        MovieListViewDto dto = new MovieListViewDto();
        dto.setId(movie.getId());
        dto.setTitle(movie.getTitle());
        dto.setDescription(movie.getDescription());
        dto.setYear(movie.getYear());
        dto.setRating(movie.getRating());
        dto.setImageUrl(movie.getImageUrl());

        return dto;
    }

}
