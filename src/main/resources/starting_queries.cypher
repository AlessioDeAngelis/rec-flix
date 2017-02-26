//DELETE ALL NODES AND RELATIONSHIPS
MATCH (n) DETACH DELETE (n);

//CREATE A NODE
CREATE (n:Person { id:1, name: 'Alessio' }) RETURN n

//CREATE AN INDEX
CREATE INDEX ON :Person(id)

//CREATE MORE NODES
FOREACH (personId IN [1,2,3,4,5,6,7] |
 CREATE (:Person {id:personId, name: 'Person'+personId}))

//CREATE RELATIONSHIP
MATCH (person:Person)
MATCH (friend:Person)
WHERE person.id = 1
and friend.id = 2
CREATE (person)-[:FRIEND_OF]->(friend);

MATCH (person1:Person)
MATCH (person2:Person)
MATCH (person3:Person)
MATCH (person4:Person)
MATCH (person5:Person)
MATCH (person6:Person)
MATCH (person7:Person)
WHERE person1.id = 1
and person2.id = 2
and person3.id = 3
and person4.id = 4
and person5.id = 5
and person6.id = 6
and person7.id = 7
CREATE (person1)-[:FRIEND_OF]->(person3)
CREATE (person1)-[:FRIEND_OF]->(person4)
CREATE (person1)-[:FRIEND_OF]->(person5)
CREATE (person2)-[:FRIEND_OF]->(person6)
CREATE (person6)-[:FRIEND_OF]->(person7)
CREATE (person7)-[:FRIEND_OF]->(person4);

//DO PERSON1 AND PERSON7 HAVE ANY MUTUAL FRIEND?
MATCH (person1:Person)-[r:FRIEND_OF]-(friend:Person)-[r2:FRIEND_OF]-(person7:Person)
              WHERE person1.id = 1 and person7.id = 7
              RETURN friend;

//SOME AGGREGATION
MATCH (person:Person)-[r:FRIEND_OF]-(friend:Person)
RETURN person.id, count(friend) as friendsCount
ORDER BY friendsCount DESC

//LOAD JSON
CALL apoc.load.json('file:///C:/Users/a.deangelis/workspaceIntellij/whitehall-flix/src/main/resources/movies.json') YIELD value
AS movie
RETURN movie
LIMIT 5;

//LOAD JSON FROM A REST API
CALL apoc.load.json('http://www.omdbapi.com/?i=tt0109830&plot=short&r=json') YIELD value
AS movie
RETURN movie

//CREATE A MOVIE FROM JSON
CALL apoc.load.json('file:///C:/Users/a.deangelis/Desktop/movies.json') YIELD value
AS movie
MERGE (m:Movie {imdbID: movie.imdbID})
SET
m.title = movie.Title,
m.rated = movie.Rated,
m.imdbVotes = movie.imdbVotes,
m.imdbRating = movie.imdbRating,
m.poster = movie.Poster
;

//CREATE COUNTRY NODES
CALL apoc.load.json('file:///C:/Users/a.deangelis/Desktop/movies.json') YIELD value
WITH split(trim(value.Country), ',') AS countries
UNWIND countries AS country
return distinct name
MERGE (n:Country {name: country})
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
WITH split(value.Director, ',') as directors
UNWIND directors AS director
WITH trim(director) as directorName
MERGE (:Director {name : directorName})
;

//CREATE DIRECTED RELATIONSHIP
CALL apoc.load.json('file:///C:/Users/a.deangelis/Desktop/movies.json') YIELD value
WITH value.imdbID as imdbID , split(value.Director, ',') as directors
UNWIND directors AS director
WITH trim(director) as directorName
MERGE (:Director {name : directorName})
;