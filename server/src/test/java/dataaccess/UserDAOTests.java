package dataaccess;

import model.UserData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTests {

    private SQLUserDAO userDAO;

    @BeforeEach
    void setUp() throws DataAccessException {
        userDAO = new SQLUserDAO();
        userDAO.deleteAllUsers();
    }

    @AfterEach
    void tearDown() {
        userDAO.deleteAllUsers();
    }

    @Test
    @DisplayName("Create User - Success")
    void createUserSuccess() {
        UserData userData = new UserData("testUser", "password123", "test@example.com");

        assertDoesNotThrow(() -> userDAO.createUser(userData),
                "Creating a user should not throw an exception");

        UserData retrievedUser = userDAO.getUser("testUser");
        assertNotNull(retrievedUser);
        assertEquals(userData.username(), retrievedUser.username());
        assertEquals(userData.email(), retrievedUser.email());
    }

    @Test
    @DisplayName("Create User - Duplicate Username")
    void createUserNegativeDuplicateUsername() {
        UserData userData1 = new UserData("testUser", "password123", "test1@example.com");
        UserData userData2 = new UserData("testUser", "anotherPass", "test2@example.com");

        userDAO.createUser(userData1);

        assertThrows(RuntimeException.class,
                () -> userDAO.createUser(userData2),
                "Creating a user with a duplicate username should throw an exception");
    }

    @Test
    @DisplayName("Create User - Null Username")
    void createUserNegativeNullUsername() {
        UserData invalidUser = new UserData(null, "password123", "test@example.com");

        assertThrows(RuntimeException.class,
                () -> userDAO.createUser(invalidUser),
                "Creating a user with a null username should throw an exception");
    }

    @Test
    @DisplayName("Get User - Success")
    void getUserSuccess() {
        UserData userData = new UserData("testUser", "password123", "test@example.com");
        userDAO.createUser(userData);

        UserData retrievedUser = userDAO.getUser("testUser");
        assertNotNull(retrievedUser);
        assertEquals(userData.username(), retrievedUser.username());
        assertEquals(userData.email(), retrievedUser.email());
    }

    @Test
    @DisplayName("Get User - Nonexistent Username")
    void getUserNegativeNonExistentUsername() {
        UserData retrievedUser = userDAO.getUser("nonExistentUser");
        assertNull(retrievedUser, "Retrieving a non-existent user should return null");
    }

    @Test
    @DisplayName("Delete All Users - Success")
    void deleteAllUsersSuccess() {
        userDAO.createUser(new UserData("user1", "password1", "user1@example.com"));
        userDAO.createUser(new UserData("user2", "password2", "user2@example.com"));

        assertDoesNotThrow(() -> userDAO.deleteAllUsers(),
                "Deleting all users should not throw an exception");

        assertNull(userDAO.getUser("user1"), "User1 should be null after deletion");
        assertNull(userDAO.getUser("user2"), "User2 should be null after deletion");
    }
}
