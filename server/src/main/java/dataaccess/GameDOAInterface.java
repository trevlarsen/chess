package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.ArrayList;

public interface GameDOAInterface {
    void createGame(GameData gameData); // Adds a game to the database

    ChessGame getGame(String GameID); // Find and return a game based on a gameID
    ArrayList<ChessGame> getAllGames(); // Returns a list of all games in the database

    boolean joinGame(String username, ChessGame.TeamColor playerColor, String gameID); // Add a user by username to a game using the gameID and player color

    boolean deleteAllGames(); // Deletes every game in the database
}
