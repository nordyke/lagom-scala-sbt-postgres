# Lagom starter with Postgres

This is the Lagom starter application, with Postgres instead of Cassandra, both for persistence and for read-side.

## Getting started

The only setup is to create your database with the configurations specified in `application.json`.
Then run the app.


## Create a postgres docker instance and your database

```
docker run --name postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_USER=postgres -p 5432:5432 -d postgres:alpine
docker exec -it <container id> psql -U postgres        (password is 'postgres')

create database lagom;
```

## Start the application

```
sbt runAll
```

## First things to try

### 1. Basic API

Open localhost:9000/api/hello/Billy

### 2. Publish a command

It will persist an event and publish to Kafka

```
// Publish a command to Alice entity
curl -H "Content-Type: application/json" -X POST -d '{"message": "Hi"}' http://localhost:9000/api/hello/Alice
```

Now look at your database. The event was persisted in the event journal.

### Next steps to try

1. Read the docs. https://www.lagomframework.com/documentation/1.5.x/scala/Home.html
2. Write a read-side to turn the events in a queryable view model. https://www.lagomframework.com/documentation/1.5.x/scala/ReadSideSlick.html
3. Spin up another Lagom service and get the two to communicate.
4. Create another Entity.

### For the ambitious

1. There is no official RabbitMQ client for the Message Broker API. Write one!

