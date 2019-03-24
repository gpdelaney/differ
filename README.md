Differ APP
============
App that compares 2 encoded Json and returns if they are equal or not.

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
rightJson:
/v1/differ/{id}/right
compare:
/v1/differ/{id}
