package chess;

import java.util.ArrayList;
import java.util.Collection;

public class BishopMoves {

//    public static void main(String[] args) {
//        var board = new ChessBoard();
//        System.out.println(board);
//    }

    public BishopMoves() { }

    public static ArrayList<ChessMove> calculateMoves(ChessBoard board, ChessPosition position, ChessPiece piece) {

        System.out.println(board);
        var moves = new ArrayList<ChessMove>();

        int[] xs = {1, -1, 1, -1};
        int[] ys = {1, 1, -1, -1};

        for (int i = 0; i < 4; i++) {
            int row = position.getRow() + xs[i];
            int col = position.getColumn() + ys[i];

            while (ChessPiece.inBounds(row, col)) {
                ChessPiece current = board.pieces[row-1][col-1];
                if (current == null) {
                    moves.add(new ChessMove(position, new ChessPosition(row, col), null));
                } else if (current.getTeamColor() != piece.getTeamColor()) {
                    moves.add(new ChessMove(position, new ChessPosition(row, col), null));
                    break;
                } else {
                    break;
                }
                row += xs[i];
                col += ys[i];
            }
        }

        return moves;
    }
}
