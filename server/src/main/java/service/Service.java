package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDOA;
import dataaccess.MemoryGameDOA;
import dataaccess.MemoryUserDOA;

public class Service {

    public final static MemoryUserDOA userDataAccess = new MemoryUserDOA();
    public final static MemoryGameDOA gameDataAccess = new MemoryGameDOA();
    public final static MemoryAuthDOA authDataAccess = new MemoryAuthDOA();

    public Service() {
    }

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
