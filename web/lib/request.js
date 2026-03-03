var request = require('request');
var url = require('url');

var RSS_READER_API_URL = process.env.RSS_READER_API_URL || 'http://localhost:5000';

function doRequest(path, options, callback) {
  options.uri = url.resolve(RSS_READER_API_URL, path);
  request(options, function(err, res, body) {
    if (err) {
      return callback(new Error('Problem on RSS reader API: ' + err.message));
    }

    if (res.statusCode < 200 || res.statusCode >= 300) {
      return callback(new Error('RSS reader API returned status code ' + res.statusCode));
    }

    if (!body) {
      return callback(null);
    }

    console.log('Response from RSS reader API:' + body);

    var obj;
    try {
      obj = JSON.parse(body);
    } catch (err) {
      return callback(new Error('Unable to parse body:' + body + ', reason:'+ err.message));
    }

    return callback(null, obj);
  });
}

exports.get = function(path, callback) {
  return doRequest(path, {}, callback)
};

exports.post = function(path, body, callback) {
  return doRequest(path, {method: 'POST', body: body, json: true}, callback);
};

exports.delete = function(path, callback) {
  return doRequest(path, {method: 'DELETE'}, callback);
};
