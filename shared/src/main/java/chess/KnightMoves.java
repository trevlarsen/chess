package chess;

import java.util.ArrayList;

public class KnightMoves {
    public KnightMoves() {}

    public static ArrayList<ChessMove> calculateMoves(ChessBoard board, ChessPosition position, ChessPiece piece) {

        int[] xDirections = {1, 1, -1, -1, 2, 2, -2, -2};
        int[] yDirections = {2, -2, 2, -2, 1, -1, 1, -1};

        return ShortRangeMoves.calculateMoves(board, position, piece, xDirections, yDirections);
    }
}
