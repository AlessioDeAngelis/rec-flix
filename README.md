# WHITEHALL-FLIX
Simple Netflix-style Recommender System leveraging on Neo4j 3.1 
and Spring, made for fun and educational purposes.  

The sample movies are taken from the **The Open Movie Database**,  http://www.omdbapi.com/.
You can find the dataset in the file **movies.json** within the
resource directory, or querying the REST application endpoint http://localhost:8080/findMovies.

There is also a csv file with the ratings of the users that watched those movies, ratings.csv
 You can obtain it as well querying the REST application endpoint http://localhost:8080/userRatings

 
Open the file <b>recommendation.json</b>. You will find an 
array of couples: the name/description of the recommendation technique and the corresponding Cypher 
query to run it against the graph db.
An example is 
```json
{
  "name": "BEST ITALIAN COMEDIES",
  "query": "MATCH (c:Country)<-[:PRODUCTED_IN]-(n:Movie)-[:HAS_GENRE]->(g:Genre) where g.name = 'Comedy' and c.name = 'Italy' RETURN n.poster as poster LIMIT 25"
}
```

Note that the application expects the Cypher query to return the variable named **poster**.
It is the image url and the application will use it to render the movie on the screen.

## The challenge ##
1. Download Whitehall-flix application
2. Modify the application.properties and set the neo4j properties according
to the Neo4j Server running your dataset. You should use the bolt protocol

For example  
```
neo4j.uri=bolt://localhost:7687  
neo4j.username=neo4j  
neo4j.password=neo4j  
```
3.Modify the recommendation.json in order to provide recommendations.
The challenge requires you to provide the following recommendations:  
* MOST POPULAR movies in the dataset  
*  SIMILAR TO WHAT YOU WATCHED: the movies that should be recommended to the user
according to her interests and preferences   
* YOUR FRIENDS LOVED THOSE: the movies that should be recommended according to the
 friends interests  
 
 You can add more entries! Let your creativity flow! :D

4. Run the application with your favourite IDE or build it with   
mvn clean install -Dmaven.test.skip=true and then run it with
java -jar target/whitehall-flix-0.0.1-SNAPSHOT.jar

5. Take a look to http://localhost:8080 and have fun :D