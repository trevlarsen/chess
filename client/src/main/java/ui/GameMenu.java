package ui;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPiece;
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

    public void makeMove() throws IOException {
        try {
            var currentTeamColor = ui.currentPlayerColor;
            // Prompt user for move input
            ChessPosition start = null;
            ChessPosition end = null;

            while (start == null || end == null) {
                System.out.println("Enter your move (e.g., e2 e4):");
                String moveInput = scanner.nextLine();

                String[] parts = moveInput.split(" ");
                if (parts.length != 2) {
                    System.out.println("Invalid move format. Use 'e2 e4'.");
                    continue;
                }

                start = parsePosition(parts[0]);
                end = parsePosition(parts[1]);

                if (start == null || end == null) {
                    System.out.println("Invalid position(s). Please try again.");
                }
            }

            // Check for pawn promotion
            ChessPiece.PieceType promotion = null;
            if (isPromotion(start, end)) {
                while (promotion == null) {
                    System.out.println("Enter piece for promotion (e.g., QUEEN, ROOK, BISHOP, KNIGHT):");
                    String promotionInput = scanner.nextLine().toUpperCase();
                    promotion = parsePromotionPiece(promotionInput);

                    if (promotion == null) {
                        System.out.println("Invalid promotion piece. Please try again.");
                    }
                }
            }

            var move = new ChessMove(start, end, promotion);
            ui.serverFacade.makeMove(ui.loggedInAuth, ui.currentGameID, move);
        } catch (Exception e) {
            printError("Make Move", e.getMessage());
        }
    }

    private static ChessPosition parsePosition(String input) throws IOException {
        if (input.length() != 2) {
            throw new IOException(input + " is not a valid square");
        }

        char colChar = input.charAt(0);
        char rowChar = input.charAt(1);

        if (colChar < 'a' || colChar > 'h' || rowChar < '1' || rowChar > '8') {
            throw new IOException(input + " is not a valid square.");
        }

        int col = colChar - 'a'; // 'a' -> 0, 'b' -> 1, ...
        int row = rowChar - '1'; // '1' -> 0, '2' -> 1, ...

        return new ChessPosition(row + 1, col + 1); // Adjust to 1-based indexing
    }

    private static boolean isPromotion(ChessPosition start, ChessPosition end) {
        // A pawn promotion occurs when a pawn moves to the last rank
        return (start.getRow() == 7 || start.getRow() == 2) && (end.getRow() == 8 || end.getRow() == 1);
    }

    private static ChessPiece.PieceType parsePromotionPiece(String input) {
        try {
            return ChessPiece.PieceType.valueOf(input);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }


    private void highlightMoves() throws IOException {
        System.out.println("Highlight moves for piece (Enter coordinate, for example a1): ");

        String input = getValidCoordinate();

        ChessPosition start = parsePosition(input);
        System.out.println(start);

        ui.refreshGames();
        var game = ui.listedGames.get(ui.currentGameIndex).game();
        var piece = game.getBoard().getPiece(start);

        if (piece == null) {
            System.out.println("There is no piece there");
        } else {
            moves = (ArrayList<ChessMove>) game.validMoves(start);
            ui.boardPrinter.reprint(ui.currentGameIndex, ui.currentPlayerColor, moves);
        }
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
//        ui.boardPrinter.printboth(ui.currentGameIndex, ui.currentPlayerColor, new ArrayList<>());
    }
}
