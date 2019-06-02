# Tasty-Search
Simple search api implementation with the help of solr

Note: Create a solr core(Tasty_Search) to store and retrieve data.
      Add fields to the managed-schema as follows:
      <field name="id" type="string" multiValued="false" indexed="true" required="true" stored="true"/>
      <field name="name" type="text_general"/>
      <field name="price" type="pdoubles"/>
      <field name="productId" type="string" multiValued="false" indexed="true" required="true" stored="true"/>
      <field name="profileName" type="string" stored="true"/>
      <field name="score" type="pfloat" stored="true"/>
      <field name="summary" type="text_general" indexed="true" stored="true"/>
      <field name="text" type="text_general" indexed="true" stored="true"/>
      <field name="time" type="string" stored="true"/>
      <field name="userId" type="string" stored="true"/>


# Indexing 
Food data indexed to the solr server with the controller IndexDataController by calling the api(http://localhost:8080/tasty_search/indexData).

foods.txt contains the data in the following format:
  product/productId: B001E4KFG0
  review/userId: A3SGXH7AUHU8GW
  review/profileName: delmartian
  review/helpfulness: 1/1
  review/score: 5.0
  review/time: 1303862400
  review/summary: Good Quality Dog Food
  review/text: I have bought several of the Vitality canned dog food products and have found them all to be of good quality.  The product looks more like a stew than a processed meat and it smells better. My Labrador is finicky and she appreciates this product better than  most.
  
  
# Search
  Search text with the help of searchText api
  (http://localhost:8080/tasty_search/searchText?search_text=Great! Just good)
  
  
  The aforementioned api returns top 20 documents.
  Top documents are decided by the score, where the score is defined by the following:
    Score(D, Q) = Q ⋂ D
    (i.e. # tokens matching between Query(Q) & Document(D) normalized by query
    length the number of tokens in the given query).
    

# Instruction to run the api
  1. Navigate to Tasty_Search where pom.xml exists.
  2. Compile as mvn clean install (tasty_search.war will be created in target folder)
  3. Deploy the war in the tomcat/webapps folder.
  4. Start tomcat
  5. Run http://localhost:8080/tasty_search/indexData to index data
  6. Run http://localhost:8080/tasty_search/searchText?search_text=good to search text
   
