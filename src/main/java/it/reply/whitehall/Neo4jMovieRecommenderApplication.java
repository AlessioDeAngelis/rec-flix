package it.reply.whitehall;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class Neo4jMovieRecommenderApplication extends SpringBootServletInitializer {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public static void main(String[] args) {
        SpringApplication.run(Neo4jMovieRecommenderApplication.class, args);
    }
}