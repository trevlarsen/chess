package service;

import dataaccess.DataAccessException;
import model.GameData;

import java.util.ArrayList;

public class GameService {

    public GameService() {}


    public int createGame() throws DataAccessException {
        try {
            return 1;
        } catch (Exception e) {
            throw new DataAccessException("Error:" + e.getMessage());
        }
    }

    public void joinGame() throws DataAccessException {
        try {
            var i = 9;
        } catch (Exception e) {
            throw new DataAccessException("Error:" + e.getMessage());
        }
    }

    public ArrayList<GameData> listGames() throws DataAccessException {
        try {
            return new ArrayList<>();
        } catch (Exception e) {
            throw new DataAccessException("Error:" + e.getMessage());
        }
    }
}
