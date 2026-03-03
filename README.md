# rss-reader

RSS reader app with a Java backend API and a Node.js frontend.

## Structure

- `server/` — REST API built with Java, Jetty, JAX-RS (Jersey), Google Guice and H2
- `web/` — Frontend built with Node.js (Express)

## Prerequisites

- Java 11+
- Maven 3
- Node.js

## Running locally

### Server

```
cd server
mvn package
java -cp "target/classes:target/dependency/*" com.tatsuyaoiw.rssreader.Main
```

Starts on port 8080 by default. Set the `PORT` environment variable to override it.

Uses an embedded H2 database — no external database setup required. Data is persisted to `rss-reader.mv.db` in the working directory. To use a different database, set `JDBC_DATABASE_URL` to any JDBC-compatible connection string.

### Web

```
cd web
npm install
RSS_READER_API_URL=http://localhost:8080/ npm start
```

Starts on port 3000. Open http://localhost:3000.

## API

### GET `/subscriptions`

List subscriptions.

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
                    "description": "The AG isn't helped by public badgering from the Oval Office."
                }
            ]
        }
    }
]
```

### GET `/subscriptions/:id`

Get subscription.

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
                "description": "The AG isn't helped by public badgering from the Oval Office."
            }
        ]
    }
}
```

### POST `/subscriptions`

Subscribe to a feed. Any RSS feed URL should work, for example [these URLs](https://www.wsj.com/news/rss-news-and-feeds) from The Wall Street Journal.

Request:

```json
{
  "url": "https://feeds.a.dj.com/rss/RSSOpinion.xml"
}
```

```
201 Created
```

### DELETE `/subscriptions/:id`

Unsubscribe from a feed.

```
204 No Content
```
