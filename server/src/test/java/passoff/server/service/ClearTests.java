package passoff.server.service;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDOA;
import dataaccess.MemoryGameDOA;
import dataaccess.MemoryUserDOA;
import model.UserData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.GameService;
import service.BaseService;
import service.UserService;

import static org.junit.jupiter.api.Assertions.*;

public class ClearTests {
    private final GameService gameService = new GameService();
    private final UserService userService = new UserService();
    private final BaseService baseService = new BaseService();

    @Test
    @DisplayName("Clear - Successful")
    public void clearSuccess() throws DataAccessException {
        baseService.clear();

        MemoryGameDOA.resetGameIDs();
        UserData goodUser = new UserData("Trevor", "mypass", "mymail.com");
        String trueToken = userService.register(goodUser).registerResponse().authToken();
        gameService.createGame(trueToken, "game1");
        gameService.createGame(trueToken, "game2");

        assertFalse(MemoryUserDOA.userDatabase.isEmpty());
        assertFalse(MemoryAuthDOA.authDatabase.isEmpty());
        assertFalse(MemoryGameDOA.gameDatabase.isEmpty());

        baseService.clear();

        assertTrue(MemoryUserDOA.userDatabase.isEmpty());
        assertTrue(MemoryAuthDOA.authDatabase.isEmpty());
        assertTrue(MemoryGameDOA.gameDatabase.isEmpty());
    }
}
