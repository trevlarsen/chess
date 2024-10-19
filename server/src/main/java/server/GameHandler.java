package server;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import model.reponses.EmptyResponse;
import model.requests.CreateGameRequest;
import model.requests.JoinGameRequest;
import model.results.CreateGameResult;
import model.results.JoinGameResult;
import model.results.ListGamesResult;
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
        String authToken = request.headers("authorization");
        JoinGameRequest joinGameRequest = new Gson().fromJson(request.body(), JoinGameRequest.class);

        JoinGameResult result = service.joinGame(authToken, joinGameRequest);

        response.status(result.statusCode());
        if (result.success()) {
            return new Gson().toJson(new EmptyResponse());
        } else {
            return new Gson().toJson(result.errorMessage());
        }
    }

    public Object listGames(Request request, Response response) {
        String authToken = request.headers("authorization");

        ListGamesResult result = service.listGames(authToken);

        response.status(result.statusCode());
        if (result.success()) {
            return new Gson().toJson(result.listGamesResponse());
        } else {
            return new Gson().toJson(result.errorMessage());
        }
    }

}
