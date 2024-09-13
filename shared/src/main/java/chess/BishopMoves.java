package chess;

import java.util.ArrayList;

public class BishopMoves {

    public BishopMoves() { }

    /*
    Accepts ChessBoard, ChessPosition, and ChessPiece
    Compute all possible Bishop moves, disregarding the King's status
     */
    public static ArrayList<ChessMove> calculateMoves(ChessBoard board, ChessPosition position, ChessPiece piece) {

        // Define directions
        int[] xDirections = {1, -1, 1, -1};
        int[] yDirections = {1, 1, -1, -1};

        // Calculate moves using directions
        return LongRangeMoves.calculateMoves(board, position, piece, xDirections, yDirections);
    }
}
