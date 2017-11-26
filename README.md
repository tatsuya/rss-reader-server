# rss-reader

RSS reader application backend service API, built with Java 8, JAX-RS (Jersey), Jersey, Google Guice and PostgreSQL.

## Prerequisite

- Java 8
- Maven 3
- PostgreSQL

## Deploying on Heroku

The application is primarily made for deploying on Heroku. Note that [Heroku Postgres](https://devcenter.heroku.com/articles/heroku-postgresql) add-on needs to be added before deployment.

```
heroku login
heroku create
git push heroku master
heroku open
```

## Running locally

Rebuild artifact:

```
mvn -DskipTests clean install
```

And run by either:

```
heroku local web
```

or:

```
mvn -DskipTests clean install
java -cp "target/classes:target/dependency/*" "com.tatsuyaoiw.Main"
```

This is how Jetty is started on Heroku and the command is defined in `Procfile`, the configuration file for Heroku.

## API

### GET `/feeds`

List feeds.

### GET `/subscriptions`

List subscriptions.

### POST `/subscriptions`

Subscribe feed.

Request:

```json
{
  "url": "http://www.wsj.com/xml/rss/3_7085.xml"
}
```

Response:

```
201 Created
```
