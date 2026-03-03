var express = require('express');
var router = express.Router();

var subscription = require('../lib/subscription');

router.get('/', function(req, res, next) {
  console.log('Getting current subscriptions...');
  subscription.list(function(err, subscriptions) {
    if (err) {
      return next(err);
    }
    res.render('index', {
      title: 'RSS reader - Home',
      subscriptions: subscriptions
    });
  });
});

module.exports = router;
