//CREATE COUNTRY NODES
CALL apoc.load.json('file:///C:/Users/a.deangelis/Desktop/movies.json') YIELD value
WITH split(value.Country, ',') AS countries
UNWIND countries AS country
WITH trim(country) AS countryName
MERGE (n:Country {name: countryName})
;

//CREATE GENRE NODES
CALL apoc.load.json('file:///C:/Users/a.deangelis/Desktop/movies.json') YIELD value
WITH split(value.Genre, ',') AS genres
UNWIND genres AS genre
WITH trim(genre) AS genre_name
MERGE (n:Genre {name: genre_name})
;

//CREATE ACTOR NODES
CALL apoc.load.json('file:///C:/Users/a.deangelis/Desktop/movies.json') YIELD value
WITH split(value.Actors, ',') AS actors
UNWIND actors AS actor
WITH trim(actor) AS actor_name
MERGE (n:Actor {name: actor_name})
;

//CREATE DIRECTOR NODES
CALL apoc.load.json('file:///C:/Users/a.deangelis/Desktop/movies.json') YIELD value
WITH split(value.Director, ',') AS directors
UNWIND directors AS director
WITH trim(director) AS directorName
MERGE (:Director {name : directorName})
;

//CREATE DIRECTED RELATIONSHIP
CALL apoc.load.json('file:///C:/Users/a.deangelis/Desktop/movies.json') YIELD value
WITH value.imdbID AS imdbID , split(value.Director, ',') AS directors
UNWIND directors AS director
WITH imdbID, trim(director) AS directorName
MATCH (movie:Movie)
MATCH (director:Director)
WHERE movie.imdbID = imdbID
AND director.name = directorName
MERGE (director)-[:DIRECTED]->(movie)
;

//CREATE ACTED_IN RELATIONSHIP
CALL apoc.load.json('file:///C:/Users/a.deangelis/Desktop/movies.json') YIELD value
WITH value.imdbID AS imdbID, split(value.Actors, ',') AS actors
UNWIND actors AS actor
WITH imdbID, trim(actor) AS actorName
MATCH (movie:Movie)
MATCH (actor:Actor)
WHERE movie.imdbID = imdbID
AND actor.name = actorName
MERGE (actor)-[:ACTED_IN]->(movie);

//CREATE HAS_GENRE RELATIONSHIP
CALL apoc.load.json('file:///C:/Users/a.deangelis/Desktop/movies.json') YIELD value
WITH value.imdbID AS imdbID, split(value.Genre, ',') AS genres
UNWIND genres AS genre
WITH imdbID, trim(genre) AS genreName
MATCH (movie:Movie)
MATCH (genre:Genre)
WHERE movie.imdbID = imdbID
AND genre.name = genreName
MERGE (movie)-[:HAS_GENRE]->(genre)
;

//CREATE PRODUCTED_IN RELATIONSHIP
CALL apoc.load.json('file:///C:/Users/a.deangelis/Desktop/movies.json') YIELD value
WITH value.imdbID AS imdbID, split(value.Country, ',') AS countries
UNWIND countries AS country
WITH imdbID, trim(country) AS countryName
MATCH (movie:Movie)
MATCH (country:Country)
WHERE movie.imdbID = imdbID
AND country.name = countryName
MERGE (movie)-[:PRODUCTED_IN]->(country)
;

//CREATE WATCHED RELATIONSHIP
LOAD CSV WITH HEADERS FROM
'http://localhost:8080/userRatings' AS line
WITH toInt(line.person) AS personId, toInt(line.rating) AS rating, line.movie AS imdbID
MATCH (person:Person {id : personId})
MATCH (movie:Movie {imdbID: imdbID})
MERGE (person)-[:WATCHED {rating:rating}]->(movie)

//MOST POPULAR
MATCH (movie:Movie)-[:WATCHED]-(person:Person)
WITH movie, count(person) AS watchers
  ORDER BY watchers DESC
LIMIT 10
RETURN movie.imdbID AS imdbID, movie.title AS title, movie.poster AS poster

MATCH (movie:Movie)-[:WATCHED]-(person:Person) WITH movie, count(person) AS watchers ORDER BY watchers DESC LIMIT 10 RETURN movie.imdbID AS imdbID, movie.title AS title, movie.poster AS poster