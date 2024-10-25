package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;

/**
 * Provides shared data access resources and utility functions
 * for other services to interact with in-memory storage.
 */
public class BaseService {

    /**
     * Shared data access objects
     */
    public final static MemoryUserDAO USER_DAO = new MemoryUserDAO();
    public final static MemoryGameDAO GAME_DAO = new MemoryGameDAO();
    public final static MemoryAuthDAO AUTH_DAO = new MemoryAuthDAO();

    public BaseService() {
    }

    /**
     * Clears all user, game, and authentication data from the system.
     *
     * @throws DataAccessException if an error occurs during data deletion.
     */
    public void clear() throws DataAccessException {
        try {
            USER_DAO.deleteAllUsers();
            GAME_DAO.deleteAllGames();
            AUTH_DAO.deleteAllAuths();
        } catch (Exception e) {
            throw new DataAccessException("Error: description");
        }
    }
}
