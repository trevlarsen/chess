package dataaccess;

import model.GameData;
import model.UserData;
import model.results.ListGamesResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.BaseService;
import service.GameService;
import service.UserService;

import static org.junit.jupiter.api.Assertions.*;

public class ListGamesTests {
    private final GameService gameService = new GameService();
    private final UserService userService = new UserService();
    private final BaseService baseService = new BaseService();

    private String trueToken;
    private final String firstGame = "game1";
    private final String secondGame = "game2";

    @BeforeEach
    public void registerUser() throws DataAccessException {
        baseService.clear();

        MemoryGameDAO.resetGameIDs();
        UserData goodUser = new UserData("Trevor", "mypass", "mymail.com");
        trueToken = userService.register(goodUser).registerResponse().authToken();

        gameService.createGame(trueToken, firstGame);
        gameService.createGame(trueToken, secondGame);
    }

    @Test
    @DisplayName("List Games - Successful")
    public void listGamesSuccess() {
        ListGamesResult result = gameService.listGames(trueToken);

        assertTrue(result.success());
        assertEquals(200, result.statusCode());
        assertEquals("{}", result.errorMessage().message());
        assertNotNull(result.listGamesResponse());

        var current = firstGame;
        for (GameData game : result.listGamesResponse().games()) {
            assertEquals(current, game.gameName());
            current = secondGame;
        }
    }

    @Test
    @DisplayName("List Games - Missing Auth Token")
    public void listGamesMissingAuthToken() {
        ListGamesResult result = gameService.listGames(null);

        assertFalse(result.success());
        assertEquals(400, result.statusCode());
        assertEquals("Error: missing fields", result.errorMessage().message());
        assertNull(result.listGamesResponse());
    }

    @Test
    @DisplayName("List Games - Unauthorized")
    public void listGamesUnauthorized() {
        ListGamesResult result = gameService.listGames("invalid-token");

        assertFalse(result.success());
        assertEquals(401, result.statusCode());
        assertEquals("Error: unauthorized", result.errorMessage().message());
        assertNull(result.listGamesResponse());
    }
}