package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    ChessBoard board;
    public TeamColor turn;
    public boolean isResigned;

    public ChessGame() {
        board = new ChessBoard();
        board.resetBoard();

        turn = TeamColor.WHITE;
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return turn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        turn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    public boolean isResigned() {
        return isResigned;
    }

    public void setResigned(boolean resigned) {
        isResigned = resigned;
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        // Extract information about the start position
        var attacker = board.getPiece(startPosition);
        var teamColor = attacker.getTeamColor();
        var moves = attacker.pieceMoves(board, startPosition);

        // Add a move if it is valid
        var valid = new ArrayList<ChessMove>();
        for (var move : moves) {
            // Extract information about the move
            var endPos = move.getEndPosition();
            var captured = board.getPiece(endPos);

            // Determine if a piece will be captured
            boolean pieceCaptured = captured != null;

            // Make move
            board.addPiece(endPos, attacker);
            board.pieces[startPosition.getRow() - 1][startPosition.getColumn() - 1] = null;

            // See if it is valid
            if (!isInCheck(teamColor)) {
                valid.add(move);
            }

            // Undo move
            board.addPiece(startPosition, attacker);
            board.pieces[endPos.getRow() - 1][endPos.getColumn() - 1] = null;
            if (pieceCaptured) {
                board.addPiece(endPos, captured);
            }
        }
        return valid;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        // Extract information about move
        var start = move.getStartPosition();
        var end = move.getEndPosition();
        var promotion = move.getPromotionPiece();
        var attacker = board.pieces[start.getRow() - 1][start.getColumn() - 1];

        // Check that it's the attackers turn and that the move is valid
        if (attacker == null) {
            throw new InvalidMoveException("There is no piece there");
        } else if (attacker.getTeamColor() != turn) {
            throw new InvalidMoveException("It's not your turn");
        } else if (!validMoves(start).contains(move)) {
            throw new InvalidMoveException("Invalid Move");
        }

        // Implement any promotion
        if (promotion != null) {
            attacker.setPieceType(promotion);
        }

        // Move the pieces
        board.addPiece(end, attacker);
        board.pieces[start.getRow() - 1][start.getColumn() - 1] = null;

        // Switch turns
        if (turn == TeamColor.WHITE) {
            setTeamTurn(TeamColor.BLACK);
        } else {
            setTeamTurn(TeamColor.WHITE);
        }
    }

    /**
     * Checks if the specified team is in check by looking for any attacking pieces that can reach the king.
     *
     * @param teamColor the color of the team to check for check status
     * @return true if the team is in check; false otherwise
     */
    public boolean isInCheck(TeamColor teamColor) {
        // Get the position of the king
        var kingPosition = getKingPosition(teamColor);

        // Check each position on the board for enemy pieces
        for (int i = 0; i < board.pieces.length; i++) {
            for (int j = 0; j < board.pieces[i].length; j++) {
                var currentPiece = board.pieces[i][j];
                if (isEnemyPiece(currentPiece, teamColor)) {
                    if (canAttackKing(currentPiece, new ChessPosition(i + 1, j + 1), kingPosition)) {
                        return true; // The king is in check
                    }
                }
            }
        }
        return false; // The king is not in check
    }

    /**
     * Checks if the specified piece is an enemy piece.
     *
     * @param piece     the piece to check
     * @param teamColor the color of the team to compare against
     * @return true if the piece is an enemy piece; false otherwise
     */
    private boolean isEnemyPiece(ChessPiece piece, TeamColor teamColor) {
        return piece != null && piece.getTeamColor() != teamColor;
    }

    /**
     * Determines if the specified piece can attack the king at its position.
     *
     * @param piece         the attacking piece
     * @param piecePosition the position of the attacking piece
     * @param kingPosition  the position of the king
     * @return true if the piece can attack the king; false otherwise
     */
    private boolean canAttackKing(ChessPiece piece, ChessPosition piecePosition, ChessPosition kingPosition) {
        var moves = piece.pieceMoves(board, piecePosition);
        for (var move : moves) {
            if (move.getEndPosition().equals(kingPosition)) {
                return true; // The piece can attack the king
            }
        }
        return false; // The piece cannot attack the king
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        // Determine if the team is at least in check
        if (isInCheck(teamColor)) {
            // Check to see if the team is also in stalemate, meaning they have no more valid moves
            var piecePositions = board.getTeamPiecePositions(teamColor);
            for (var position : piecePositions) {
                if (!validMoves(position).isEmpty()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        // Check if any of the team's pieces have valid moves
        var piecePositions = board.getTeamPiecePositions(teamColor);
        for (var position : piecePositions) {
            // If they do, then the team is not in stalemate
            if (!validMoves(position).isEmpty()) {
                return false;
            }
        }
        // Otherwise, they are in stalemate only if they are not also in check
        return !isInCheck(teamColor);
    }

    // Returns the kings position
    public ChessPosition getKingPosition(TeamColor teamColor) {
        // Look at each of the teams piece positions
        var piecePositions = board.getTeamPiecePositions(teamColor);
        for (var position : piecePositions) {
            var current = board.getPiece(position);
            // Return the team position for the king
            if (current != null && current.getTeamColor() == teamColor && current.getPieceType() == ChessPiece.PieceType.KING) {
                return position;
            }
        }
        return null;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessGame that = (ChessGame) o;
        return board.equals(that.board) && turn == that.turn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(board, turn);
    }

    @Override
    public String toString() {
        return "Game: " + turn + "'s turn, " + board;
    }
}
