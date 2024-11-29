package ui.websocket;

import com.google.gson.Gson;
//import exception.ResponseException;
import ui.websocket.NotificationHandler;
//import webSocketMessages.Action;
import websocket.messages.*;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

//need to extend Endpoint for websocket to work properly
public class WebSocketFacade extends Endpoint {

    Session session;
    NotificationHandler notificationHandler;


    public WebSocketFacade(String url, NotificationHandler notificationHandler) throws IOException {
        try {
            url = url.replace("http", "ws");
            URI socketURI = new URI(url + "/connect");
            this.notificationHandler = notificationHandler;

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);

            //set message handler
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

    public void loadGame(String serverMessage) {
        LoadGameMessage loadGame = new Gson().fromJson(serverMessage, LoadGameMessage.class);
        notificationHandler.loadGame(loadGame);
    }

    public void error(String serverMessage) {
        ErrorMessage error = new Gson().fromJson(serverMessage, ErrorMessage.class);
        notificationHandler.error(error);
    }

    public void notification(String serverMessage) {
        Notification notification = new Gson().fromJson(serverMessage, Notification.class);
        notificationHandler.notify(notification);
    }


}