package chess;

import java.util.ArrayList;

public class KingMoves {
    public KingMoves() {}

    public static ArrayList<ChessMove> calculateMoves(ChessBoard board, ChessPosition position, ChessPiece piece) {

        int[] xDirections = {1, 1, -1, -1, 0, 0, 1, -1};
        int[] yDirections = {1, -1, 1, -1, 1, -1, 0, 0};

        return ShortRangeMoves.calculateMoves(board, position, piece, xDirections, yDirections);
    }
}
