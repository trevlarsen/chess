package service;

import dataaccess.*;

/**
 * Provides shared data access resources and utility functions
 * for other services to interact with in-memory storage.
 */
public class BaseService {

    /**
     * Shared data access objects
     */
    public final static UserDAOInterface USER_DAO;
    public final static GameDAOInterface GAME_DAO;
    public final static AuthDAOInterface AUTH_DAO;
    public static boolean usingSql = true;  // Set this to true to use SQL DAOs

    static {
        if (usingSql) {
            try {
                USER_DAO = new SQLUserDAO();
                GAME_DAO = new SQLGameDAO();
                AUTH_DAO = new SQLAuthDAO();
            } catch (DataAccessException e) {
                throw new RuntimeException("Failed to initialize SQL DAOs: " + e.getMessage(), e);
            }
        } else {
            // Use Memory DAOs
            USER_DAO = new MemoryUserDAO();
            GAME_DAO = new MemoryGameDAO();
            AUTH_DAO = new MemoryAuthDAO();
        }
    }

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
