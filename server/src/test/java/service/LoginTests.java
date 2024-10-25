package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import model.UserData;
import model.requests.LoginRequest;
import model.results.LoginResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTests {
    private final UserService userService = new UserService();
    private final BaseService baseService = new BaseService();

    @BeforeEach
    public void registerUser() throws DataAccessException {
        baseService.clear();

        UserData goodUser = new UserData("Trevor", "mypass", "mymail.com");
        userService.register(goodUser);
    }

    @Test
    @DisplayName("Login - Successful")
    public void loginSuccess() {
        LoginRequest loginRequest = new LoginRequest("Trevor", "mypass");
        LoginResult result = userService.login(loginRequest);

        assertTrue(result.success());
        assertEquals(200, result.statusCode());
        assertEquals("{}", result.errorMessage().message());
        assertNotNull(result.loginResponse());

//        assertTrue(MemoryAuthDAO.authDatabase.contains(result.loginResponse()));
    }

    @Test
    @DisplayName("Login - Missing Username")
    public void loginMissingUsername() {
        LoginRequest loginRequest = new LoginRequest(null, "mypass");
        LoginResult result = userService.login(loginRequest);

        assertFalse(result.success());
        assertEquals(400, result.statusCode());
        assertEquals("Error: missing fields", result.errorMessage().message());
        assertNull(result.loginResponse());
    }

    @Test
    @DisplayName("Login - Unauthorized")
    public void loginUnauthorized() {
        LoginRequest loginRequest = new LoginRequest("Trevor", "wrongpass");
        LoginResult result = userService.login(loginRequest);

        assertFalse(result.success());
        assertEquals(401, result.statusCode());
        assertEquals("Error: unauthorized", result.errorMessage().message());
        assertNull(result.loginResponse());
    }
}