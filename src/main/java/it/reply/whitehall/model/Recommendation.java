package it.reply.whitehall.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author a.deangelis
 */
public class Recommendation {
    private String name;
    private String query;
    private List<Movie> movies;

    public Recommendation() {
        this.name = "";
        this.query = "";
        this.movies = new ArrayList<>();
    }

    public Recommendation(String name, String query) {
        this.name = name;
        this.query = query;
        this.movies = new ArrayList<>();
    }

    public Recommendation(String name, String query, List<Movie> movies) {
        this.name = name;
        this.query = query;
        this.movies = movies;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
