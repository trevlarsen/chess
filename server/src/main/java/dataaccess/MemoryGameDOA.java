package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.ArrayList;

public class MemoryGameDOA implements GameDOAInterface {

    private static ArrayList<GameData> gameDatabase = new ArrayList<>();
    private int nextGameID = 1;

    public MemoryGameDOA() {
    }


    @Override
    public GameData newGame(String gameName) {
        return new GameData(nextGameID++, null, null, gameName, new ChessGame());
    }

    // Adds a game to the database
    @Override
    public void createGame(GameData gameData) {
        gameDatabase.add(gameData);
    }

    // Find and return a game based on a gameID
    @Override
    public GameData getGame(int gameID) {
        for (var game : gameDatabase) {
            if (game.gameID() == gameID) {
                return game;
            }
        }
        return null;
    }

    // Returns a list of all games in the database
    @Override
    public ArrayList<GameData> getAllGames() {
        return gameDatabase;
    }

    // Add a user by username to a game using the gameID and player color
    @Override
    public boolean joinGame(String username, ChessGame.TeamColor playerColor, int gameID) {
        for (var game : gameDatabase) {
            if (game.gameID() == gameID) {
                GameData updatedGame;
                if (playerColor == ChessGame.TeamColor.WHITE) {
                    updatedGame = new GameData(gameID, username, game.blackUsername(), game.gameName(), game.game());
                } else if (playerColor == ChessGame.TeamColor.BLACK) {
                    updatedGame = new GameData(gameID, game.whiteUsername(), username, game.gameName(), game.game());
                } else {
                    return false;
                }
                gameDatabase.remove(game);
                createGame(updatedGame);
                return true;
            }
        }
        return false;
    }

    // Deletes every game in the database
    @Override
    public boolean deleteAllGames() {
        gameDatabase.clear();
        return true;
    }
}
