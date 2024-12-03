package ui;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.io.IOException;
import java.util.ArrayList;

public class ChessBoardPrinter {

    private static final String WHITE_PIECE_COLOR = EscapeSequences.SET_TEXT_COLOR_BLUE;
    private static final String BLACK_PIECE_COLOR = EscapeSequences.SET_TEXT_COLOR_RED;
    private static final String RESET_COLOR = EscapeSequences.RESET_TEXT_COLOR + EscapeSequences.SET_BG_COLOR_BLACK;
    private static final String LIGHT_SQUARE = EscapeSequences.SET_BG_COLOR_LIGHT_GREY;
    private static final String DARK_SQUARE = EscapeSequences.SET_BG_COLOR_DARK_GREY;
    private static final String LIGHT_GREEN_SQUARE = EscapeSequences.SET_BG_COLOR_GREEN;
    private static final String DARK_GREEN_SQUARE = EscapeSequences.SET_BG_COLOR_DARK_GREEN;
    private final MenuManager ui;

    public ChessBoardPrinter(MenuManager ui) {
        this.ui = ui;
    }

    public void printBoard(ChessPiece[][] board, boolean isWhiteView, ArrayList<ChessMove> moves) {
        String[] ranks = isWhiteView ? new String[]{"8", "7", "6", "5", "4", "3", "2", "1"} :
                new String[]{"1", "2", "3", "4", "5", "6", "7", "8"};
        String[] files = isWhiteView ? new String[]{"a", "b", "c", "d", "e", "f", "g", "h"} :
                new String[]{"h", "g", "f", "e", "d", "c", "b", "a"};

        // Print the file labels (a-h or h-a)
        System.out.print("\n  ");
        for (String file : files) {
            System.out.print(" " + file + " ");
        }
        System.out.println();

        // Print the board rows
        for (int row = 0; row < 8; row++) {
            System.out.print(ranks[row] + " ");
            for (int col = 0; col < 8; col++) {
                int displayRow = isWhiteView ? row : 7 - row;
                int displayCol = isWhiteView ? col : 7 - col;
                ChessPiece piece = board[displayRow][displayCol];
                String squareColor = (displayRow + displayCol) % 2 == 0 ? LIGHT_SQUARE : DARK_SQUARE;

                // Highlight squares that are in the list of moves
                if (isMoveInList(displayRow, displayCol, moves)) {
                    squareColor = (displayRow + displayCol) % 2 == 0 ? LIGHT_GREEN_SQUARE : DARK_GREEN_SQUARE;
                }

                // Print the square with the piece's color
                System.out.print(squareColor + getPieceRepresentation(piece) + RESET_COLOR);
            }
            System.out.println(" " + ranks[row]);
        }

        // Print the file labels again
        System.out.print("  ");
        for (String file : files) {
            System.out.print(" " + file + " ");
        }
        System.out.println();
    }

    /**
     * Checks if a specific square (row, col) is in the list of moves.
     *
     * @param row   the row of the square
     * @param col   the column of the square
     * @param moves the list of ChessMove objects to check against
     * @return true if the square is in the list of moves, false otherwise
     */
    private boolean isMoveInList(int row, int col, ArrayList<ChessMove> moves) {
        for (ChessMove move : moves) {
            if (move.getStartPosition().getRow() == row && move.getStartPosition().getColumn() == col) {
                return true;  // start position is in the list of moves
            }
            if (move.getEndPosition().getRow() == row && move.getEndPosition().getColumn() == col) {
                return true;  // end position is in the list of moves
            }
        }
        return false;
    }

    /**
     * Returns a string representation of the piece to be printed.
     *
     * @param piece the ChessPiece object
     * @return a string representing the piece (e.g., "W K" for White King)
     */
    private String getPieceRepresentation(ChessPiece piece) {
        if (piece == null) {
            return "   "; // empty square (three spaces)
        }
        String color = isWhite(piece) ? WHITE_PIECE_COLOR : BLACK_PIECE_COLOR;  // White or Black piece
        // Make sure the piece symbol is centered in the 3-character wide space
        return color + String.format("%-3s", String.format("%2s", getSymbol(piece))) + RESET_COLOR;  // Center the piece symbol
    }


    /**
     * Checks if the given piece is a white piece.
     *
     * @param piece the ChessPiece object
     * @return true if the piece is white, false otherwise
     */
    public boolean isWhite(ChessPiece piece) {
        return piece.getTeamColor() == ChessGame.TeamColor.WHITE;
    }

    /**
     * Returns the symbol of the piece (e.g., "K" for King, "Q" for Queen, etc.).
     *
     * @param piece the ChessPiece object
     * @return the symbol of the piece
     */
    public String getSymbol(ChessPiece piece) {
        return switch (piece.getPieceType()) {
            case KING -> "K";
            case QUEEN -> "Q";
            case ROOK -> "R";
            case BISHOP -> "B";
            case KNIGHT -> "N";
            case PAWN -> "P";
        };
    }

    public void reprint(int gameIndex, ChessGame.TeamColor color, ArrayList<ChessMove> moves) throws IOException {
        ui.refreshGames();
        var game = ui.listedGames.get(gameIndex).game().getBoard().pieces;
        printBoard(game, color != ChessGame.TeamColor.WHITE, moves);
    }
}
