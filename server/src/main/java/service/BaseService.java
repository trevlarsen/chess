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
    public final static MemoryUserDOA userDataAccess = new MemoryUserDOA();
    public final static MemoryGameDOA gameDataAccess = new MemoryGameDOA();
    public final static MemoryAuthDOA authDataAccess = new MemoryAuthDOA();

    public BaseService() {
    }

    /**
     * Clears all user, game, and authentication data from the system.
     *
     * @throws DataAccessException if an error occurs during data deletion.
     */
    public void clear() throws DataAccessException {
        try {
            userDataAccess.deleteAllUsers();
            gameDataAccess.deleteAllGames();
            authDataAccess.deleteAllAuths();
        } catch (Exception e) {
            throw new DataAccessException("Error: description");
        }
    }
}
