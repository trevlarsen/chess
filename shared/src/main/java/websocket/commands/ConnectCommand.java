package websocket.commands;

import chess.ChessMove;

public class ConnectCommand extends UserGameCommand {

    public ConnectCommand(String authToken, int gameID) {
        super(CommandType.CONNECT, authToken, gameID);
    }
}