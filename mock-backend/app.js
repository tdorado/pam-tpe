const express = require('express');

const app = express();

const router = express.Router();

app.use(logger('dev'));

router.post('/debt/pay', (req, res) => {});

router.get('/groups/{userId}', (req, res) => {});

router.get('/groups/{groupId}', (req, res) => {});

router.get('/events/{userId}', (req, res) => {});

router.get('/events/{eventId}', (req, res) => {});

// to event or group
router.post('/charge', (req, res) => {})

app.use('/', router);

// catch 404 and forward to error handler
app.use(function (req, res, next) {
    next(createError(404));
});

// error handler
app.use(function (err, req, res, next) {
    // set locals, only providing error in development
    res.locals.message = err.message;

    // render the error page
    res.status(err.status || 500);
    res.json('error');
});

app.listen(9090);

module.exports = app;
