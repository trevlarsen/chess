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
//    public final static MemoryUserDAO USER_DAO = new MemoryUserDAO();
    public final static MemoryGameDAO GAME_DAO = new MemoryGameDAO();
//    public final static MemoryAuthDAO AUTH_DAO = new MemoryAuthDAO();

    public final static SQLUserDAO USER_DAO;
    //    public final static SQLGameDAO GAME_DAO;
    public final static SQLAuthDAO AUTH_DAO;


    static {
        try {
            USER_DAO = new SQLUserDAO();
//            GAME_DAO = new SQLGameDAO();
            AUTH_DAO = new SQLAuthDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
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
