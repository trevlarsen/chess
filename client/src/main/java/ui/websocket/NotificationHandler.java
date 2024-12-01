package ui.websocket;

import websocket.messages.*;

public interface NotificationHandler {
    void notify(NotificationMessage message);

    void loadGame(LoadGameMessage loadGameMessage);

    void error(ErrorMessage error);
}