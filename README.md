# WHITEHALL-FLIX
Simple Netflix-style Recommender System leveraging on Neo4j and Spring and made for educational purposes.
Open <b>application.properties</b> and set the neo4j properties according
to the Neo4j Server running your dataset.

For example  
```
neo4j.uri=bolt://localhost:7687  
neo4j.username=neo4j  
neo4j.password=neo4j  
```

The sample movies are taken from the **The Open Movie Database**,  http://www.omdbapi.com/.
You can find the dataset in the file **movies.json** within the
resource directory, or quering the application endpoint http://localhost:8080/findMovies.
The app should be loaded
 
Open the file <b>recommendation.json</b>. You will find there an 
array of couples: the name/description of the recommendation technique and the corresponding Cypher 
query to run it against the graph db.
An example is 
```json
{
  "name": "BEST ITALIAN COMEDIES",
  "query": "MATCH (c:Country)<-[:fromCountry]-(n:Movie)-[:hasGenre]->(g:Genre) where g.name = 'Comedy' and c.name = 'Italy' RETURN n.imdbID as imdbID, n.title as title, n.poster as poster LIMIT 25"
}
```

Note that the application expects the Cypher query to return the following
three variables: **imdbID**, **title** and **poster** where poster is the image url.