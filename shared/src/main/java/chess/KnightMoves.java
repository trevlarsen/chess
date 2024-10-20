package chess;

import java.util.ArrayList;

/**
 * The {@code KnightMoves} class contains methods to calculate the possible moves
 * for a Knight piece in a chess game.
 */
public class KnightMoves {

    public KnightMoves() {
    }

    /**
     * Calculates all possible moves for a Knight piece on the given chessboard
     * from the specified position.
     *
     * @param board    the chessboard on which the piece is located
     * @param position the current position of the Knight piece
     * @param piece    the Knight piece for which moves are being calculated
     * @return an {@code ArrayList} of {@code ChessMove} representing all possible moves
     */
    public static ArrayList<ChessMove> calculateMoves(ChessBoard board, ChessPosition position, ChessPiece piece) {
        // Define the directions for Knight movement: L-shape moves
        int[] xDirections = {1, 1, -1, -1, 2, 2, -2, -2}; // x-coordinates of possible moves
        int[] yDirections = {2, -2, 2, -2, 1, -1, 1, -1}; // y-coordinates of possible moves

        // Calculate moves using the defined directions
        return ShortRangeMoves.calculateMoves(board, position, piece, xDirections, yDirections);
    }
}
