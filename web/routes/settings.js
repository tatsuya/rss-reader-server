var express = require('express');
var router = express.Router();

var subscription = require('../lib/subscription');

router.get('/', function(req, res, next) {
  console.log('Getting current subscriptions...');
  subscription.list(function(err, subscriptions) {
    if (err) {
      return next(err);
    }
    res.render('settings', {
      title: 'RSS reader - Settings',
      subscriptions: subscriptions
    });
  });
});

router.post('/subscriptions', function(req, res, next) {
  var url = req.body.url;
  console.log('Subscribing ' + url);
  subscription.subscribe(url, function(err) {
    if (err) {
      return next(err);
    }
    res.redirect('/settings');
  });
});

router.post('/subscriptions/:id/delete', function(req, res, next) {
  var id = req.params.id;
  console.log('Unsubscribing ' + id);
  subscription.unsubscribe(id, function(err) {
    if (err) {
      return next(err);
    }
    res.redirect('/settings');
  });
});

module.exports = router;
