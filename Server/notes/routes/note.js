var Note = require('models/note').Note;

exports.get = function(req, res) {
    Note.find({ username: req.params.id}, function (err, notes) {
        if (err) return next(err);
        res.json(notes);
    })
}

exports.post = function(req, res, next) {
    var username = req.body.username;
    var text = req.body.text;

    var note = new Note({username: username, text: text});
    note.save(function(err) {
        if (err) return next(err);
        res.json(note);
        //res.send(200);
        // ... 200 OK
    });
}