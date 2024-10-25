package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import model.UserData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClearTests {
    private final GameService gameService = new GameService();
    private final UserService userService = new UserService();
    private final BaseService baseService = new BaseService();

    @Test
    @DisplayName("Clear - Successful")
    public void clearSuccess() throws DataAccessException {
        baseService.clear();

        MemoryGameDAO.resetGameIDs();
        UserData goodUser = new UserData("Trevor", "mypass", "mymail.com");
        String trueToken = userService.register(goodUser).registerResponse().authToken();
        gameService.createGame(trueToken, "game1");
        gameService.createGame(trueToken, "game2");

        assertFalse(MemoryUserDAO.userDatabase.isEmpty());
        assertFalse(MemoryAuthDAO.authDatabase.isEmpty());
        assertFalse(MemoryGameDAO.gameDatabase.isEmpty());

        baseService.clear();

        assertTrue(MemoryUserDAO.userDatabase.isEmpty());
        assertTrue(MemoryAuthDAO.authDatabase.isEmpty());
        assertTrue(MemoryGameDAO.gameDatabase.isEmpty());
    }
}
