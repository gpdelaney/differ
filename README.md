Differ APP
============
App that compares 2 encoded Json and returns if they are equal or not. First you must persist two encoded json with the same ID. The you have to call the compare endpoint with that ID to get the result of that comparison. You can have several encoded jsons with different id, if you submit a encoded Json with the same id, it will update the previous one.

Components
==========
Spring Boot \n
Gradle \n
JPA \n
H2DB (embedded)\n

Instructions to run
===================

Download Repo\n
./gradlew bootRun

Endpoints
===================
leftJson:\n
/v1/differ/{id}/left\n
body: encodedJson\n
rightJson:\n
/v1/differ/{id}/right\n
body: encodedJson\n
compare:\n
/v1/differ/{id}\n
