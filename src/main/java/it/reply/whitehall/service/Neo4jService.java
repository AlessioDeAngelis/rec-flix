package it.reply.whitehall.service;

import it.reply.whitehall.model.Movie;
import org.neo4j.driver.v1.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author a.deangelis
 */
@Service
public class Neo4jService {

    @Autowired
    private Environment environment;

    public List<Movie> getMoviesByQuery(String cypherQuery) {
        String neo4jUri = this.environment.getProperty("neo4j.uri");
        String neo4jPassword = this.environment.getProperty("neo4j.password");
        String neo4jUsername = this.environment.getProperty("neo4j.username");
        List<Movie> movies = new ArrayList<>();

        Driver driver = GraphDatabase.driver(neo4jUri, AuthTokens.basic(neo4jUsername, neo4jPassword));

        try (Session session = driver.session()) {
            StatementResult rs = session.run(cypherQuery);
            while (rs.hasNext()) {
                Record record = rs.next();
                String moviePoster = record.get("poster").asString();
                String imdbID = record.get("imdbID").asString();
                String title = record.get("title").asString();
                Movie movie = new Movie();
                movie.setImdbID(imdbID);
                movie.setTitle(title);
                movie.setPoster(moviePoster);
                movies.add(movie);
            }
        }

        driver.close();
        return movies;
    }
}
