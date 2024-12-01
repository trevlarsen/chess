package ui.websocket;

import websocket.messages.*;
import websocket.messages.Error;

public interface NotificationHandler {
    void notify(Notification message);

    void loadGame(LoadGame loadGameMessage);

    void error(ErrorMessage error);
}