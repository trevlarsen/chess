package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.ArrayList;

public class MemoryGameDOA implements GameDOAInterface {

    public static ArrayList<GameData> gameDatabase = new ArrayList<>();
    private static int nextGameID = 1;

    public MemoryGameDOA() {
    }

    @Override
    public GameData newGame(String gameName) {
        return new GameData(nextGameID++, null, null, gameName, new ChessGame());
    }

    @Override
    public void addGame(GameData gameData) {
        gameDatabase.add(gameData);
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

    public static void resetGameIDs() {
        nextGameID = 1;
    }
}
