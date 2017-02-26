package it.reply.whitehall.controller;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.reply.whitehall.model.Movie;
import it.reply.whitehall.model.OMDBMovieList;
import it.reply.whitehall.model.Recommendation;
import it.reply.whitehall.model.Recommendations;
import it.reply.whitehall.service.Neo4jService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @author a.deangelis
 */
@RestController
public class HomeController {

    @Autowired
    private Neo4jService neo4jService;
    @Autowired
    private ResourceLoader resourceLoader;

    @RequestMapping("/findMovies")
    @ResponseBody
    public OMDBMovieList retrieveOMDBMovie() throws IOException {
        InputStream inputStream = new ClassPathResource("movies.json").getInputStream();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        OMDBMovieList omdbMovieList = mapper.readValue(inputStream, OMDBMovieList.class);
        return omdbMovieList;
    }

    @RequestMapping("/userRatings")
    @ResponseBody
    public String retrieveUserRatings() throws IOException {
        InputStream inputStream = new ClassPathResource("ratings.csv").getInputStream();
        String ratings = StreamUtils.copyToString(inputStream, Charset.defaultCharset());
        return ratings;
    }

    @RequestMapping("/")
    public ModelAndView home() throws IOException {
        return new ModelAndView("whitehall-flix");
    }

    @RequestMapping("/recommend")
    @ResponseBody
    @CrossOrigin
    public Recommendations recommend() throws IOException {
        InputStream inputStream = new ClassPathResource("recommendation.json").getInputStream();
        ObjectMapper mapper = new ObjectMapper();
        Recommendations recommendations = mapper.readValue(inputStream, Recommendations.class);
        for (Recommendation recommendation : recommendations) {
            if (StringUtils.isNotEmpty(recommendation.getQuery())) {
                List<Movie> recommendedMovie = this.neo4jService.getMoviesByQuery(recommendation.getQuery());
                recommendation.setMovies(recommendedMovie);
            }
        }
        return recommendations;
    }
}
