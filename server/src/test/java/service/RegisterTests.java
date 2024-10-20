package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryUserDOA;
import model.UserData;
import model.results.RegisterResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.BaseService;
import service.UserService;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterTests {
    private final UserService userService = new UserService();
    private final BaseService baseService = new BaseService();

    @BeforeEach
    public void clear() throws DataAccessException {
        baseService.clear();
    }

    @Test
    @DisplayName("Register - Successful")
    public void registerSuccess() {
        UserData goodUser = new UserData("Trevor", "mypass", "mymail.com");
        RegisterResult result = userService.register(goodUser);

        assertTrue(result.success());
        assertEquals(200, result.statusCode());
        assertEquals("{}", result.errorMessage().message());
        assertNotNull(result.registerResponse());
        assertEquals(goodUser.username(), result.registerResponse().username());

        assertTrue(MemoryUserDOA.userDatabase.contains(goodUser));
    }

    @Test
    @DisplayName("Register - Username Already Taken")
    public void registerUsernameTaken() {
        UserData goodUser = new UserData("Trevor", "mypass", "mymail.com");
        userService.register(goodUser);

        UserData existingUser = new UserData("Trevor", "mypass", "mymail.com");
        RegisterResult result = userService.register(existingUser);

        assertFalse(result.success());
        assertEquals(403, result.statusCode());
        assertEquals("Error: username already taken", result.errorMessage().message());
        assertNull(result.registerResponse());
    }

    @Test
    @DisplayName("Register - Missing Username")
    public void registerMissingUsername() {
        UserData noUsernameUser = new UserData(null, "mypass", "mymail.com");
        RegisterResult result = userService.register(noUsernameUser);

        assertFalse(result.success());
        assertEquals(400, result.statusCode());
        assertEquals("Error: missing fields", result.errorMessage().message());
        assertNull(result.registerResponse());
    }
}
