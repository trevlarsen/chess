package chess;

import java.util.ArrayList;

/**
 * The {@code RookMoves} class contains methods to calculate the possible moves
 * for a Rook piece in a chess game.
 */
public class RookMoves {

    public RookMoves() {
    }

    /**
     * Calculates all possible moves for a Rook piece on the given chessboard
     * from the specified position.
     *
     * @param board    the chessboard on which the piece is located
     * @param position the current position of the Rook piece
     * @param piece    the Rook piece for which moves are being calculated
     * @return an {@code ArrayList} of {@code ChessMove} representing all possible moves
     */
    public static ArrayList<ChessMove> calculateMoves(ChessBoard board, ChessPosition position, ChessPiece piece) {
        // Define directions for Rook movement: vertical and horizontal
        int[] xDirections = {0, 0, 1, -1}; // x-coordinates of possible moves
        int[] yDirections = {-1, 1, 0, 0}; // y-coordinates of possible moves

        // Calculate moves using the defined directions
        return LongRangeMoves.calculateMoves(board, position, piece, xDirections, yDirections);
    }
}
