# rss-reader-server

RSS reader service API, built with Java, Jetty, JAX-RS (Jersey), Google Guice and H2.

## Prerequisite

- Java 11+
- Maven 3

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

```
mvn package
java -cp "target/classes:target/dependency/*" com.tatsuyaoiw.rssreader.Main
```

The app uses an embedded H2 database — no external database setup required. Data is persisted to `rss-reader.mv.db` in the working directory.

The server starts on port 8080 by default. Set the `PORT` environment variable to override it.

To use a different database, set `JDBC_DATABASE_URL` to any JDBC-compatible connection string.

## TODO

- Write unit tests for repositories
