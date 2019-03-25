Differ APP
============
App that compares 2 encoded Json and returns if they are equal or not. First you must persist two encoded json with the same ID. 
Then you have to call the compare endpoint with that ID to get the result of that comparison. 
You can have several encoded jsons with different id, if you submit a encoded Json with the same id, it will update the previous one.
If you have an unencoded json (normal json) you can send it to the /v1/differ/json/{id}/left or /right and it will encode and store
in the database, you can compare them by calling the v1/diff/1 endpoint.

If one of the Json to compare is null, the application will return a 404 when trying to compare.

Components
==========
Spring Boot

Gradle

JPA

H2DB (embedded)

Instructions to run
===================

Download Repo
./gradlew bootRun

Endpoints
===================
leftJson:
/v1/differ/{id}/left
body: {encodedJson}
--------------------
rightJson:
/v1/differ/{id}/right
body: {encodedJson}
--------------------
compare:
/v1/differ/{id}
------------------
leftUnencodedJson:
/v1/differ/json/{id}/left
body: {"example":"json"}
--------------------
rightJson:
/v1/differ/json/{id}/right
body: {"example":"json"}

