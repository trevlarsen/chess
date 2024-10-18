import chess.*;
import dataaccess.DataAccessException;
import server.Server;

public class Main {
    public static void main(String[] args) throws DataAccessException {
//        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        var server = new Server();
        var port = server.run(8080);
        System.out.println("♕ 240 Chess Server started on port" + port);
    }
}