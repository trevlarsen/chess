package dataaccess;

import model.AuthData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AuthDAOTest {

    private SQLAuthDAO authDAO;

    @BeforeEach
    void setUp() throws DataAccessException {
        authDAO = new SQLAuthDAO();
        authDAO.deleteAllAuths(); // Ensure a clean state before each test
    }

    @AfterEach
    void tearDown() {
        authDAO.deleteAllAuths(); // Clean up after each test
    }

    @Test
    @DisplayName("New Auth - Success")
    void newAuthSuccess() {
        String username = "testUser";
        AuthData authData = authDAO.newAuth(username);

        assertNotNull(authData);
        assertEquals(username, authData.username());
        assertNotNull(authData.authToken());
    }

    @Test
    @DisplayName("New Auth - Empty Username")
    void newAuthNegativeEmptyUsername() {
        String username = "";
        AuthData authData = authDAO.newAuth(username);

        assertNull(authData);
    }

    @Test
    @DisplayName("Add Auth - Success")
    void addAuthSuccess() {
        String username = "testUser";
        AuthData authData = authDAO.newAuth(username);

        assertDoesNotThrow(() -> authDAO.addAuth(authData), "Adding auth data should not throw an exception");

        // Verify that the auth data can be retrieved
        AuthData retrievedData = authDAO.getAuth(authData.authToken());
        assertNotNull(retrievedData);
        assertEquals(authData.username(), retrievedData.username());
    }

    @Test
    @DisplayName("Add Auth - Invalid Token")
    void addAuthNegativeInvalidToken() {
        AuthData invalidAuthData = new AuthData(null, "testUser");

        assertThrows(RuntimeException.class, () -> authDAO.addAuth(invalidAuthData),
                "Adding invalid auth data should throw an exception");
    }

    @Test
    @DisplayName("Get Auth - Success")
    void getAuthSuccess() {
        String username = "testUser";
        AuthData authData = authDAO.newAuth(username);
        authDAO.addAuth(authData);

        AuthData retrievedData = authDAO.getAuth(authData.authToken());
        assertNotNull(retrievedData);
        assertEquals(username, retrievedData.username());
    }

    @Test
    @DisplayName("Get Auth - Nonexistent Token")
    void getAuthNegativeNonExistentToken() {
        String nonExistentToken = UUID.randomUUID().toString(); // Use a token that doesn't exist
        AuthData retrievedData = authDAO.getAuth(nonExistentToken);
        assertNull(retrievedData, "Retrieving with a non-existent token should return null");
    }

    @Test
    @DisplayName("Delete Auth - Success")
    void deleteAuthSuccess() {
        String username = "testUser";
        AuthData authData = authDAO.newAuth(username);
        authDAO.addAuth(authData);

        authDAO.deleteAuth(authData.authToken());

        AuthData retrievedData = authDAO.getAuth(authData.authToken());
        assertNull(retrievedData, "Auth data should be null after deletion");
    }

    @Test
    @DisplayName("Delete Auth - Nonexistent Token")
    void deleteAuthNegativeNonExistentToken() {
        String nonExistentToken = UUID.randomUUID().toString(); // Use a token that doesn't exist
        assertDoesNotThrow(() -> authDAO.deleteAuth(nonExistentToken),
                "Deleting a non-existent auth token should not throw an exception");
    }

    @Test
    @DisplayName("Delete All Auths - Success")
    void deleteAllAuthsSuccess() {
        authDAO.addAuth(authDAO.newAuth("user1"));
        authDAO.addAuth(authDAO.newAuth("user2"));

        assertDoesNotThrow(() -> authDAO.deleteAllAuths(), "Deleting all auths should not throw an exception");

        // Verify that the auth data is cleared
        AuthData retrievedData1 = authDAO.getAuth("user1"); // Attempt to retrieve an existing user
        AuthData retrievedData2 = authDAO.getAuth("user2"); // Attempt to retrieve an existing user
        assertNull(retrievedData1, "Auth data for user1 should be null after deletion");
        assertNull(retrievedData2, "Auth data for user2 should be null after deletion");
    }
}
