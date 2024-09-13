package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMoves {

    public PawnMoves() { }

    /*
    Accepts ChessBoard, ChessPosition, and ChessPiece
    Compute all possible Pawn moves, disregarding the King's status
     */
    public static ArrayList<ChessMove> calculateMoves(ChessBoard board, ChessPosition position, ChessPiece piece) {

        var moves = new ArrayList<ChessMove>();
        // Determine direction of movement based on piece color
        int scale;
        if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
            scale = 1;
        } else {
            scale = -1;
        }

        // Forward move direction
        var row = position.getRow() + scale;
        var col = position.getColumn();
        // Check if spot is open
        ChessPiece current = board.pieces[row-1][col-1];
        if (current == null) {
            moves.add(new ChessMove(position, new ChessPosition(row, col), null));
        }

        // Capture move directions
        int[] xs = {scale, scale};
        int[] ys = {1, -1};
        // For each capture position
        for (int i = 0; i < 2; i++) {
            var row2 = position.getRow() + xs[i];
            var col2 = position.getColumn() + ys[i];
            // Check bounds
            if (ChessPiece.inBounds(row2, col2)) {
                ChessPiece current2 = board.pieces[row2 - 1][col2 - 1];
                // Check if capture is available
                if (current2 != null && current2.getTeamColor() != piece.getTeamColor()) {
                    moves.add(new ChessMove(position, new ChessPosition(row2, col2), null));
                }
            }
        }

        // Add promotion options for eligible moves
        var toRemove = new ArrayList<ChessMove>();
        var toAdd = new ArrayList<ChessMove>();
        for (var move : moves) {
            // Determine if move puts piece in first or last row
            var r = move.getEndPosition().getRow();
            if (r == 1 || r == 8 ) {
                // Extract old move information
                var start = move.getStartPosition();
                var end = move.getEndPosition();

                // Schedule to remove old move
                toRemove.add(move);

                // Schedule to add new moves with promotion options
                toAdd.add(new ChessMove(start, end, ChessPiece.PieceType.QUEEN));
                toAdd.add(new ChessMove(start, end, ChessPiece.PieceType.BISHOP));
                toAdd.add(new ChessMove(start, end, ChessPiece.PieceType.KNIGHT));
                toAdd.add(new ChessMove(start, end, ChessPiece.PieceType.ROOK));
            }
        }
        // Update list with promotion information
        moves.removeAll(toRemove);
        moves.addAll(toAdd);

        // First move direction
        if ((position.getRow() == 2 && piece.getTeamColor() == ChessGame.TeamColor.WHITE) || (position.getRow() == 7 && piece.getTeamColor() == ChessGame.TeamColor.BLACK)) {
            // Check if one spot ahead is open
            var row3 = position.getRow() + scale;
            var col3 = position.getColumn();
            ChessPiece current3 = board.pieces[row3 - 1][col3 - 1];
            if (current3 == null) {
                // Check if two spots ahead is open
                var row4 = row3 + scale;
                ChessPiece current4 = board.pieces[row4 - 1][col3 - 1];
                if (current4 == null) {
                    moves.add(new ChessMove(position, new ChessPosition(row4, col3), null));
                }
            }
        }

        return moves;
    }
}
