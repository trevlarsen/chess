package service;

import chess.ChessGame;
import dataaccess.DataAccessException;
import model.GameData;
import model.UserData;
import model.reponses.CreateGameResponse;
import model.reponses.ErrorResponse;
import model.reponses.UserResponse;
import model.results.CreateGameResult;
import model.results.UserResult;

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

    public void joinGame() throws DataAccessException {
        try {
            var i = 9;
        } catch (Exception e) {
            throw new DataAccessException("Error:" + e.getMessage());
        }
    }

    public ArrayList<GameData> listGames() throws DataAccessException {
        try {
            return new ArrayList<>();
        } catch (Exception e) {
            throw new DataAccessException("Error:" + e.getMessage());
        }
    }
}
