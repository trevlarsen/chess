package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDOA;
import dataaccess.MemoryGameDOA;
import dataaccess.MemoryUserDOA;

public class Service {

    final static MemoryUserDOA userDataAccess = new MemoryUserDOA();
    final static MemoryGameDOA gameDataAccess = new MemoryGameDOA();
    final static MemoryAuthDOA authDataAccess = new MemoryAuthDOA();

    public Service() throws DataAccessException {}

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
