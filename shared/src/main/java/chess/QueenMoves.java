package chess;

import java.util.ArrayList;

public class QueenMoves {

    public QueenMoves() { }

    /*
    Accepts ChessBoard, ChessPosition, and ChessPiece
    Compute all possible Queen moves, disregarding the King's status
     */
    public static ArrayList<ChessMove> calculateMoves(ChessBoard board, ChessPosition position, ChessPiece piece) {

        // Define directions
        int[] xDirections = {1, -1, 1, -1, 0, 0, 1, -1};
        int[] yDirections = {1, 1, -1, -1, -1, 1, 0, 0};

        // Calculate moves using directions
        return LongRangeMoves.calculateMoves(board, position, piece, xDirections, yDirections);
    }
}