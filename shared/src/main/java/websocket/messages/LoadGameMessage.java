package websocket.messages;

public class LoadGameMessage extends ServerMessage {
    public String game;

    public LoadGameMessage(String game) {
        super(ServerMessageType.LOAD_GAME);
        this.game = game;
    }

    @Override
    public String toString() {
        return "LoadGameMessage{" +
                "game='" + game +
                '}';
    }
}