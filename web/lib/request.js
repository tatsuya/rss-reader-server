var RSS_READER_API_URL = process.env.RSS_READER_API_URL || 'http://localhost:8080';

function doRequest(path, options, callback) {
  var uri = new URL(path, RSS_READER_API_URL);
  var init = { method: options.method || 'GET' };

  if (options.body) {
    init.headers = { 'Content-Type': 'application/json' };
    init.body = JSON.stringify(options.body);
  }

  fetch(uri, init)
    .then(function(res) {
      if (res.status < 200 || res.status >= 300) {
        throw new Error('RSS reader API returned status code ' + res.status);
      }
      return res.text();
    })
    .then(function(body) {
      if (!body) return callback(null);
      console.log('Response from RSS reader API: ' + body);
      try {
        callback(null, JSON.parse(body));
      } catch (err) {
        callback(new Error('Unable to parse body: ' + body + ', reason: ' + err.message));
      }
    })
    .catch(function(err) {
      callback(new Error('Problem on RSS reader API: ' + err.message));
    });
}

exports.get = function(path, callback) {
  return doRequest(path, {}, callback);
};

exports.post = function(path, body, callback) {
  return doRequest(path, { method: 'POST', body: body }, callback);
};

exports.delete = function(path, callback) {
  return doRequest(path, { method: 'DELETE' }, callback);
};
