package websocket.messages;

public class NotificationMessage extends ServerMessage {
    String message;

    public NotificationMessage(String message) {
        super(ServerMessageType.NOTIFICATION);
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "message='" + message +
                '}';
    }
}