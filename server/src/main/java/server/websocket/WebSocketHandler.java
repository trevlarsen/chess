package server.websocket;

import chess.ChessGame;
import chess.ChessMove;
import com.google.gson.Gson;
import dataaccess.DataAccessException;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import service.*;
import websocket.messages.*;
import websocket.commands.*;

import java.io.IOException;
import java.util.Objects;

@WebSocket
public class WebSocketHandler {
    private final ConnectionManager connections = new ConnectionManager();
    private final ValidationService service = new ValidationService();

    public WebSocketHandler() {
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        var command = new Gson().fromJson(message, UserGameCommand.class);
        switch (command.getCommandType()) {
            case CONNECT -> connectPlayer(session, message);
            case MAKE_MOVE -> makeMove(session, message);
            case LEAVE -> leaveGame(message);
            case RESIGN -> resignGame(message);
        }
    }

    // Connect Player methods
    private void connectPlayer(Session session, String message) throws IOException {
        var command = new Gson().fromJson(message, ConnectCommand.class);
        String authToken = command.getAuthToken();
        int gameID = command.getGameID();

        try {
            // Validate authorization and game existence
            if (ValidationService.isUnauthorized(authToken) || service.getGame(gameID) == null) {
                connections.add(authToken, session, gameID);
                throw new IOException("Unauthorized");
            }

            String username = service.getUsername(authToken);
            GameData gameData = service.getGame(gameID);

            // Determine player type
            String type = determinePlayerType(username, gameData);
            var notification = new NotificationMessage(username + " has joined as " + type);

            // Notify and send game data
            sendGame(gameData, session, authToken);
            connections.notifyOthers(authToken, new Gson().toJson(notification), gameID);
        } catch (Exception e) {
            error(authToken, e);
        }
    }

    private String determinePlayerType(String username, GameData gameData) {
        if (Objects.equals(gameData.blackUsername(), username)) {
            return "BLACK";
        } else if (Objects.equals(gameData.whiteUsername(), username)) {
            return "WHITE";
        } else {
            return "an OBSERVER";
        }
    }

    private void sendGame(GameData gameData, Session session, String authToken) throws IOException {
        ChessGame game = gameData.game();
        connections.add(authToken, session, gameData.gameID());

        var loadGame = new LoadGameMessage(new Gson().toJson(game));
        connections.notifyPlayer(authToken, new Gson().toJson(loadGame));
    }

    // Make Move methods
    private void makeMove(Session session, String message) throws IOException {
        var command = new Gson().fromJson(message, MakeMoveCommand.class);
        String authToken = command.getAuthToken();
        int gameID = command.getGameID();

        try {
            validateAuthAndGame(authToken, session, gameID);

            String username = service.getUsername(authToken);
            GameData gameData = service.getGame(gameID);
            ChessGame game = gameData.game();
            if (game.isResigned()) {
                throw new IOException("Game is resigned. No moves allowed.");
            }

            validatePlayerMove(username, gameData, game, command.getMove());

            ChessMove move = command.getMove();
            game.makeMove(move);

            updateGameState(authToken, game, gameID, gameData, session);

            sendMoveNotification(authToken, gameID, move);

            handleCheckAndCheckmate(game, gameID);
        } catch (Exception e) {
            error(authToken, e);
        }
    }

    private void validateAuthAndGame(String authToken, Session session, int gameID) throws IOException {
        if (ValidationService.isUnauthorized(authToken) || service.getGame(gameID) == null) {
            connections.add(authToken, session, gameID);
            throw new IOException("Unauthorized");
        }
    }

    private void validatePlayerMove(String username,
                                    GameData gameData,
                                    ChessGame game,
                                    ChessMove move) throws DataAccessException {
        ChessGame.TeamColor playerColor = service.checkUserColor(username, gameData);
        ChessGame.TeamColor pieceColor = game.getBoard().getPiece(move.getStartPosition()).getTeamColor();
        if (pieceColor != playerColor) {
            throw new DataAccessException("You can only move your pieces.");
        }
    }

    private void updateGameState(String authToken,
                                 ChessGame game,
                                 int gameID,
                                 GameData gameData,
                                 Session session) throws IOException, DataAccessException {
        String gameString = new Gson().toJson(game);
        service.setGame(game, gameID);

        var loadGame = new LoadGameMessage(gameString);
        connections.notifyOthers(authToken, new Gson().toJson(loadGame), gameID);
        sendGame(gameData, session, authToken);
    }

    private void sendMoveNotification(String authToken, int gameID, ChessMove move) throws IOException {
        char startCol = (char) (move.getStartPosition().getColumn() + 96);
        char endCol = (char) (move.getEndPosition().getColumn() + 96);

        String moveDescription = startCol + (9 - move.getStartPosition().getRow()) +
                " to " + endCol + (9 - move.getEndPosition().getRow());
        var notification = new NotificationMessage("A move has been made: " + moveDescription + ".");
        connections.notifyOthers(authToken, new Gson().toJson(notification), gameID);
    }

    private void handleCheckAndCheckmate(ChessGame game, int gameID) throws IOException {
        boolean isCheckmate = false;

        if (game.isInCheckmate(ChessGame.TeamColor.WHITE)) {
            broadcastNotification("WHITE is in checkmate", gameID);
            isCheckmate = true;
        }
        if (game.isInCheckmate(ChessGame.TeamColor.BLACK)) {
            broadcastNotification("BLACK is in checkmate", gameID);
            isCheckmate = true;
        }

        if (!isCheckmate) {
            if (game.isInCheck(ChessGame.TeamColor.WHITE)) {
                broadcastNotification("WHITE is in check", gameID);
            }
            if (game.isInCheck(ChessGame.TeamColor.BLACK)) {
                broadcastNotification("BLACK is in check", gameID);
            }
        }
    }

    private void broadcastNotification(String message, int gameID) throws IOException {
        var notification = new NotificationMessage(message);
        connections.broadcast(new Gson().toJson(notification), gameID);
    }


    // Leave Game methods
    public void leaveGame(String message) throws IOException {
        var command = new Gson().fromJson(message, LeaveCommand.class);
        String authToken = command.getAuthToken();
        try {
            String username = service.getUsername(authToken);
            connections.remove(authToken);

            int gameID = command.getGameID();
            service.setGameUsername(gameID, username);

            var notification = new NotificationMessage(username + " has left the game.");
            connections.notifyOthers(authToken, new Gson().toJson(notification), command.getGameID());
        } catch (Exception e) {
            error(authToken, e);
        }
    }

    public void resignGame(String message) throws IOException {
        var command = new Gson().fromJson(message, ResignCommand.class);
        String authToken = command.getAuthToken();
        try {
            String username = service.getUsername(authToken);
            service.setGameResign(command.getGameID(), username);
            var notification = new NotificationMessage(username + " has resigned.");
            connections.broadcast(new Gson().toJson(notification), command.getGameID());
            connections.remove(authToken);
        } catch (Exception e) {
            error(authToken, e);
        }
    }

    public void error(String authToken, Exception exception) throws IOException {
        var error = new ErrorMessage("Error: " + exception.toString());
        connections.notifyPlayer(authToken, new Gson().toJson(error));
        connections.remove(authToken);
    }

}