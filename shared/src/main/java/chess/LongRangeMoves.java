package chess;

import java.util.ArrayList;

/**
 * The {@code LongRangeMoves} class provides methods to calculate potential moves
 * for chess pieces that can move over multiple squares in a straight line,
 * such as the Rook, Bishop, and Queen.
 */
public class LongRangeMoves {

    public LongRangeMoves() {
    }

    /**
     * Calculates all possible long-range moves for a given chess piece
     * based on specified directional vectors.
     *
     * @param board       the chessboard on which the piece is located
     * @param position    the current position of the chess piece
     * @param piece       the chess piece for which moves are being calculated
     * @param xDirections an array of x-directional vectors for movement
     * @param yDirections an array of y-directional vectors for movement
     * @return an {@code ArrayList} of {@code ChessMove} representing all possible long-range moves
     */
    public static ArrayList<ChessMove> calculateMoves(ChessBoard board, ChessPosition position, ChessPiece piece, int[] xDirections, int[] yDirections) {
        var moves = new ArrayList<ChessMove>();

        // Cycle through each direction
        for (int i = 0; i < xDirections.length; i++) {
            // Calculate the new position based on the directional vector
            int row = position.getRow() + xDirections[i];
            int col = position.getColumn() + yDirections[i];

            // Continue moving in the same direction until the bounds of the board are exceeded
            while (ChessPiece.inBounds(row, col)) {
                ChessPiece current = board.pieces[row - 1][col - 1];

                // If the target square is empty, add the move
                if (current == null) {
                    moves.add(new ChessMove(position, new ChessPosition(row, col), null));
                    // If an enemy piece is present, add the move and stop
                } else if (current.getTeamColor() != piece.getTeamColor()) {
                    moves.add(new ChessMove(position, new ChessPosition(row, col), null));
                    break; // Stop moving in this direction after capturing
                    // If an ally piece is present, stop moving in this direction
                } else {
                    break;
                }
                // Move again in the same direction
                row += xDirections[i];
                col += yDirections[i];
            }
        }
        return moves; // Return all calculated long-range moves
    }
}
