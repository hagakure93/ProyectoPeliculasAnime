package com.proyecto.peliculas.peliculas.DTOs;

import com.proyecto.peliculas.peliculas.models.Movie;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieListViewDto {
    
    private Long id;
    private String title;
    private String description;
    private int year;
    private Double rating;
    private String imageUrl;

     
    public MovieListViewDto() {
    }

    // Constructor para convertir una entidad Movie a este DTO
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
