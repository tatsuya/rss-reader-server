var express = require('express');
var router = express.Router();

var subscription = require('../lib/subscription');

router.get('/:id', function(req, res, next) {
  var id = req.params.id;
  console.log('Getting subscription ' + id);
  subscription.get(id, function(err, subscription) {
    if (err) {
      return next(err);
    }
    res.render('subscription', {
      title: 'RSS reader - ' + subscription.feed.title,
      subscription: subscription
    });
  });
});

module.exports = router;
