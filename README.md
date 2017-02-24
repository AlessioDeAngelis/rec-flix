# whitehall-flix
Simple netflix-style recommender system leveraging on Neo4j and Spring made for educational purposes.

Open application.properties and set the neo4j properties according
to your running Neo4j Server.

For example  
neo4j.uri=bolt://localhost:7687  
neo4j.username=neo4j  
neo4j.password=neo4j  

Open the file recommendation.json. You will find there an 
array of couples: the name/description of the recommdation technique and the corresponding Cypher 
query to run it against the graph db.
An example is   
  {
    "name": "BEST ITALIAN COMEDIES",
    "query": "MATCH (c:Country)<-[:fromCountry]-(n:Movie)-[:hasGenre]->(g:Genre) where g.name = 'Comedy' and c.name = 'Italy' RETURN n.imdbID as imdbID, n.title as title, n.poster as poster LIMIT 25"
  }