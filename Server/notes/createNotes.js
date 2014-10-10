var mongoose = require('lib/mongoose');
mongoose.set('debug', true);
var async = require('async');


// 1. drop database
// 2. create & save 3 users
// 3. close connection

async.series([
    open,
    //dropDatabase,
    requireModels,
    createUsers,
    close
], function(err) {
    console.log(arguments);
    mongoose.disconnect();
    process.exit(err ? 255 : 0);
});

function open(callback) {
    mongoose.connection.on('open', callback);
}

function dropDatabase(callback) {
    var db = mongoose.connection.db;
    db.dropDatabase(callback);
}

function requireModels(callback) {
    require('models/note');

    async.each(Object.keys(mongoose.models), function(modelName, callback) {
        mongoose.models[modelName].ensureIndexes(callback);
    }, callback);
}

function createUsers(callback) {

    var notes = [
        {username: 'Вася', text: 'Позвонить на работу'},
        {username: 'Петя', text: '123484835'},
        {username: 'admin', text: 'На след. неделе к врачу'},
        {username: 'Вася', text: 'ds678345'},
        {username: 'Петя', text: '21.21.334567'},
        {username: 'admin', text: 'Творог, фарш, апельсины'},
        {username: 'Вася', text: 'Гете'},
        {username: 'Петя', text: 'Марина'},
        {username: 'admin', text: 'NFS'},
        {username: 'Вася', text: 'Крылова, 12'},
        {username: 'Петя', text: '123.50'},
        {username: 'admin', text: 'the true hero'}
    ];

    async.each(notes, function(noteData, callback) {
        var note = new mongoose.models.Note(noteData);
        note.save(callback);
    }, callback);
}

function close(callback) {
    mongoose.disconnect(callback);
}

