var User = require('models/user').User;
var HttpError = require('error').HttpError;

exports.get = function(req, res, next) {
    User.findOne({username: req.params.id}, function (err, user) {
        if (err) return next(err);
        if (user) {
            res.send(user.salt, {
                'Content-Type': 'text/plain'
            }, 201);
        } else {
            res.send(403);
        }
    })
}