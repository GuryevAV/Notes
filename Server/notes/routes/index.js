var checkAuth = require('middleware/checkAuth');

module.exports = function(app) {

    //app.get('/login', require('./login').get);
    //app.post('/login', require('./login').post);
    app.post('/login_hash', require('./login_hash').post);
    app.post('/logout', require('./logout').post);

    app.get('/notes/:id', checkAuth, require('./note').get);
    app.post('/note', checkAuth, require('./note').post);
    app.post('/note_remove', checkAuth, require('./note_remove').post);

    app.get('/salt/:id', require('./salt').get);
};

/*
var User = require('models/user').User;
var HttpError = require('error').HttpError;
var ObjectID = require('mongodb').ObjectID;

module.exports = function(app) {
    app.get('/', function (req, res, next) {
        res.render("index", {});
    });

    var User = require('models/user').User;
    app.get('/users', function (req, res, next) {
        User.find({}, function (err, users) {
            if (err) return next(err);
            res.json(users);
        })
    });

    app.get('/user/:id', function (req, res, next) {
        try {
            var id = new ObjectId(req.params.id);
        } catch (e) {
            return next(404);
        }
        User.findById(id, function (err, user) {
            if (err) return next(err);
            if (!user) {
                next(new HttpError(404, "User not found"));
            } else {
                res.json(user);
            }
        });
    });
};*/
