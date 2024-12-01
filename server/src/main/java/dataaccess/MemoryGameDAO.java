package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.ArrayList;

public class MemoryGameDAO implements GameDAOInterface {

    public static ArrayList<GameData> gameDatabase = new ArrayList<>();
    private static int nextGameID = 1;

    public MemoryGameDAO() {
    }

    @Override
    public GameData newGame(String gameName) {
        return new GameData(nextGameID++, null, null, gameName, new ChessGame());
    }

    @Override
    public int addGame(GameData gameData) {
        gameDatabase.add(gameData);
        return gameData.gameID();
    }

    @Override
    public GameData getGame(int gameID) {
        for (var game : gameDatabase) {
            if (game.gameID() == gameID) {
                return game;
            }
        }
        return null;
    }

    @Override
    public ArrayList<GameData> getAllGames() {
        return gameDatabase;
    }

    @Override
    public boolean joinGame(String username, ChessGame.TeamColor playerColor, int gameID) {
        for (var game : gameDatabase) {
            if (game.gameID() == gameID) {
                GameData updatedGame;
                // Only adds the player if the color is available
                if (playerColor == ChessGame.TeamColor.WHITE && game.whiteUsername() == null) {
                    updatedGame = new GameData(gameID, username, game.blackUsername(), game.gameName(), game.game());
                } else if (playerColor == ChessGame.TeamColor.BLACK && game.blackUsername() == null) {
                    updatedGame = new GameData(gameID, game.whiteUsername(), username, game.gameName(), game.game());
                } else {
                    break;
                }
                gameDatabase.remove(game);
                addGame(updatedGame);
                return true;
            }
        }
        return false;
    }

    @Override
    public void deleteAllGames() {
        gameDatabase.clear();
    }

    @Override
    public void updateGameString(String game, int gameID) throws DataAccessException {

    }

    @Override
    public void deleteUsername(int gameID, String color) throws DataAccessException {

    }

    public static void resetGameIDs() {
        nextGameID = 1;
    }
}
