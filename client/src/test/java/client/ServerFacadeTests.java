package client;

import chess.ChessGame;
import model.responses.ErrorResponse;
import org.junit.jupiter.api.*;
import server.Server;
import ui.MenuManager;
import ui.ServerFacade;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ServerFacadeTests {

    private static Server server;
    static ServerFacade facade;

    @BeforeAll
    public static void init() throws IOException {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        var manager = new MenuManager("http://localhost:" + port);
        facade = manager.serverFacade;
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @BeforeEach
    public void clearData() throws IOException {
        facade.clear();
    }

    @Test
    @DisplayName("Register - Success")
    void register() throws Exception {
        var registerResponse = facade.register("player1", "password", "p1@email.com");
        assertTrue(registerResponse.authToken().length() > 10);
    }

    @Test
    @DisplayName("Register - Fail (Duplicate Username)")
    void registerDuplicateUsername() {
        try {
            facade.register("player1", "password", "p1@email.com");
            facade.register("player1", "password", "p1@email.com");
        } catch (IOException e) {
            assertTrue(e.getMessage().contains(ErrorResponse.usernameTaken().message()));
        }
    }

    @Test
    @DisplayName("Login - Success")
    void login() throws Exception {
        facade.register("player1", "password", "p1@email.com");
        var loginResponse = facade.login("player1", "password");
        assertNotNull(loginResponse);
        assertTrue(loginResponse.authToken().length() > 10);
    }

    @Test
    @DisplayName("Login - Fail (Invalid Credentials)")
    void loginInvalidCredentials() {
        try {
            facade.login("invalidUser", "wrongPassword");
        } catch (IOException e) {
            assertTrue(e.getMessage().contains(ErrorResponse.unauthorized().message()));
        }
    }

    @Test
    @DisplayName("Logout - Success")
    void logout() throws Exception {
        var registerResponse = facade.register("player1", "password", "p1@email.com");
        assertDoesNotThrow(() -> facade.logout(registerResponse.authToken()));
    }

    @Test
    @DisplayName("Logout - Fail (Unauthorized)")
    void logoutUnauthorized() {
        try {
            facade.logout("invalidAuthToken");
        } catch (IOException e) {
            assertTrue(e.getMessage().contains(ErrorResponse.unauthorized().message()));
        }
    }

    @Test
    @DisplayName("Create Game - Success")
    void createGame() throws Exception {
        var registerResponse = facade.register("player1", "password", "p1@email.com");
        var gameId = facade.createGame("Chess Game", registerResponse.authToken());
        assertTrue(gameId > 0);
    }

    @Test
    @DisplayName("Create Game - Fail (Unauthorized)")
    void createGameUnauthorized() {
        try {
            facade.createGame("Chess Game", "invalidAuthToken");
        } catch (IOException e) {
            assertTrue(e.getMessage().contains(ErrorResponse.unauthorized().message()));
        }
    }

    @Test
    @DisplayName("Join Game - Success")
    void joinGame() throws Exception {
        var registerResponse = facade.register("player1", "password", "p1@email.com");
        var gameId = facade.createGame("Chess Game", registerResponse.authToken());
        assertDoesNotThrow(() -> facade.joinGame(ChessGame.TeamColor.WHITE, gameId, registerResponse.authToken()));
    }

    @Test
    @DisplayName("Join Game - Fail (Unauthorized)")
    void joinGameUnauthorized() {
        try {
            facade.joinGame(ChessGame.TeamColor.WHITE, 9999, "invalidAuthToken");
        } catch (IOException e) {
            assertTrue(e.getMessage().contains(ErrorResponse.unauthorized().message()));
        }
    }

    @Test
    @DisplayName("List Games - Success")
    void listGames() throws Exception {
        var registerResponse = facade.register("player1", "password", "p1@email.com");
        facade.createGame("Chess Game", registerResponse.authToken());
        var games = facade.listGames(registerResponse.authToken());
        assertNotNull(games);
        assertFalse(games.isEmpty());
    }

    @Test
    @DisplayName("List Games - Fail (Unauthorized)")
    void listGamesUnauthorized() {
        try {
            facade.listGames("invalidAuthToken");
        } catch (IOException e) {
            assertTrue(e.getMessage().contains(ErrorResponse.unauthorized().message()));
        }
    }

    @Test
    @DisplayName("Clear Database - Success")
    void clear() throws Exception {
        var registerResponse = facade.register("player1", "password", "p1@email.com");
        facade.createGame("mygame", registerResponse.authToken());
        facade.clear();
        registerResponse = facade.register("player1", "password", "p1@email.com");
        var games = facade.listGames(registerResponse.authToken());
        assertTrue(games.isEmpty());
    }
}
