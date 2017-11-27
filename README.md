# rss-reader

RSS reader service API, built with Java 8, JAX-RS (Jersey), Jersey, Google Guice and PostgreSQL.

## Prerequisite

- Java 8
- Maven 3
- PostgreSQL

## Class diagram

Please see: `rss-reader-class-diagram.png` placed in this project directory.

## API

### GET `/subscriptions`

List subscriptions.

Response:

```
200 OK
```

```json
[
  {
    "id": 1,
    "url": "http://www.wsj.com/xml/rss/3_7085.xml",
    "feed": {
      "title": "WSJ.com: World News",
      "description": "World News",
      "entries": [
        {
          "title": "Pakistan Stops Anti-",
          "link": "https://www.wsj.com/articles/pakistan-stops-anti-protest-operation-after-deadly-clashes-1511690273?mod=fox_australian",
          "description": "An operation to clear a protest by Islamist activists in the capital was on hold Sunday morning after at least seven people were killed and "
        }
      ]
    }
  }
]
```

### GET `/subscriptions/:id`

Get subscription.

Response:

```
200 OK
```

```json
{
  "id": 1,
  "url": "http://www.wsj.com/xml/rss/3_7085.xml",
  "feed": {
    "title": "WSJ.com: World News",
    "description": "World News",
    "entries": [
      {
        "title": "Pakistan Stops Anti-",
        "link": "https://www.wsj.com/articles/pakistan-stops-anti-protest-operation-after-deadly-clashes-1511690273?mod=fox_australian",
        "description": "An operation to clear a protest by Islamist activists in the capital was on hold Sunday morning after at least seven people were killed and "
      }
    ]
  }
}
```

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

### DELETE `/subscriptions/:id`

Unsubscribe feed.

```
204 No Content
```

## Running locally

Rebuild artifact:

```
mvn -DskipTests clean install
java -cp "target/classes:target/dependency/*" "com.tatsuyaoiw.Main"
```

This is how Jetty is started on Heroku and the command is defined in `Procfile`.

## Deploying on Heroku

Note: [Heroku Postgres](https://devcenter.heroku.com/articles/heroku-postgresql) add-on needs to be added before deployment.

```
heroku login
heroku create
git push heroku master
heroku open
```
