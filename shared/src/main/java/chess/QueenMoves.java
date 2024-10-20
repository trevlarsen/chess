package chess;

import java.util.ArrayList;

/**
 * The {@code QueenMoves} class contains methods to calculate the possible moves
 * for a Queen piece in a chess game.
 */
public class QueenMoves {

    public QueenMoves() {
    }

    /**
     * Calculates all possible moves for a Queen piece on the given chessboard
     * from the specified position.
     *
     * @param board    the chessboard on which the piece is located
     * @param position the current position of the Queen piece
     * @param piece    the Queen piece for which moves are being calculated
     * @return an {@code ArrayList} of {@code ChessMove} representing all possible moves
     */
    public static ArrayList<ChessMove> calculateMoves(ChessBoard board, ChessPosition position, ChessPiece piece) {
        // Define directions for Queen movement: vertical, horizontal, and diagonal
        int[] xDirections = {1, -1, 1, -1, 0, 0, 1, -1}; // x-coordinates of possible moves
        int[] yDirections = {1, 1, -1, -1, -1, 1, 0, 0}; // y-coordinates of possible moves

        // Calculate moves using the defined directions
        return LongRangeMoves.calculateMoves(board, position, piece, xDirections, yDirections);
    }
}
