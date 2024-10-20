package chess;

import java.util.ArrayList;

/**
 * The {@code KingMoves} class contains methods to calculate the possible moves
 * for a King piece in a chess game.
 */
public class KingMoves {

    public KingMoves() {
    }

    /**
     * Calculates all possible moves for a King piece on the given chessboard
     * from the specified position.
     *
     * @param board    the chessboard on which the piece is located
     * @param position the current position of the King piece
     * @param piece    the King piece for which moves are being calculated
     * @return an {@code ArrayList} of {@code ChessMove} representing all possible moves
     */
    public static ArrayList<ChessMove> calculateMoves(ChessBoard board, ChessPosition position, ChessPiece piece) {
        // Define directions for King movement: one square in all eight directions
        int[] xDirections = {1, 1, -1, -1, 0, 0, 1, -1}; // Horizontal and diagonal movements
        int[] yDirections = {1, -1, 1, -1, 1, -1, 0, 0}; // Vertical and diagonal movements

        // Calculate moves using the defined directions
        return ShortRangeMoves.calculateMoves(board, position, piece, xDirections, yDirections);
    }
}
