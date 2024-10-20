package server;

import com.google.gson.Gson;
import model.responses.EmptyResponse;
import model.requests.CreateGameRequest;
import model.requests.JoinGameRequest;
import model.results.CreateGameResult;
import model.results.JoinGameResult;
import model.results.ListGamesResult;
import service.GameService;
import spark.Request;
import spark.Response;

import static server.BaseHandler.handleResponse;

public class GameHandler {

    private final GameService service = new GameService();
    private static final Gson GSON = new Gson();
    private static volatile GameHandler instance;

    private GameHandler() {
    }

    /**
     * Provides a singleton instance of GameHandler.
     *
     * @return the single instance of GameHandler
     */
    public static GameHandler getInstance() {
        if (instance == null) {
            synchronized (GameHandler.class) {
                if (instance == null) {
                    instance = new GameHandler();
                }
            }
        }
        return instance;
    }

    /**
     * Handles the creation of a new game.
     *
     * @param request  the HTTP request containing game data
     * @param response the HTTP response object
     * @return JSON response indicating success or failure
     */
    public Object createGame(Request request, Response response) {
        String authToken = request.headers("authorization");
        CreateGameRequest createGameRequest = GSON.fromJson(request.body(), CreateGameRequest.class);
        CreateGameResult result = service.createGame(authToken, createGameRequest.gameName());
        return handleResponse(response, result.statusCode(), result.success(), result.createGameResponse(), result.errorMessage());
    }

    /**
     * Handles user requests to join a game.
     *
     * @param request  the HTTP request containing join game data
     * @param response the HTTP response object
     * @return JSON response indicating success or failure
     */
    public Object joinGame(Request request, Response response) {
        String authToken = request.headers("authorization");
        JoinGameRequest joinGameRequest = GSON.fromJson(request.body(), JoinGameRequest.class);
        JoinGameResult result = service.joinGame(authToken, joinGameRequest);
        return handleResponse(response, result.statusCode(), result.success(), new EmptyResponse(), result.errorMessage());
    }

    /**
     * Handles requests to list all available games.
     *
     * @param request  the HTTP request
     * @param response the HTTP response object
     * @return JSON response indicating success or failure
     */
    public Object listGames(Request request, Response response) {
        String authToken = request.headers("authorization");
        ListGamesResult result = service.listGames(authToken);
        return handleResponse(response, result.statusCode(), result.success(), result.listGamesResponse(), result.errorMessage());
    }

}
