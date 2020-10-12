# rss-reader

RSS reader service API, built with Java 8, Jetty, JAX-RS (Jersey), Google Guice and MySQL.

## Prerequisite

- Java 8
- Maven 3
- MySQL

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
        "url": "https://feeds.a.dj.com/rss/RSSOpinion.xml",
        "feed": {
            "title": "RSSOpinion",
            "description": "RSSOpinion",
            "entries": [
                {
                    "title": "Trump's Misguided Sw",
                    "link": "https://www.wsj.com/articles/trumps-misguided-swipe-at-bill-barr-11602449244",
                    "description": "The AG isn’t helped by public badgering from the Oval Office."
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
    "url": "https://feeds.a.dj.com/rss/RSSOpinion.xml",
    "feed": {
        "title": "RSSOpinion",
        "description": "RSSOpinion",
        "entries": [
            {
                "title": "Trump's Misguided Sw",
                "link": "https://www.wsj.com/articles/trumps-misguided-swipe-at-bill-barr-11602449244",
                "description": "The AG isn’t helped by public badgering from the Oval Office."
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
  "url": "https://feeds.a.dj.com/rss/RSSOpinion.xml"
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
java -cp "target/classes:target/dependency/*" "com.tatsuyaoiw.rssreader.Main"
```

This is how Jetty is started on Heroku and the command is defined in `Procfile`.

## Deploying on Heroku

Note: A MySQL add-on such as [Heroku Postgres](https://elements.heroku.com/addons/cleardb) needs to be added before deployment.

```
heroku login
heroku create
git push heroku master
heroku open
```

## Local development

In this repository, we have a [Docker Compose](https://docs.docker.com/compose/) file (`docker-compose.yml`) to create and start MySQL service locally.

```
docker-compose up -d
```

## TODO

- Write unit tests for repositories
