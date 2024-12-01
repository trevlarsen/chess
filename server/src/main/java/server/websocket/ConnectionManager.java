package server.websocket;

import org.eclipse.jetty.websocket.api.Session;
import websocket.messages.*;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ConnectionManager {
    private final ConcurrentHashMap<String, Connection> connections = new ConcurrentHashMap<>();

    public void add(String authToken, Session session, int gameID) {
        connections.put(authToken, new Connection(authToken, session, gameID));
    }

    public void remove(String authToken) {
        connections.remove(authToken);
    }

    public void broadcast(String message, int gameID) throws IOException {
        sendToConnections(message, c -> c.session.isOpen() && c.gameID == gameID);
    }

    public void notifyPlayer(String authToken, String message) throws IOException {
        sendToConnections(message, c -> c.session.isOpen() && c.authToken.equals(authToken));
    }

    public void notifyOthers(String excludeAuthToken, String message, int gameID) throws IOException {
        sendToConnections(message, c -> c.session.isOpen() && !c.authToken.equals(excludeAuthToken) && c.gameID == gameID);
    }

    private void sendToConnections(String message, java.util.function.Predicate<Connection> filter) throws IOException {
        var removeList = connections.values().stream()
                .filter(c -> !c.session.isOpen())
                .collect(Collectors.toList());

        connections.values().stream()
                .filter(filter)
                .forEach(c -> {
                    try {
                        c.send(message);
                    } catch (IOException e) {
                        removeList.add(c); // Mark for removal if send fails
                    }
                });

        // Remove closed or failed connections
        removeList.forEach(c -> connections.remove(c.authToken));
    }
}
