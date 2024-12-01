package service;

import chess.ChessGame;
import dataaccess.DataAccessException;
import model.GameData;
import org.mindrot.jbcrypt.BCrypt;

import java.lang.reflect.Field;
import java.util.Objects;

import static service.BaseService.*;

/**
 * A utility service responsible for validating inputs and checking authorization.
 */
public class ValidationService {

    /**
     * Validates the provided input objects by checking for null values
     * and empty strings in their fields. This method uses reflection
     * to access the fields of each input object.
     *
     * @param inputs Varargs of objects to be validated.
     * @return {@code true} if all input objects and their fields are valid (not
     * null and not empty); {@code false} if any input object is null,
     * or if any field in the objects is null or an empty string.
     */
    public static boolean inputIsInvalid(Object... inputs) throws IllegalAccessException {
        for (Object input : inputs) {
            if (input == null) {
                return true;
            }

            if (input instanceof String) {
                if (((String) input).isEmpty()) {
                    return true;
                }
                continue;
            }

            // Check for fields in the object
            for (Field field : input.getClass().getDeclaredFields()) {
                field.setAccessible(true);

                Object fieldValue = field.get(input);
                if (fieldValue == null) {
                    return true;
                }
                // Additional check for empty Strings
                if (fieldValue instanceof String && ((String) fieldValue).isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if the provided authentication token is valid.
     *
     * @param authToken The authentication token to validate.
     * @return {@code true} if the token is unauthorized (not found), {@code false} otherwise.
     */
    public static boolean isUnauthorized(String authToken) {
        return AUTH_DAO.getAuth(authToken) == null;
    }

    public static boolean passwordsMatch(String inputtedPassword, String storedEncryptedPassword) {
        if (usingSql) {
            return BCrypt.checkpw(inputtedPassword, storedEncryptedPassword);
        }
        return Objects.equals(inputtedPassword, storedEncryptedPassword);
    }

    public String getUsername(String authToken) {
        var authData = AUTH_DAO.getAuth(authToken);
        return authData.username();
    }

    public GameData getGame(int gameID) {
        return GAME_DAO.getGame(gameID);
    }

    public ChessGame.TeamColor checkUserColor(String username, GameData gameData) throws DataAccessException {
        if (Objects.equals(gameData.whiteUsername(), username)) {
            return ChessGame.TeamColor.WHITE;
        } else if (Objects.equals(gameData.blackUsername(), username)) {
            return ChessGame.TeamColor.BLACK;
        }
        throw new DataAccessException("Observers cannot resign the game.");
    }


    public void setGame(String game, int gameID) throws DataAccessException {
        GAME_DAO.updateGameString(game, gameID);
    }

    public void setGameResign(int gameID, String username) throws DataAccessException {
        GameData gameData = GAME_DAO.getGame(gameID);
        ChessGame game = gameData.game();

        checkUserColor(username, gameData);

        if (checkIsResigned(gameID)) {
            throw new DataAccessException("Game is already resigned.");
        }

        game.setResigned(true);
        GAME_DAO.updateGameString(game.toString(), gameID);
    }


    public boolean checkIsResigned(int gameID) {
        GameData gameData = GAME_DAO.getGame(gameID);
        ChessGame game = gameData.game();
        return game.isResigned();
    }

    public void setGameUsername(int gameID, String username) throws DataAccessException {
        GameData gameData = getGame(gameID);

        if (Objects.equals(username, gameData.whiteUsername())) {
            GAME_DAO.deleteUsername(gameID, "white");
        } else if (Objects.equals(username, gameData.blackUsername())) {
            GAME_DAO.deleteUsername(gameID, "black");
        }
    }
}
