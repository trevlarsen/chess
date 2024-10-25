package service;

import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.MemoryGameDAO;
import model.GameData;
import model.UserData;
import model.requests.JoinGameRequest;
import model.results.JoinGameResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JoinGameTests {
    private final GameService gameService = new GameService();
    private final UserService userService = new UserService();
    private final BaseService baseService = new BaseService();

    private String trueToken;
    private int trueGameID;

    @BeforeEach
    public void registerUser() throws DataAccessException {
        baseService.clear();

        MemoryGameDAO.resetGameIDs();
        UserData goodUser = new UserData("Trevor", "mypass", "mymail.com");
        trueToken = userService.register(goodUser).registerResponse().authToken();

        String gameName = "Chess";
        trueGameID = gameService.createGame(trueToken, gameName).createGameResponse().gameID();
    }

    @Test
    @DisplayName("Join Game - Successful")
    public void joinGameSuccess() {
        JoinGameRequest joinGameRequest = new JoinGameRequest(ChessGame.TeamColor.WHITE, trueGameID);
        JoinGameResult result = gameService.joinGame(trueToken, joinGameRequest);

        assertTrue(result.success());
        assertEquals(200, result.statusCode());
        assertEquals("{}", result.errorMessage().message());

//        assertTrue(MemoryGameDAO.gameDatabase.contains(new GameData(trueGameID, "Trevor", null, "Chess", new ChessGame())));
    }

    @Test
    @DisplayName("Join Game - Missing Join Game Request")
    public void joinGameMissingRequest() {
        JoinGameResult result = gameService.joinGame(trueToken, null);

        assertFalse(result.success());
        assertEquals(400, result.statusCode());
        assertEquals("Error: missing fields", result.errorMessage().message());
    }

    @Test
    @DisplayName("Join Game - Unauthorized")
    public void joinGameUnauthorized() {
        JoinGameRequest joinGameRequest = new JoinGameRequest(ChessGame.TeamColor.WHITE, trueGameID); // Update with valid parameters
        JoinGameResult result = gameService.joinGame("invalid-token", joinGameRequest);

        assertFalse(result.success());
        assertEquals(401, result.statusCode());
        assertEquals("Error: unauthorized", result.errorMessage().message());
    }

    @Test
    @DisplayName("Join Game - Game Not Found")
    public void joinGameGameNotFound() {
        JoinGameRequest joinGameRequest = new JoinGameRequest(ChessGame.TeamColor.WHITE, 999); // Non-existing game ID
        JoinGameResult result = gameService.joinGame(trueToken, joinGameRequest);

        assertFalse(result.success());
        assertEquals(400, result.statusCode());
        assertEquals("Error: game not found", result.errorMessage().message());
    }

    @Test
    @DisplayName("Join Game - Player Color Already Taken")
    public void joinGamePlayerColorTaken() {
        UserData otherUser = new UserData("Josh", "otherpass", "othermail.com");
        String otherToken = userService.register(otherUser).registerResponse().authToken();
        JoinGameRequest joinGameRequest = new JoinGameRequest(ChessGame.TeamColor.WHITE, trueGameID); // Update with valid parameters
        gameService.joinGame(otherToken, joinGameRequest);

        joinGameRequest = new JoinGameRequest(ChessGame.TeamColor.WHITE, trueGameID); // Update with valid parameters
        JoinGameResult result = gameService.joinGame(trueToken, joinGameRequest);

        assertFalse(result.success());
        assertEquals(403, result.statusCode());
        assertEquals("Error: player color already taken", result.errorMessage().message());
    }
}