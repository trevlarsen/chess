package server;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import model.UserData;
import model.requests.CreateGameRequest;
import model.results.CreateGameResult;
import model.results.UserResult;
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

    public Object createGame(Request request, Response response) {
        String authToken = request.headers("authorization");
        CreateGameRequest createGameRequest = new Gson().fromJson(request.body(), CreateGameRequest.class);

        CreateGameResult result = service.createGame(authToken, createGameRequest.gameName());

        response.status(result.statusCode());
        if (result.success()) {
            return new Gson().toJson(result.createGameResponse());
        } else {
            return new Gson().toJson(result.errorMessage());
        }
    }

    public Object joinGame(Request request, Response response) {
        return new Object();
    }

    public Object listGames(Request request, Response response) {
        return new Object();
    }

}
