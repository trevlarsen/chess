package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.ArrayList;

public class MemoryGameDOA implements GameDOAInterface{

    private static ArrayList<GameData> gameDatabase = new ArrayList<>();


    public MemoryGameDOA() {}


    // Adds a game to the database
    @Override
    public void createGame(GameData gameData) {

    }

    // Find and return a game based on a gameID
    @Override
    public ChessGame getGame(String GameID) {
        return null;
    }

    // Returns a list of all games in the database
    @Override
    public ArrayList<ChessGame> getAllGames() {
        return null;
    }

    // Add a user by username to a game using the gameID and player color
    @Override
    public boolean joinGame(String username, ChessGame.TeamColor playerColor, String gameID) {
        return false;
    }

    // Deletes every game in the database
    @Override
    public boolean deleteAllGames() {
        return false;
    }
}
