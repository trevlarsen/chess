package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KnightMoves {

    public KnightMoves() {
    }

    /*
    Accepts ChessBoard, ChessPosition, and ChessPiece
    Compute all possible Knight moves, disregarding the King's status
     */
    public static ArrayList<ChessMove> calculateMoves(ChessBoard board, ChessPosition position, ChessPiece piece) {

        // Initialize list of moves
        var moves = new ArrayList<ChessMove>();

        // Define directions
        int[] xs = {1, 1, -1, -1, 2, 2, -2, -2};
        int[] ys = {2, -2, 2, -2, 1, -1, 1, -1};

        // Cycle through directions
        for (int i = 0; i < 8; i++) {
            // Move once in direction
            int row = position.getRow() + xs[i];
            int col = position.getColumn() + ys[i];

            // For each position in that direction, add move if possible
            if (ChessPiece.inBounds(row, col)) {
                ChessPiece current = board.pieces[row-1][col-1];
                // Can move if no piece is there
                if (current == null) {
                    moves.add(new ChessMove(position, new ChessPosition(row, col), null));
                    // Can move if enemy is there, capture and stop
                } else if (current.getTeamColor() != piece.getTeamColor()) {
                    moves.add(new ChessMove(position, new ChessPosition(row, col), null));
                }
            }
        }
        return moves;
    }
}