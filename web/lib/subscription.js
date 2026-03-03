var request = require('./request');

exports.list = function(callback) {
  request.get('subscriptions', callback);
};

exports.get = function(id, callback) {
  request.get('subscriptions/' + id, callback);
};

exports.subscribe = function(url, callback) {
  request.post('subscriptions', {url: url}, callback);
};

exports.unsubscribe = function(id, callback) {
  request.delete('subscriptions/' + id, callback);
};