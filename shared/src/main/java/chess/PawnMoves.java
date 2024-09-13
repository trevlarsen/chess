package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMoves {

    public PawnMoves() { }

    /*
    Accepts ChessBoard, ChessPosition, and ChessPiece
    Compute all possible Queen moves, disregarding the King's status
     */
    public static ArrayList<ChessMove> calculateMoves(ChessBoard board, ChessPosition position, ChessPiece piece) {

        // Initialize list of moves
        var moves = new ArrayList<ChessMove>();

        int scale;
        if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
            scale = 1;
        } else {
            scale = -1;
        }

        // Basic Move
        if (ChessPiece.inBounds(position.getRow() + scale, position.getColumn())) {
            scale = 1;
        }

        return moves;
    }
}
