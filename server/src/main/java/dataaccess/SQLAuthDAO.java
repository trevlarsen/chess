package dataaccess;

import model.AuthData;
import service.ValidationService;

import java.util.UUID;

public class SQLAuthDAO implements AuthDAOInterface {

    public SQLAuthDAO() throws DataAccessException {
        String[] createStatements = {
                """
                CREATE TABLE IF NOT EXISTS auth (
                    username VARCHAR(255) NOT NULL,
                    authToken VARCHAR(255) NOT NULL UNIQUE,
                    PRIMARY KEY (authToken)
                    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
                """
        };
        DatabaseManager.configureDatabase(createStatements);
    }

    @Override
    public AuthData newAuth(String username) {
        if (username == null || username.isBlank()) {
            return null;
        }

        String authToken = UUID.randomUUID().toString();
        return new AuthData(authToken, username);
    }

    @Override
    public void addAuth(AuthData authData) {
        String sql = "INSERT INTO auth (username, authToken) VALUES (?, ?)";
        try (var conn = DatabaseManager.getConnection();
             var statement = conn.prepareStatement(sql)) {
            if (ValidationService.inputIsInvalid(authData)) {
                throw new Exception("Input is invalid");
            }
            statement.setString(1, authData.username());
            statement.setString(2, authData.authToken());
            statement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Failed to add auth data: " + e.getMessage(), e);
        }
    }

    @Override
    public AuthData getAuth(String authToken) {
        String sql = "SELECT username FROM auth WHERE authToken = ?";
        try (var conn = DatabaseManager.getConnection();
             var statement = conn.prepareStatement(sql)) {
            if (ValidationService.inputIsInvalid(authToken)) {
                throw new Exception("Input is invalid");
            }
            statement.setString(1, authToken);
            try (var rs = statement.executeQuery()) {
                if (rs.next()) {
                    String username = rs.getString("username");
                    return new AuthData(authToken, username);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve auth data: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void deleteAuth(String authToken) {
        String sql = "DELETE FROM auth WHERE authToken = ?";
        try (var conn = DatabaseManager.getConnection();
             var statement = conn.prepareStatement(sql)) {
            if (ValidationService.inputIsInvalid(authToken)) {
                throw new Exception("Input is invalid");
            }
            statement.setString(1, authToken);
            statement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete auth data: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteAllAuths() {
        String sql = "DELETE FROM auth";
        try (var conn = DatabaseManager.getConnection();
             var statement = conn.prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete all auth data: " + e.getMessage(), e);
        }
    }
}
