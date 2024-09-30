package chess;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {

    public ChessPiece[][] pieces = new ChessPiece[8][8];

    public ChessBoard() {
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        pieces[position.getRow()-1][position.getColumn()-1] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return pieces[position.getRow()-1][position.getColumn()-1];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        pieces[0] = new ChessPiece[] {
                                        new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK),
                                        new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT),
                                        new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP),
                                        new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN),
                                        new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING),
                                        new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP),
                                        new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT),
                                        new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK),};
        for (int i = 1; i <= 8; i++) {
            this.addPiece(new ChessPosition(2, i), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN));
        }

        for (int i = 2; i <= 5; i++) {
            Arrays.fill(pieces[i], null);
        }

        for (int i = 1; i <= 8; i++) {
            this.addPiece(new ChessPosition(7, i), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN));
        }

        pieces[7] = new ChessPiece[] {  new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK),
                                        new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT),
                                        new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP),
                                        new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN),
                                        new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING),
                                        new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP),
                                        new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT),
                                        new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK),};
    }

    // Gets each position and piece for a given team
    public Map<ChessPosition, ChessPiece> getTeamPiecePositions(ChessGame.TeamColor teamColor) {
        Map<ChessPosition, ChessPiece> piecePositions = new HashMap<>();
        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces[0].length; j++) {
                var current = pieces[i][j];
                if (current != null && current.getTeamColor() == teamColor) {
                    piecePositions.put(new ChessPosition(i+1, j+1), current);
                }
            }
        }
        return piecePositions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessBoard that = (ChessBoard) o;
        return Arrays.deepEquals(pieces, that.pieces);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(pieces);
    }

    @Override
    public String toString() {
        var rep = new StringBuilder("Board");
        for (int i = 0; i <= 7; i++) {
            rep.append(Arrays.deepToString(pieces[i])).append("\n");
        }
        return rep.toString();
    }
}
