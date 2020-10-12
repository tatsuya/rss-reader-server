# rss-reader-server

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

Subscribe feed. Any RSS feed URL should work, for example, [these URLs](https://www.wsj.com/news/rss-news-and-feeds) are available in The Wall Street Journal.

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

## Local development

### Running MySQL

In this repository, we have a [Docker Compose](https://docs.docker.com/compose/) file (`docker-compose.yml`) to create and start MySQL service locally.

```
docker-compose up -d
```

### Build an artifact

```
mvn -DskipTests clean install
java -cp "target/classes:target/dependency/*" "com.tatsuyaoiw.rssreader.Main"
```

This is how we start Jetty on Heroku. You can check the actual command in the `Procfile`.

## Deploying on Heroku

Note: With Heroku, this application requires the [JawsDB MySQL](https://elements.heroku.com/addons/jawsdb) add-on installed as the MySQL data source.

```
heroku login
heroku create
git push heroku master
heroku open
```

## TODO

- Write unit tests for repositories
