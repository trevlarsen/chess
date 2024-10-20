package server;

import spark.Spark;

public class Server {

    public Server() {
    }

    /**
     * Runs the Spark server on the specified port.
     *
     * @param desiredPort the port number to run the server on
     * @return the actual port the server is running on
     */
    public int run(int desiredPort) {
        Spark.port(desiredPort);
        Spark.staticFiles.location("web");

        registerEndpoints();

        Spark.awaitInitialization();
        return Spark.port();
    }

    /**
     * Registers the endpoints for the server.
     */
    private void registerEndpoints() {
        Spark.delete("/db", (req, res) -> BaseHandler.getInstance().clear(res));
        Spark.post("/user", (req, res) -> UserHandler.getInstance().register(req, res));
        Spark.post("/session", (req, res) -> UserHandler.getInstance().login(req, res));
        Spark.delete("/session", (req, res) -> UserHandler.getInstance().logout(req, res));
        Spark.post("/game", (req, res) -> GameHandler.getInstance().createGame(req, res));
        Spark.put("/game", (req, res) -> GameHandler.getInstance().joinGame(req, res));
        Spark.get("/game", (req, res) -> GameHandler.getInstance().listGames(req, res));
    }

    /**
     * Stops the Spark server.
     */
    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
