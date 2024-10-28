package dataaccess;

import chess.ChessGame;
import model.GameData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameDAOTests {

    private SQLGameDAO gameDAO;

    @BeforeEach
    void setUp() throws DataAccessException {
        gameDAO = new SQLGameDAO();
        gameDAO.deleteAllGames();
    }

    @AfterEach
    void tearDown() {
        gameDAO.deleteAllGames();
    }

    @Test
    @DisplayName("New Game - Success")
    void newGameSuccess() {
        GameData gameData = gameDAO.newGame("Test Game");

        assertNotNull(gameData);
        assertEquals("Test Game", gameData.gameName());
        assertNotNull(gameData.game());
    }

    @Test
    @DisplayName("Add Game - Success")
    void addGameSuccess() {
        GameData gameData = gameDAO.newGame("Test Game");
        int gameID = gameDAO.addGame(gameData);

        assertTrue(gameID > 0, "Game ID should be positive after successful insertion");

        GameData retrievedGame = gameDAO.getGame(gameID);
        assertNotNull(retrievedGame);
        assertEquals(gameData.gameName(), retrievedGame.gameName());
    }

    @Test
    @DisplayName("Add Game - Null Game Name")
    void addGameNegativeNullGameName() {
        GameData invalidGame = new GameData(0, null, null, null, new ChessGame());

        assertThrows(RuntimeException.class,
                () -> gameDAO.addGame(invalidGame),
                "Adding a game with a null name should throw an exception");
    }

    @Test
    @DisplayName("Get Game - Success")
    void getGameSuccess() {
        GameData gameData = gameDAO.newGame("Test Game");
        int gameID = gameDAO.addGame(gameData);

        GameData retrievedGame = gameDAO.getGame(gameID);

        assertNotNull(retrievedGame);
        assertEquals(gameData.gameName(), retrievedGame.gameName());
    }

    @Test
    @DisplayName("Get Game - Nonexistent ID")
    void getGameNegativeNonExistentID() {
        GameData retrievedGame = gameDAO.getGame(-1);

        assertNull(retrievedGame, "Retrieving a non-existent game should return null");
    }

    @Test
    @DisplayName("Get All Games - Success")
    void getAllGamesSuccess() {
        gameDAO.addGame(gameDAO.newGame("Game 1"));
        gameDAO.addGame(gameDAO.newGame("Game 2"));

        ArrayList<GameData> allGames = gameDAO.getAllGames();

        assertEquals(2, allGames.size(), "There should be 2 games after adding them");
    }

    @Test
    @DisplayName("Join Game - Success")
    void joinGameSuccess() {
        int gameID = gameDAO.addGame(gameDAO.newGame("Test Game"));

        boolean joined = gameDAO.joinGame("user1", ChessGame.TeamColor.WHITE, gameID);

        assertTrue(joined, "User should successfully join as White");

        GameData game = gameDAO.getGame(gameID);
        assertEquals("user1", game.whiteUsername());
    }

    @Test
    @DisplayName("Join Game - Already Occupied Position")
    void joinGameNegativeOccupiedPosition() {
        int gameID = gameDAO.addGame(gameDAO.newGame("Test Game"));
        gameDAO.joinGame("user1", ChessGame.TeamColor.WHITE, gameID);

        boolean joinedAgain = gameDAO.joinGame("user2", ChessGame.TeamColor.WHITE, gameID);

        assertFalse(joinedAgain, "User should not be able to join an already occupied position");
    }

    @Test
    @DisplayName("Delete All Games - Success")
    void deleteAllGamesSuccess() {
        gameDAO.addGame(gameDAO.newGame("Game 1"));
        gameDAO.addGame(gameDAO.newGame("Game 2"));

        assertDoesNotThrow(() -> gameDAO.deleteAllGames(),
                "Deleting all games should not throw an exception");

        ArrayList<GameData> allGames = gameDAO.getAllGames();
        assertTrue(allGames.isEmpty(), "All games should be deleted");
    }
}
