Differ APP
============
App that compares 2 encoded Json and returns if they are equal or not. First you must persist two encoded json with the same ID. The you have to call the compare endpoint with that ID to get the result of that comparison. You can have several encoded jsons with different id, if you submit a encoded Json with the same id, it will update the previous one.

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
body: encodedJson
rightJson:
/v1/differ/{id}/right
body: encodedJson
compare:
/v1/differ/{id}
