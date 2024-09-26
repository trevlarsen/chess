package chess;

import java.util.ArrayList;

public class ShortRangeMoves {
    public ShortRangeMoves() {}

    public static ArrayList<ChessMove> calculateMoves(ChessBoard board, ChessPosition position, ChessPiece piece, int[] xDirections, int[] yDirections) {
        var moves = new ArrayList<ChessMove>();

        for (int i = 0; i < xDirections.length; i++) {
            int row = position.getRow() + xDirections[i];
            int col = position.getColumn() + yDirections[i];

            if (ChessPiece.inBounds(row, col)) {
                var current = board.pieces[row-1][col-1];

                if (current == null || current.getTeamColor() != piece.getTeamColor()) {
                    moves.add(new ChessMove(position, new ChessPosition(row, col), null));
                }
            }
        }
        return moves;
    }
}
