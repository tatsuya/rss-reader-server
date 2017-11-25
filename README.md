# rss-reader

## Prerequisite

- Java 8
- Maven 3
- Heroku (Optional)

## Deploying on Heroku

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
