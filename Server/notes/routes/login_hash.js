var User = require('models/user').User;
var HttpError = require('error').HttpError;
var AuthError = require('models/user').AuthError;

exports.post = function(req, res, next) {
    var username = req.body.username;
    var hash = req.body.hash;
    var salt = req.body.salt;

    if (salt) {
        User.createuser(username, salt, hash, function (err, user) {
            if (err) {
                return next(err);
            }
            req.session.user = user._id;
            res.send(200);
        });
    } else {
        User.authhash(username, hash, function (err, user) {
            if (err) {
                if (err instanceof AuthError) {
                    return next(new HttpError(403, err.message));
                } else {
                    return next(err);
                }
            }
            req.session.user = user._id;
            res.send(200);
        });
    }
};