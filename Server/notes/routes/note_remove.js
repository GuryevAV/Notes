var Note = require('models/note').Note;

exports.post = function(req, res, next) {
    //var username = req.body.username;
    //var text = req.body.text;
    var _id = req.body._id;

    Note.remove({ _id: _id }, function(err) {
        if (err) return next(err);
        res.send(200);
        // ... 200 OK
    });
}