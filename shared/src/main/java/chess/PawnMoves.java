package chess;

import java.util.ArrayList;

/**
 * The {@code PawnMoves} class contains methods to calculate the possible moves
 * for a Pawn piece in a chess game.
 */
public class PawnMoves {

    public PawnMoves() {
    }

    /**
     * Calculates all possible moves for a Pawn piece on the given chessboard
     * from the specified position.
     *
     * @param board    the chessboard on which the piece is located
     * @param position the current position of the Pawn piece
     * @param piece    the Pawn piece for which moves are being calculated
     * @return an {@code ArrayList} of {@code ChessMove} representing all possible moves
     */
    public static ArrayList<ChessMove> calculateMoves(ChessBoard board, ChessPosition position, ChessPiece piece) {
        var moves = new ArrayList<ChessMove>();
        int teamDirection; // Direction the Pawn moves based on its team color
        int row; // Row of the Pawn's position
        int col; // Column of the Pawn's position
        ChessPiece currentPiece; // Current piece at the target position

        // Set the direction of movement based on team color
        teamDirection = (piece.getTeamColor() == ChessGame.TeamColor.WHITE) ? 1 : -1;

        // Check for one square forward move
        row = position.getRow() + teamDirection;
        col = position.getColumn();
        currentPiece = board.pieces[row - 1][col - 1];
        if (currentPiece == null) {
            moves.add(new ChessMove(position, new ChessPosition(row, col), null));
        }

        // Check for capturing moves
        int[] xDirections = {teamDirection, teamDirection};
        int[] yDirections = {1, -1};
        for (int i = 0; i < xDirections.length; i++) {
            row = position.getRow() + xDirections[i];
            col = position.getColumn() + yDirections[i];
            if (ChessPiece.inBounds(row, col)) {
                currentPiece = board.pieces[row - 1][col - 1];
                if (currentPiece != null && currentPiece.getTeamColor() != piece.getTeamColor()) {
                    moves.add(new ChessMove(position, new ChessPosition(row, col), null));
                }
            }
        }

        // Handle promotions
        var toRemove = new ArrayList<ChessMove>();
        var toAdd = new ArrayList<ChessMove>();
        for (var move : moves) {
            var r = move.getEndPosition().getRow();
            // If the Pawn reaches the promotion rank, add promotion options
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
        moves.removeAll(toRemove); // Remove original moves that will be promoted
        moves.addAll(toAdd); // Add promotion moves

        // Check for the option to move two squares forward on the first move
        if ((position.getRow() == 2 && piece.getTeamColor() == ChessGame.TeamColor.WHITE) ||
                (position.getRow() == 7 && piece.getTeamColor() == ChessGame.TeamColor.BLACK)) {
            row = position.getRow() + teamDirection;
            col = position.getColumn();
            currentPiece = board.pieces[row - 1][col - 1];
            if (currentPiece == null) {
                row += teamDirection;
                currentPiece = board.pieces[row - 1][col - 1];
                if (currentPiece == null) {
                    moves.add(new ChessMove(position, new ChessPosition(row, col), null));
                }
            }
        }
        return moves; // Return all calculated moves
    }
}
