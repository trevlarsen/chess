package ui;

import chess.ChessMove;
import chess.ChessPosition;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import static ui.MenuManager.*;

public class GameMenu {

    private final MenuManager ui;
    private ArrayList<ChessMove> moves = new ArrayList<>();

    public GameMenu(MenuManager ui) {
        this.ui = ui;
    }

    public MenuState run() throws IOException {
        MenuState result = MenuState.GAME;
//        ui.boardPrinter.reprint(ui.currentGameIndex, ui.currentPlayerColor, moves);
        moves = new ArrayList<>();
//        printGameMenu();
        int input = getValidOption(6);

        switch (input) {
            case 1 -> this.makeMove();
            case 2 -> this.highlightMoves();
            case 3 -> this.resign();
            case 4 -> result = this.leaveGame();
            case 5 -> this.redrawBoard();
            case 6 -> {
                printHelp();
                printGameMenu();
            }
            default -> {
                System.out.println("Invalid option. Please try again.");
                printGameMenu();
            }
        }
        return result;
    }

    private void makeMove() {
        System.out.println("makeMove functionality yet to be implemented");
    }

    private void highlightMoves() throws IOException {
        System.out.println("Highlight moves for piece (Enter coordinate, for example a1): ");

        String input = getValidCoordinate();

        ChessPosition start = convertCoordinateToPosition(input);
        System.out.println(start);

        ui.refreshGames();
        var game = ui.listedGames.get(ui.currentGameIndex).game();

        moves = (ArrayList<ChessMove>) game.validMoves(start);
    }

    private String getValidCoordinate() {
        String input = "";
        boolean isValid = false;
        while (!isValid) {
            input = scanner.nextLine().trim().toLowerCase();
            if (input.length() == 2 && input.matches("[a-h][1-8]")) {
                isValid = true;
            } else {
                System.out.println("Invalid input. Please enter a valid coordinate (e.g., a1, h8).");
            }
        }
        return input;
    }

    private ChessPosition convertCoordinateToPosition(String coordinate) {
        // Extract column (letter) and row (digit) from the input
        char col = coordinate.charAt(0);  // 'a' to 'h'
        char row = coordinate.charAt(1);  // '1' to '8'

        // Convert the column (a-h) to an index (0-7)
        int colIndex = col - 'a';  // 'a' becomes 0, 'b' becomes 1, ..., 'h' becomes 7

        // Convert the row (1-8) to an index (0-7)
        int rowIndex = 8 - (row - '0');  // '1' becomes 7, '8' becomes 0 (for reverse board)

        // Return the ChessPosition object
        return new ChessPosition(rowIndex + 1, colIndex + 1);
    }


    private void resign() throws IOException {
        String input;

        while (true) {
            System.out.println("Are you sure that you would like to resign? (y/n): ");
            input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("y")) {
                ui.serverFacade.resignGame(ui.loggedInAuth, ui.currentGameID);
                return;
            } else if (input.equals("n")) {
                System.out.println("Resignation cancelled ");
                return;
            } else {
                System.out.println("Invalid input. Please enter 'y' or 'n'.");
            }
        }
    }


    private MenuState leaveGame() throws IOException {
        String input;

        while (true) {
            System.out.println("\nAre you sure that you would like to leave? (y/n): ");
            input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("y")) {
                ui.serverFacade.leaveGame(ui.loggedInAuth, ui.currentGameID);
                return MenuState.POSTLOGIN;
            } else if (input.equals("n")) {
                System.out.println("Leave cancelled ");
                return MenuState.GAME;
            } else {
                System.out.println("Invalid input. Please enter 'y' or 'n'.");
            }
        }
    }

    private void redrawBoard() throws IOException {
        ui.boardPrinter.reprint(ui.currentGameIndex, ui.currentPlayerColor, new ArrayList<>());
    }
}
