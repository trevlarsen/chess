package ui.websocket;

import websocket.messages.*;

import java.io.IOException;

public interface NotificationHandler {
    void notify(NotificationMessage message) throws IOException;

    void loadGame(LoadGameMessage loadGameMessage) throws IOException;

    void error(ErrorMessage error) throws IOException;
}