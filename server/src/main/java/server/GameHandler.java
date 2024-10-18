package server;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import service.GameService;
import spark.Request;
import spark.Response;

public class GameHandler {

    private final GameService service = new GameService();

    private static GameHandler instance;

    public GameHandler() throws DataAccessException {
    }

    public static GameHandler getInstance() throws DataAccessException {
        if (instance == null) {
            instance = new GameHandler(); // Ensure the instance is assigned here
        }
        return instance;
    }

    public Object createGame(Request req, Response res) {
        return new Object();
    }

    public Object joinGame(Request req, Response res) {
        return new Object();
    }

    public Object listGames(Request req, Response res) {
        return new Object();
    }

}
