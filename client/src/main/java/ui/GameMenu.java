package ui;

import chess.ChessGame;

import java.awt.*;

import static ui.MenuManager.*;

public class GameMenu {

    private final MenuManager ui;

    public GameMenu(MenuManager ui) {
        this.ui = ui;
    }

    public MenuState run() {
        MenuState result = MenuState.GAME;
        ui.boardPrinter.tempPrint();
        printGameMenu();
        int input = getValidOption(6);

        switch (input) {
            case 1 -> this.makeMove();
            case 2 -> this.highlightMoves();
            case 3 -> this.resign();
            case 4 -> result = this.leaveGame();
            case 5 -> this.redrawBoard();
            case 6 -> printHelp();
            default -> System.out.println("Invalid option. Please try again.");
        }
        return result;
    }

    private void makeMove() {
        System.out.println("makeMove functionality yet to be implemented");
    }

    private void highlightMoves() {
        System.out.println("highlightMoves moves functionality yet to be implemented");
    }

    private void resign() {
        System.out.println("resign functionality yet to be implemented");
    }

    private MenuState leaveGame() {
        System.out.println("leaveGame functionality yet to be implemented");
        return MenuState.POSTLOGIN;
    }

    private void redrawBoard() {
        System.out.println("redrawBoard moves functionality yet to be implemented");
    }
}
