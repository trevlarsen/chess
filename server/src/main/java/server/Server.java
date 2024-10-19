package server;

import spark.*;

public class Server {

    public Server() {
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.delete("/db", (req, res) -> (Handler.getInstance()).clear(res));

        Spark.post("/user", (req, res) -> (UserHandler.getInstance()).register(req, res));
        Spark.post("/session", (req, res) -> (UserHandler.getInstance()).login(req, res));
        Spark.delete("/session", (req, res) -> (UserHandler.getInstance()).logout(req, res));

        Spark.post("/game", (req, res) -> (GameHandler.getInstance()).createGame(req, res));
        Spark.put("/game", (req, res) -> (GameHandler.getInstance()).joinGame(req, res));
        Spark.get("/game", (req, res) -> (GameHandler.getInstance()).listGames(req, res));

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
