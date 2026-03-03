# rss-reader-web

Front-end part of RSS reader app, built with Node.js (Express).

This app requires *RSS reader server* running as a backend API. Set the URL of the server API to `RSS_READER_API_URL` environment variable. The default value of `RSS_READER_API_URL` is `http://localhost:5000/`.

## Running locally

```
npm install
npm start
```

## Deploying on Heroku

Set the Heroku environment variable by `heroku config:set` command.

```
heroku login
heroku create
heroku config:set RSS_READER_API_URL=https://rss-reader-server.herokuapp.com/
git push heroku master
heroku open
```
