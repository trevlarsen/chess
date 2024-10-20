package chess;

import java.util.ArrayList;

/**
 * The {@code BishopMoves} class contains methods to calculate the possible moves
 * for a Bishop piece in a chess game.
 */
public class BishopMoves {

    public BishopMoves() {
    }

    /**
     * Calculates all possible moves for a Bishop piece on the given chessboard
     * from the specified position. The King's status is disregarded in this calculation.
     *
     * @param board    the chessboard on which the piece is located
     * @param position the current position of the Bishop piece
     * @param piece    the Bishop piece for which moves are being calculated
     * @return an {@code ArrayList} of {@code ChessMove} representing all possible moves
     */
    public static ArrayList<ChessMove> calculateMoves(ChessBoard board, ChessPosition position, ChessPiece piece) {
        // Define directions for Bishop movement: diagonal directions
        int[] xDirections = {1, -1, 1, -1}; // Positive and negative directions along the x-axis
        int[] yDirections = {1, 1, -1, -1}; // Positive and negative directions along the y-axis

        // Calculate moves using the defined directions
        return LongRangeMoves.calculateMoves(board, position, piece, xDirections, yDirections);
    }
}
