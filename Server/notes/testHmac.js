var crypto = require('crypto');

var mykey = "0.7471963369753212";
var test = "supervasya";

var result = crypto.createHmac('sha1', mykey).update(test).digest('hex');
console.log(result);