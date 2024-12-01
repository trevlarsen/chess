package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;

import java.sql.SQLException;
import java.util.ArrayList;

public class SQLGameDAO implements GameDAOInterface {

    private final Gson gson = new Gson(); // Gson instance for serialization/deserialization

    public SQLGameDAO() throws DataAccessException {
        String[] createStatements = {
                """
                CREATE TABLE IF NOT EXISTS games (
                    gameID INT AUTO_INCREMENT,
                    whiteUsername VARCHAR(255),
                    blackUsername VARCHAR(255),
                    gameName VARCHAR(255) NOT NULL,
                    gameState TEXT NOT NULL,
                    PRIMARY KEY (gameID)
                ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
                """
        };
        DatabaseManager.configureDatabase(createStatements);
    }

    @Override
    public GameData newGame(String gameName) {
        ChessGame game = new ChessGame();
        return new GameData(2, null, null, gameName, game); // Return new GameData without an ID
    }

    @Override
    public int addGame(GameData gameData) {
        String sql = "INSERT INTO games (gameName, gameState) VALUES (?, ?)";
        try (var conn = DatabaseManager.getConnection();
             var statement = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, gameData.gameName());
            statement.setString(2, gson.toJson(gameData.game()));  // Serialize game state to JSON
            statement.executeUpdate();
            var keys = statement.getGeneratedKeys();
            if (keys.next()) {
                // Get the generated game ID
                return keys.getInt(1);
            }
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException("Failed to add game: " + e.getMessage(), e);
        }
        return -1;
    }

    @Override
    public GameData getGame(int gameID) {
        String sql = "SELECT * FROM games WHERE gameID = ?";
        try (var conn = DatabaseManager.getConnection();
             var statement = conn.prepareStatement(sql)) {
            statement.setInt(1, gameID);
            try (var rs = statement.executeQuery()) {
                if (rs.next()) {
                    ChessGame game = gson.fromJson(rs.getString("gameState"), ChessGame.class);  // Deserialize JSON to ChessGame
                    return new GameData(
                            rs.getInt("gameID"),
                            rs.getString("whiteUsername"),
                            rs.getString("blackUsername"),
                            rs.getString("gameName"),
                            game
                    );
                }
            }
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException("Failed to retrieve game: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public ArrayList<GameData> getAllGames() {
        ArrayList<GameData> games = new ArrayList<>();
        String sql = "SELECT * FROM games";
        try (var conn = DatabaseManager.getConnection();
             var statement = conn.prepareStatement(sql);
             var rs = statement.executeQuery()) {
            while (rs.next()) {
                ChessGame game = gson.fromJson(rs.getString("gameState"), ChessGame.class);  // Deserialize JSON to ChessGame
                games.add(new GameData(
                        rs.getInt("gameID"),
                        rs.getString("whiteUsername"),
                        rs.getString("blackUsername"),
                        rs.getString("gameName"),
                        game
                ));
            }
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException("Failed to retrieve all games: " + e.getMessage(), e);
        }
        return games;
    }

    @Override
    public boolean joinGame(String username, ChessGame.TeamColor playerColor, int gameID) {
        String sql;
        if (playerColor == ChessGame.TeamColor.WHITE) {
            sql = "UPDATE games SET whiteUsername = ? WHERE gameID = ? AND whiteUsername IS NULL";
        } else {
            sql = "UPDATE games SET blackUsername = ? WHERE gameID = ? AND blackUsername IS NULL";
        }
        try (var conn = DatabaseManager.getConnection();
             var statement = conn.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setInt(2, gameID);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException("Failed to join game: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteAllGames() {
        String sql = "TRUNCATE TABLE games";
        try (var conn = DatabaseManager.getConnection();
             var statement = conn.prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException("Failed to delete all games: " + e.getMessage(), e);
        }
    }

    public void updateGameString(String game, int gameID) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "UPDATE games SET gameState = ? WHERE gameID = ?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, game);
                ps.setInt(2, gameID);
                int rowsAffected = ps.executeUpdate();
            }
        } catch (Exception e) {
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        }
    }

    @Override
    public void deleteUsername(int gameID, String color) throws DataAccessException {
        if (!color.equals("white") && !color.equals("black")) {
            throw new IllegalArgumentException("Color must be 'white' or 'black'.");
        }

        try (var conn = DatabaseManager.getConnection()) {
            var statement = "UPDATE games SET " + color + "Username = ? WHERE gameID = ?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, null);
                ps.setInt(2, gameID);
                ps.executeUpdate();
            }
        } catch (Exception e) {
            throw new DataAccessException(String.format("Unable to update username: %s", e.getMessage()));
        }
    }

}
