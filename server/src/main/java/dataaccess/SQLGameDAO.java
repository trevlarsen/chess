package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.ArrayList;

public class SQLGameDAO implements GameDAOInterface {
    @Override
    public GameData newGame(String gameName) {
        return null;
    }

    @Override
    public void addGame(GameData gameData) {

    }

    @Override
    public GameData getGame(int gameID) {
        return null;
    }

    @Override
    public ArrayList<GameData> getAllGames() {
        return null;
    }

    @Override
    public boolean joinGame(String username, ChessGame.TeamColor playerColor, int gameID) {
        return false;
    }

    @Override
    public void deleteAllGames() {

    }
}
