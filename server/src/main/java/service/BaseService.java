package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDOA;
import dataaccess.MemoryGameDOA;
import dataaccess.MemoryUserDOA;

/**
 * Provides shared data access resources and utility functions
 * for other services to interact with in-memory storage.
 */
public class BaseService {

    /**
     * Shared data access objects
     */
    public final static MemoryUserDOA USER_DOA = new MemoryUserDOA();
    public final static MemoryGameDOA GAME_DOA = new MemoryGameDOA();
    public final static MemoryAuthDOA AUTH_DOA = new MemoryAuthDOA();

    public BaseService() {
    }

    /**
     * Clears all user, game, and authentication data from the system.
     *
     * @throws DataAccessException if an error occurs during data deletion.
     */
    public void clear() throws DataAccessException {
        try {
            USER_DOA.deleteAllUsers();
            GAME_DOA.deleteAllGames();
            AUTH_DOA.deleteAllAuths();
        } catch (Exception e) {
            throw new DataAccessException("Error: description");
        }
    }
}
