package passoff.server.service;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDOA;
import model.AuthData;
import model.UserData;
import model.results.LogoutResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.Service;
import service.UserService;

import static org.junit.jupiter.api.Assertions.*;

public class LogoutTests {
    private final UserService userService = new UserService();
    private final Service service = new Service();
    private String trueToken;

    @BeforeEach
    public void registerUser() throws DataAccessException {
        service.clear();

        UserData goodUser = new UserData("Trevor", "mypass", "mymail.com");
        trueToken = userService.register(goodUser).registerResponse().authToken();
    }

    @Test
    @DisplayName("Logout - Successful")
    public void logoutSuccess() {
        LogoutResult result = userService.logout(trueToken);

        assertTrue(result.success());
        assertEquals(200, result.statusCode());
        assertEquals("{}", result.errorMessage().message());

        assertFalse(MemoryAuthDOA.authDatabase.contains(new AuthData(trueToken, "Trevor")));
    }

    @Test
    @DisplayName("Logout - Unauthorized")
    public void logoutUnauthorized() {
        String authToken = "invalid-token";
        LogoutResult result = userService.logout(authToken);

        assertFalse(result.success());
        assertEquals(401, result.statusCode());
        assertEquals("Error: unauthorized", result.errorMessage().message());
    }

    @Test
    @DisplayName("Logout - Missing Auth Token")
    public void logoutMissingAuthToken() {
        LogoutResult result = userService.logout(null);

        assertFalse(result.success());
        assertEquals(401, result.statusCode());
        assertEquals("Error: unauthorized", result.errorMessage().message());
    }
}