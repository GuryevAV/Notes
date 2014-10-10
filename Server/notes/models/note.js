var mongoose = require('lib/mongoose'),
    Schema = mongoose.Schema;

var schema = new Schema({
    username: {
        type: String,
        required: true
    },
    text: {
        type: String,
        required: true
    },
    created: {
        type: Date,
        default: Date.now
    }
});


exports.Note = mongoose.model("Note", schema);