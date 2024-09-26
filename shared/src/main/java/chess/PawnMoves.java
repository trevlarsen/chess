package chess;

import java.util.ArrayList;
import java.util.Currency;

public class PawnMoves {
    public PawnMoves() {}

    public static ArrayList<ChessMove> calculateMoves(ChessBoard board, ChessPosition position, ChessPiece piece) {
        var moves = new ArrayList<ChessMove>();
        int scale;
        int row;
        int col;
        ChessPiece current;

        // scale
        if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
            scale = 1;
        } else {
            scale = -1;
        }

        // one forward
        row = position.getRow() + scale;
        col = position.getColumn();
        current = board.pieces[row-1][col-1];
        if (current == null) {
            moves.add(new ChessMove(position, new ChessPosition(row, col), null));
        }

        // capture
        int [] xDirections = {scale, scale};
        int [] yDirections = {1, -1};
        for (int i = 0; i < xDirections.length; i++) {
            row = position.getRow() + xDirections[i];
            col = position.getColumn() + yDirections[i];
            if (ChessPiece.inBounds(row, col)) {
                current = board.pieces[row-1][col-1];
                if (current != null && current.getTeamColor() != piece.getTeamColor()) {
                    moves.add(new ChessMove(position, new ChessPosition(row, col), null));
                }
            }
        }

        // promotions
        var toRemove = new ArrayList<ChessMove>();
        var toAdd = new ArrayList<ChessMove>();
        for (var move : moves) {
            var r = move.getEndPosition().getRow();
            if (r == 1 || r == 8) {
                var start = move.getStartPosition();
                var end = move.getEndPosition();

                toRemove.add(move);

                toAdd.add(new ChessMove(start, end, ChessPiece.PieceType.ROOK));
                toAdd.add(new ChessMove(start, end, ChessPiece.PieceType.QUEEN));
                toAdd.add(new ChessMove(start, end, ChessPiece.PieceType.BISHOP));
                toAdd.add(new ChessMove(start, end, ChessPiece.PieceType.KNIGHT));
            }
        }
        moves.removeAll(toRemove);
        moves.addAll(toAdd);

        // first move
        if ((position.getRow() == 2 && piece.getTeamColor() == ChessGame.TeamColor.WHITE) || (position.getRow() == 7 && piece.getTeamColor() == ChessGame.TeamColor.BLACK)) {
            row = position.getRow() + scale;
            col = position.getColumn();
            current = board.pieces[row-1][col-1];
            if (current == null) {
                row += scale;
                current = board.pieces[row-1][col-1];
                if (current == null) {
                    moves.add(new ChessMove(position, new ChessPosition(row, col), null));
                }
            }
        }
        return moves;
    }
}
