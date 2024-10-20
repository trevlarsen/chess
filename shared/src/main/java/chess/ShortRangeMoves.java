package chess;

import java.util.ArrayList;

/**
 * The {@code ShortRangeMoves} class provides methods to calculate potential moves
 * for chess pieces that can move once in any direction, such as the King and Knight.
 */
public class ShortRangeMoves {

    public ShortRangeMoves() {
    }

    /**
     * Calculates all possible short-range moves for a given chess piece
     * based on specified directional vectors.
     *
     * @param board       the chessboard on which the piece is located
     * @param position    the current position of the chess piece
     * @param piece       the chess piece for which moves are being calculated
     * @param xDirections an array of x-directional vectors for movement
     * @param yDirections an array of y-directional vectors for movement
     * @return an {@code ArrayList} of {@code ChessMove} representing all possible short-range moves
     */
    public static ArrayList<ChessMove> calculateMoves(ChessBoard board,
                                                      ChessPosition position,
                                                      ChessPiece piece,
                                                      int[] xDirections,
                                                      int[] yDirections) {
        var moves = new ArrayList<ChessMove>();

        // Iterate through each direction to calculate possible moves
        for (int i = 0; i < xDirections.length; i++) {
            // Calculate the new position based on the directional vector
            int row = position.getRow() + xDirections[i];
            int col = position.getColumn() + yDirections[i];

            // Check if the new position is within the bounds of the board
            if (ChessPiece.inBounds(row, col)) {
                var current = board.pieces[row - 1][col - 1];

                // If the target square is empty or occupied by an enemy piece, add the move
                if (current == null || current.getTeamColor() != piece.getTeamColor()) {
                    moves.add(new ChessMove(position, new ChessPosition(row, col), null));
                }
            }
        }
        return moves; // Return all calculated short-range moves
    }
}
