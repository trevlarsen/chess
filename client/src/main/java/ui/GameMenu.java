package ui;

import chess.ChessGame;

import static ui.MenuManager.*;

public class GameMenu {

    private final int numGameOptions = 2;
    private final ChessGame currentGame; // Injecting ChessGame
    private final ChessBoardPrinter boardPrinter;

    private final String gameMenu = """
            \nGame functionality coming soon!
            \t1. Go back
            \t2. Quit
            """;

    // Constructor to inject dependencies (currentGame and boardPrinter)
    public GameMenu(ChessGame currentGame) {
        this.currentGame = currentGame;
        this.boardPrinter = new ChessBoardPrinter(currentGame); // Create the board printer
    }

    public MenuState run() {
        MenuState result = MenuState.GAME;
        boardPrinter.tempPrint(); // Using the injected boardPrinter instance
        printGameMenu();
        int input = getValidOption(2);

        switch (input) {
            case 1 -> result = MenuState.POSTLOGIN;
            case 2 -> {
                printResult("Goodbye.");
                System.exit(0);
            }
            default -> System.out.println("Invalid option. Please try again.");
        }
        return result;
    }

    private void printGameMenu() {
        System.out.println(gameMenu);
    }
}
