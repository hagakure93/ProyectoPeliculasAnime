package com.proyecto.peliculas.peliculas.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.peliculas.peliculas.DTOs.MovieListViewDto;
import com.proyecto.peliculas.peliculas.models.Movie;
import com.proyecto.peliculas.peliculas.repositories.MovieRepository;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public List<MovieListViewDto> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        List<MovieListViewDto> dtoList = new ArrayList<>();
        for (Movie movie : movies) {
            dtoList.add(MovieListViewDto.fromEntity(movie));
        }
        return dtoList;
    }

    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }

    public Movie createMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public boolean deleteMovie(Long id) {
        if (!movieRepository.existsById(id)) {
            return false;
        }
        movieRepository.deleteById(id);
        return true;
    }

    public Optional<Movie> updateMovie(Long id, Movie updatedMovie) {
        if (!movieRepository.existsById(id)) {
            return Optional.empty();
        }
        updatedMovie.setId(id);
        return Optional.of(movieRepository.save(updatedMovie));
    }

    public Optional<Movie> voteMovie(Long id, double rating) {
        Optional<Movie> optional = movieRepository.findById(id);
        if (optional.isEmpty()) {
            return Optional.empty();
        }
        Movie movie = optional.get();
        double totalRatingsSum = movie.getRating() * movie.getVotes();
        int totalVotesCount = movie.getVotes();
        double newTotalRatingsSum = totalRatingsSum + rating;
        int newTotalVotesCount = totalVotesCount + 1;
        double newAverageRating = newTotalVotesCount > 0 ? (newTotalRatingsSum / newTotalVotesCount) : 0.0;
        movie.setVotes(newTotalVotesCount);
        movie.setRating(newAverageRating);
        return Optional.of(movieRepository.save(movie));
    }

    public List<Movie> searchMoviesByTitle(String title) {
        return movieRepository.findByTitle(title);
    }

    public List<Movie> addMovies(List<Movie> movies) {
        return movieRepository.saveAll(movies);
    }
}
