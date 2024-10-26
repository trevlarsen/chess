package dataaccess;

import model.UserData;

import java.sql.SQLException;

public class SQLUserDAO implements UserDAOInterface {

    public SQLUserDAO() throws DataAccessException {
        String[] createStatements = {
                """
                CREATE TABLE IF NOT EXISTS users (
                    username VARCHAR(255) NOT NULL UNIQUE,
                    password VARCHAR(255) NOT NULL,
                    email VARCHAR(255) NOT NULL UNIQUE,
                    PRIMARY KEY (username)
                ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
                """
        };
        DatabaseManager.configureDatabase(createStatements);
    }

    @Override
    public void createUser(UserData userData) {
        String sql = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
        try (var conn = DatabaseManager.getConnection();
             var statement = conn.prepareStatement(sql)) {
            statement.setString(1, userData.username());
            statement.setString(2, DatabaseManager.encryptPassword(userData.password()));
            statement.setString(3, userData.email());
            statement.executeUpdate();
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException("Failed to create user: " + e.getMessage(), e);
        }
    }

    @Override
    public UserData getUser(String username) {
        String sql = "SELECT username, password, email FROM users WHERE username = ?";
        try (var conn = DatabaseManager.getConnection();
             var statement = conn.prepareStatement(sql)) {
            statement.setString(1, username);
            try (var rs = statement.executeQuery()) {
                if (rs.next()) {
                    String user = rs.getString("username");
                    String pass = rs.getString("password");
                    String email = rs.getString("email");
                    return new UserData(user, pass, email);
                }
            }
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException("Failed to retrieve user: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void deleteAllUsers() {
        String sql = "DELETE FROM users";
        try (var conn = DatabaseManager.getConnection();
             var statement = conn.prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException("Failed to delete all users: " + e.getMessage(), e);
        }
    }
}
