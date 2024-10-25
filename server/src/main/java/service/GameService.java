package service;

import model.GameData;
import model.responses.CreateGameResponse;
import model.responses.ErrorResponse;
import model.responses.ListGamesResponse;
import model.requests.JoinGameRequest;
import model.results.CreateGameResult;
import model.results.JoinGameResult;
import model.results.ListGamesResult;

import static service.BaseService.*;

/**
 * Provides game-related operations such as the creation, joining, and listing of games.
 */
public class GameService {

    public GameService() {
    }

    /**
     * Creates a new game with the specified name if the user is authorized.
     *
     * @param authToken the token used to authenticate the user
     * @param gameName  the name of the game to be created
     * @return a {@link CreateGameResult} containing the status of the creation
     */
    public CreateGameResult createGame(String authToken, String gameName) {
        try {
            if (ValidationService.inputIsInvalid(authToken, gameName)) {
                return CreateGameResult.error(400, ErrorResponse.missingFields());
            }

            if (ValidationService.isUnauthorized(authToken)) {
                return CreateGameResult.error(401, ErrorResponse.unauthorized());
            }

            GameData newGame = GAME_DAO.newGame(gameName);
            GAME_DAO.addGame(newGame);
            return new CreateGameResult(true, 200, ErrorResponse.empty(), new CreateGameResponse(newGame.gameID()));

        } catch (Exception e) {
            return CreateGameResult.error(500, ErrorResponse.internal(e.getMessage()));
        }
    }

    /**
     * Allows a user to join a game with a specific color if authorized and the game exists.
     *
     * @param authToken       the token used to authenticate the user
     * @param joinGameRequest the {@link JoinGameRequest} containing the game ID and player color
     * @return a {@link JoinGameResult} indicating whether the user successfully joined the game
     */
    public JoinGameResult joinGame(String authToken, JoinGameRequest joinGameRequest) {
        try {
            if (ValidationService.inputIsInvalid(authToken, joinGameRequest)) {
                return JoinGameResult.error(400, ErrorResponse.missingFields());
            }

            if (ValidationService.isUnauthorized(authToken)) {
                return JoinGameResult.error(401, ErrorResponse.unauthorized());
            }

            var playerColor = joinGameRequest.playerColor();
            int gameID = joinGameRequest.gameID();

            if (GAME_DAO.getGame(gameID) == null) {
                return JoinGameResult.error(400, ErrorResponse.gameNotFound());
            }

            var user = AUTH_DAO.getAuth(authToken);
            var success = GAME_DAO.joinGame(user.username(), playerColor, gameID);

            if (!success) {
                return JoinGameResult.error(403, ErrorResponse.colorTaken());
            }

            return new JoinGameResult(true, 200, ErrorResponse.empty());

        } catch (Exception e) {
            return JoinGameResult.error(500, ErrorResponse.internal(e.getMessage()));
        }
    }

    /**
     * Lists all games available in the system for an authorized user.
     *
     * @param authToken the token used to authenticate the user
     * @return a {@link ListGamesResult} containing a list of all available games if authorized
     */
    public ListGamesResult listGames(String authToken) {
        try {
            if (ValidationService.inputIsInvalid(authToken)) {
                return ListGamesResult.error(400, ErrorResponse.missingFields());
            }

            if (ValidationService.isUnauthorized(authToken)) {
                return ListGamesResult.error(401, ErrorResponse.unauthorized());
            }

            ListGamesResponse listGamesResponse = new ListGamesResponse(GAME_DAO.getAllGames());
            return new ListGamesResult(true, 200, ErrorResponse.empty(), listGamesResponse);

        } catch (Exception e) {
            return ListGamesResult.error(500, ErrorResponse.internal(e.getMessage()));
        }
    }
}
