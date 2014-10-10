var Note = require('models/note').Note;

exports.post = function(req, res, next) {
    var username = req.body.username;
    var text = req.body.text;

    Note.remove({ text: text, username: username }, function(err) {
        if (err) return next(err);
        res.send(200);
        // ... 200 OK
    });
}