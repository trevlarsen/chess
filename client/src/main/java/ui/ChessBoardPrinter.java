package ui;

import chess.ChessGame;
import chess.ChessPiece;

public class ChessBoardPrinter {

    private static final String WHITE_PIECE_COLOR = EscapeSequences.SET_TEXT_COLOR_BLUE;
    private static final String BLACK_PIECE_COLOR = EscapeSequences.SET_TEXT_COLOR_RED;
    private static final String RESET_COLOR = EscapeSequences.RESET_TEXT_COLOR + EscapeSequences.SET_BG_COLOR_BLACK;
    private static final String LIGHT_SQUARE = EscapeSequences.SET_BG_COLOR_LIGHT_GREY;
    private static final String DARK_SQUARE = EscapeSequences.SET_BG_COLOR_DARK_GREY;
    private final MenuManager ui;

    public ChessBoardPrinter(MenuManager ui) {
        this.ui = ui;
    }

    public void printBoard(ChessPiece[][] board, boolean isWhiteView) {
        String[] ranks = isWhiteView ? new String[]{"8", "7", "6", "5", "4", "3", "2", "1"} :
                new String[]{"1", "2", "3", "4", "5", "6", "7", "8"};
        String[] files = isWhiteView ? new String[]{"a", "b", "c", "d", "e", "f", "g", "h"} :
                new String[]{"h", "g", "f", "e", "d", "c", "b", "a"};

        System.out.print("  ");
        for (String file : files) {
            System.out.print(" " + file + " ");
        }
        System.out.println();

        for (int row = 0; row < 8; row++) {
            System.out.print(ranks[row] + " ");
            for (int col = 0; col < 8; col++) {
                int displayRow = isWhiteView ? row : 7 - row;
                int displayCol = isWhiteView ? col : 7 - col;
                ChessPiece piece = board[displayRow][displayCol];
                String squareColor = (displayRow + displayCol) % 2 == 0 ? LIGHT_SQUARE : DARK_SQUARE;
                System.out.print(squareColor + getPieceRepresentation(piece) + RESET_COLOR);
            }
            System.out.println(" " + ranks[row]);
        }

        System.out.print("  ");
        for (String file : files) {
            System.out.print(" " + file + " ");
        }
        System.out.println();
    }

    private String getPieceRepresentation(ChessPiece piece) {
        if (piece == null) {
            return "   ";
        }
        String color = isWhite(piece) ? WHITE_PIECE_COLOR : BLACK_PIECE_COLOR;
        return color + " " + getSymbol(piece) + " " + RESET_COLOR;
    }

    public boolean isWhite(ChessPiece piece) {
        return piece.getTeamColor() == ChessGame.TeamColor.WHITE;
    }

//    public String getSymbol2(ChessPiece piece) {
//        return switch (piece.getPieceType()) {
//            case KING -> isWhite(piece) ? "♔" : "♚";
//            case QUEEN -> isWhite(piece) ? "♕" : "♛";
//            case ROOK -> isWhite(piece) ? "♖" : "♜";
//            case BISHOP -> isWhite(piece) ? "♗" : "♝";
//            case KNIGHT -> isWhite(piece) ? "♘" : "♞";
//            case PAWN -> isWhite(piece) ? "♙" : "♟";
//        };
//    }

    public String getSymbol(ChessPiece piece) {
//        getSymbol2(piece);  // Unnecessary line removed
        return switch (piece.getPieceType()) {
            case KING -> "K";
            case QUEEN -> "Q";
            case ROOK -> "R";
            case BISHOP -> "B";
            case KNIGHT -> "N";
            case PAWN -> "P";
        };
    }

    public void tempPrint() {
        var game = ui.currentGame.getBoard().pieces;
        printBoard(game, false);
        printBoard(game, true);
    }

    // Main method to test (if needed)
//    public static void main(String[] args) {
//        ChessGame game = new ChessGame();
//        ChessBoardPrinter boardPrinter = new ChessBoardPrinter();
//        var gameBoard = game.getBoard().pieces;
//        boardPrinter.printBoard(gameBoard, false);
//        System.out.println("\n");
//        boardPrinter.printBoard(gameBoard, true);
//    }
}
