package ui.websocket;

import chess.ChessMove;
import com.google.gson.Gson;
import websocket.commands.*;
import websocket.messages.*;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketFacade extends Endpoint {

    Session session;
    NotificationHandler notificationHandler;


    public WebSocketFacade(String url, NotificationHandler notificationHandler) throws IOException {
        try {
            url = url.replace("http", "ws");
            URI socketURI = new URI(url + "/ws");
            this.notificationHandler = notificationHandler;

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);

            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    ServerMessage serverMessage = new Gson().fromJson(message, ServerMessage.class);
                    switch (serverMessage.getServerMessageType()) {
                        case LOAD_GAME -> loadGame(message);
                        case ERROR -> error(message);
                        case NOTIFICATION -> notification(message);
                    }
                }
            });
        } catch (DeploymentException | IOException | URISyntaxException ex) {
            throw new IOException(ex.getMessage());
        }
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }

    // Receiving functions
    public void loadGame(String serverMessage) {
        handleMessage(serverMessage, LoadGame.class, notificationHandler::loadGame);
    }

    public void error(String serverMessage) {
        handleMessage(serverMessage, ErrorMessage.class, notificationHandler::error);
    }

    public void notification(String serverMessage) {
        handleMessage(serverMessage, Notification.class, notificationHandler::notify);
    }

    // Sending functions
    public void sendCommand(Object command) throws IOException {
        this.session.getBasicRemote().sendText(new Gson().toJson(command));
    }

    public void connectPlayer(String authToken, int gameID) throws IOException {
        sendCommand(new ConnectCommand(authToken, gameID));
    }

    public void makeMove(String authToken, int gameID, ChessMove move) throws IOException {
        sendCommand(new MakeMove(authToken, gameID, move));
    }

    public void resignGame(String authToken, int gameID) throws IOException {
        sendCommand(new Resign(authToken, gameID));
    }

    public void leaveGame(String authToken, int gameID) throws IOException {
        sendCommand(new Leave(authToken, gameID));
    }

    // Helper methods
    private <T> void handleMessage(String serverMessage, Class<T> clazz, java.util.function.Consumer<T> handler) {
        T message = new Gson().fromJson(serverMessage, clazz);
        handler.accept(message);
    }


}