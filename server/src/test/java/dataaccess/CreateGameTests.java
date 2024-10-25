package dataaccess;

import chess.ChessGame;
import model.GameData;
import model.UserData;
import model.results.CreateGameResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.BaseService;
import service.GameService;
import service.UserService;

import static org.junit.jupiter.api.Assertions.*;

public class CreateGameTests {
    private final GameService gameService = new GameService();
    private final UserService userService = new UserService();
    private final BaseService baseService = new BaseService();

    private String trueToken;

    @BeforeEach
    public void registerUser() throws DataAccessException {
        baseService.clear();

        MemoryGameDOA.resetGameIDs();
        UserData goodUser = new UserData("Trevor", "mypass", "mymail.com");
        trueToken = userService.register(goodUser).registerResponse().authToken();
    }

    @Test
    @DisplayName("Create Game - Successful")
    public void createGameSuccess() {
        String gameName = "Chess";
        CreateGameResult result = gameService.createGame(trueToken, gameName);

        assertTrue(result.success());
        assertEquals(200, result.statusCode());
        assertEquals("{}", result.errorMessage().message());
        assertNotNull(result.createGameResponse());
        assertEquals(1, result.createGameResponse().gameID());

        assertTrue(MemoryGameDOA.gameDatabase.contains(new GameData(1, null, null, gameName, new ChessGame())));
    }

    @Test
    @DisplayName("Create Game - Missing Auth Token")
    public void createGameMissingAuthToken() {
        String gameName = "Chess";
        CreateGameResult result = gameService.createGame(null, gameName);

        assertFalse(result.success());
        assertEquals(400, result.statusCode());
        assertEquals("Error: missing fields", result.errorMessage().message());
        assertNull(result.createGameResponse());
    }

    @Test
    @DisplayName("Create Game - Unauthorized")
    public void createGameUnauthorized() {
        String authToken = "invalid-token";
        String gameName = "Chess";
        CreateGameResult result = gameService.createGame(authToken, gameName);

        assertFalse(result.success());
        assertEquals(401, result.statusCode());
        assertEquals("Error: unauthorized", result.errorMessage().message());
        assertNull(result.createGameResponse());
    }
}