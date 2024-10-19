package service;

import chess.ChessGame;
import com.google.gson.JsonIOException;
import dataaccess.DataAccessException;
import model.GameData;
import model.reponses.CreateGameResponse;
import model.reponses.ErrorResponse;
import model.reponses.ListGamesResponse;
import model.requests.JoinGameRequest;
import model.results.CreateGameResult;
import model.results.JoinGameResult;
import model.results.ListGamesResult;

import java.util.ArrayList;

import static service.Service.*;

public class GameService {

    public GameService() {
    }


    public CreateGameResult createGame(String authToken, String gameName) {
        try {
            if (authToken == null || gameName == null) {
                return new CreateGameResult(false, 400, new ErrorResponse("Error: missing fields"), null);
            }

            if (authDataAccess.getAuth(authToken) == null) {
                return new CreateGameResult(false, 401, new ErrorResponse("Error: unauthorized"), null);
            }

            GameData newGame = gameDataAccess.newGame(gameName);
            gameDataAccess.createGame(newGame);

            return new CreateGameResult(true, 200, new ErrorResponse("{}"), new CreateGameResponse(newGame.gameID()));

        } catch (Exception e) {
            return new CreateGameResult(false, 500, new ErrorResponse("Error: " + e.getMessage()), null);
        }
    }

    public JoinGameResult joinGame(String authToken, JoinGameRequest joinGameRequest) {
        try {
            if (authToken == null || joinGameRequest == null || joinGameRequest.playerColor() == null || joinGameRequest.gameID() == null) {
                return new JoinGameResult(false, 400, new ErrorResponse("Error: missing fields"));
            }

            if (authDataAccess.getAuth(authToken) == null) {
                return new JoinGameResult(false, 401, new ErrorResponse("Error: unauthorized"));
            }

            var playerColor = joinGameRequest.playerColor();
            int gameID = joinGameRequest.gameID();

            if (gameDataAccess.getGame(gameID) == null) {
                return new JoinGameResult(false, 400, new ErrorResponse("Error: game not found"));
            }

            var user = authDataAccess.getAuth(authToken);
            var success = gameDataAccess.joinGame(user.username(), playerColor, gameID);

            if (!success) {
                return new JoinGameResult(false, 403, new ErrorResponse("Error: player color already taken"));
            }

            return new JoinGameResult(true, 200, new ErrorResponse("Error: player color already taken"));

        } catch (Exception e) {
            return new JoinGameResult(false, 500, new ErrorResponse("Error: " + e.getMessage()));
        }
    }

    public ListGamesResult listGames(String authToken) {
        try {
            if (authToken == null) {
                return new ListGamesResult(false, 400, new ErrorResponse("Error: missing fields"), null);
            }

            if (authDataAccess.getAuth(authToken) == null) {
                return new ListGamesResult(false, 401, new ErrorResponse("Error: unauthorized"), null);
            }

            ListGamesResponse listGamesResponse = new ListGamesResponse(gameDataAccess.getAllGames());

            return new ListGamesResult(true, 200, new ErrorResponse("{}"), listGamesResponse);

        } catch (Exception e) {
            return new ListGamesResult(false, 500, new ErrorResponse("Error: " + e.getMessage()), null);
        }
    }
}
