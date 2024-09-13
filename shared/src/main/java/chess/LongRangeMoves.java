package chess;

import java.util.ArrayList;

public class LongRangeMoves {

    public LongRangeMoves() { }

    public static ArrayList<ChessMove> calculateMoves(ChessBoard board, ChessPosition position, ChessPiece piece, int[] xDirections, int[] yDirections) {

        var moves = new ArrayList<ChessMove>();

        // Cycle through directions
        for (int i = 0; i < xDirections.length; i++) {
            // Move once in direction
            int row = position.getRow() + xDirections[i];
            int col = position.getColumn() + yDirections[i];

            // For each position in that direction, add move if possible
            while (ChessPiece.inBounds(row, col)) {
                ChessPiece current = board.pieces[row-1][col-1];
                // Can move if no piece is there
                if (current == null) {
                    moves.add(new ChessMove(position, new ChessPosition(row, col), null));
                    // Can move if enemy is there, capture and stop
                } else if (current.getTeamColor() != piece.getTeamColor()) {
                    moves.add(new ChessMove(position, new ChessPosition(row, col), null));
                    break;
                    // Can't move if ally is there
                } else {
                    break;
                }
                // Move again in same direction
                row += xDirections[i];
                col += yDirections[i];
            }
        }
        return moves;
    }
}
