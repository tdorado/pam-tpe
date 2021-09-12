const express = require("express");

const app = express();

app.use(express.json())

const router = express.Router();

const { groups } = require("./groups.json");
const { events } = require("./events.json");
const { users } = require("./users.json");
console.log("loaded jsons")
let chargeIds = 1;

router.post("/users/debts", (req, res) => {
  console.log("Received request to", req.path, "with params", req.body);
  const { debt_id, from_user_id, to_user_id, amount } = req.body;
  return res.json({ amount_payed: amount });
});

router.get("/users", (req, res) => {
  console.log("Received request to", req.path, "with params", req.query);
  const { user_email } = req.query;
  if (user_email === "tdallas@itba.edu.ar") {
    res.json({ user: users[0] });
  } else if (userEmail === "tdorado@itba.edu.ar") {
    res.json({ user: users[1] });
  } else {
    res.json({ user: users[2] });
  }
});

router.get("/groups", (req, res) => {
  console.log("Received request to", req.path, "with params", req.query);

  res.json({ groups });
});

router.get("/groups/:group_id", (req, res) => {
  console.log("Received request to", req.path, "with params", req.params);

  const { group_id } = req.params;
  res.json({ group: groups[group_id] });
});

router.get("/events", (req, res) => {
  console.log("Received request to", req.path, "with params", req.query);

  res.json({ events });
});

router.get("/events/:event_id", (req, res) => {
  console.log("Received request to", req.path, "with params", req.params);

  const { event_id } = req.params;
  res.json({ event: events[event_id] });
});

// to event or group
router.post("/charges", (req, res) => {
  console.log("Received request to", req.path, "with params", req.body);

  res.json({ charge: { id: chargeIds++, ...req.body } });
});

app.use("/", router);

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
  res.json("error");
});

console.log("Listening pot 8080")
app.listen(8080);

module.exports = app;
