# hearxgroup-back-end-code-challenge

Multiplayer game of up to 4 players. The game consists of presenting multiplication challenges to each participant, the first person to answer the sum correctly wins the round.

## Sub-modules

The project comprises of 2 sub-modules.

* [Client](Client): Where player creates, joines games and see results (Spring shell application).
* [Server](Server): Server that saves all the data, generates all the games and players, runs on postgres db(postgres db runs in docker image) (Spring boot application).

## Build & Dependency Management

Maven is used for build and dependency management.

### Build

You can perform a build using the standard Maven build process:
```
mvn clean install
```

Unit test will run with this command.

### IntelliJ

#### Required Plugins

You'll need the following dependencies and plugins to import the project:
* Java 8
* JUnit
* Maven Integration
* Spring Boot
* Lombok

### Runtime Dependencies

There is runtime dependancy for postgres for the server. This dependancy runs in docker.

To start this depenecy run (run this in server package where docker file is located):
1) "docker build -t game-postgres ."
2) "docker run -p 5432:5432 -d game-postgres:latest"

### Start server/client

Run "mvn clean spring-boot:run -U" in server/client submodule to start respective submodule.

### Start client

Run "mvn clean spring-boot:run -U" in client submodule.

### Configuration

Settings for server and client is under resources folder in application.properties for respective submodules.

Order and example of properties files in spring:
https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html

Example of server settings:

spring.datasource.url = jdbc:postgresql://localhost:5432/docker
spring.datasource.username = docker
spring.datasource.password = docker
spring.jpa.hibernate.ddl-auto = update
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.PostgreSQLDialect

game.players=4
game.time=20
game.rounds=3

Example of client settings:

server.ip = localhost
server.port = 8080

### Shortcummings

*Still need to implement proper scoring for response times. Want to implement ratio, earning more points there faster you are than other players.
*Also want to implement elo system, with proper rankings.
*The server still needs to run in docker container.
*Want to rework the network to use event driven push model instead of poling model I went with.
*Want to rewrite it in golang :P