package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.ArrayList;

/**
 * Interface for managing game data access operations.
 * Implementations of this interface are responsible for creating,
 * retrieving, updating, and deleting game records.
 */
public interface GameDAOInterface {

    /**
     * Creates a new game with the given name.
     *
     * @param gameName the name of the new game
     * @return a {@link GameData} object containing the newly created game information
     */
    GameData newGame(String gameName);

    /**
     * Adds the given game data to the data store.
     *
     * @param gameData the {@link GameData} object to be added
     */
    int addGame(GameData gameData);

    /**
     * Retrieves the game associated with the specified game ID.
     *
     * @param gameID the unique identifier of the game to retrieve
     * @return the {@link GameData} if the game is found, or {@code null} if not found
     */
    GameData getGame(int gameID);

    /**
     * Retrieves a list of all games currently stored.
     *
     * @return an {@link ArrayList} of {@link GameData} objects representing all games
     */
    ArrayList<GameData> getAllGames();

    /**
     * Attempts to join a game with the specified player color and game ID.
     *
     * @param username    the username of the player attempting to join
     * @param playerColor the color of the team the player wishes to join, from {@link ChessGame.TeamColor}
     * @param gameID      the ID of the game the player is trying to join
     * @return {@code true} if the player successfully joined, {@code false} if the color is already taken
     */
    boolean joinGame(String username, ChessGame.TeamColor playerColor, int gameID);

    /**
     * Deletes all games from the data store.
     */
    void deleteAllGames();
}
