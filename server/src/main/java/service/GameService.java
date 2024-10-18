package service;

import dataaccess.DataAccessException;
import model.GameData;

import java.util.ArrayList;

public class GameService {

    public GameService() {}


    public int createGame() throws DataAccessException {
        try {

        } catch (Exception e) {
            throw new DataAccessException("Error:" + e.getMessage());
        }
    }

    public void joinGame() throws DataAccessException {
        try {

        } catch (Exception e) {
            throw new DataAccessException("Error:" + e.getMessage());
        }
    }

    public ArrayList<GameData> listGames() throws DataAccessException {
        try {

        } catch (Exception e) {
            throw new DataAccessException("Error:" + e.getMessage());
        }
    }
}
