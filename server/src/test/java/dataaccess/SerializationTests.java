package dataaccess;

import chess.*;
import model.GameData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SerializationTests {

    private SQLGameDAO gameDAO;

    @BeforeEach
    public void setUp() throws DataAccessException {
        gameDAO = new SQLGameDAO();
    }

    @AfterEach
    public void tearDown() {
        gameDAO.deleteAllGames();
    }

    @Test
    public void testChessGameSerialization() {
        // 1. Create a new ChessGame and make a move
        ChessGame game = new ChessGame();
        ChessMove move = new ChessMove(
                new ChessPosition(2, 1),  // Start position (White pawn at a2)
                new ChessPosition(4, 1),   // End position (a4)
                null
        );

        try {
            game.makeMove(move);  // Execute the move (pawn to a4)
        } catch (Exception e) {
            fail("Move Error: " + e.getMessage());
        }

        // 2. Wrap the game in a GameData object and add it to the database
        GameData gameData = new GameData(0, "player1", "player2", "Test Game", game);
        int gameID = gameDAO.addGame(gameData);

        // 3. Retrieve the game from the database
        GameData retrievedGameData = gameDAO.getGame(gameID);

        // 4. Assert the retrieved game is not null
        assertNotNull(retrievedGameData, "Retrieved game data should not be null");

        // 5. Compare the original and retrieved game states
        assertEquals(game, retrievedGameData.game(), "The original and retrieved game states should be identical");

        // 6. Verify the turn is correctly stored and retrieved
        assertEquals(ChessGame.TeamColor.BLACK, retrievedGameData.game().getTeamTurn(),
                "Turn should switch to BLACK after White's move");
    }
}
