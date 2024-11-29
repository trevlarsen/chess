package websocket.commands;

import chess.ChessMove;

public class ConnectCommand extends UserGameCommand {
    private final ConnectType connectType;

    public ConnectCommand(ConnectType connectType, String authToken, int gameID, ChessMove move) {
        super(CommandType.CONNECT, authToken, gameID);
        this.connectType = connectType;
    }

    public enum ConnectType {
        PLAYER,
        OBSERVER
    }

    public ConnectType getConnectType() {
        return connectType;
    }
}