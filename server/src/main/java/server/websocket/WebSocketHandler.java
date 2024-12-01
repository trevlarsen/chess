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

import static java.lang.Character.getNumericValue;

@WebSocket
public class WebSocketHandler {
    private final ConnectionManager connections = new ConnectionManager();
    private final ValidationService service = new ValidationService();

    public WebSocketHandler() throws DataAccessException {
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws DataAccessException, IOException {
        var command = new Gson().fromJson(message, UserGameCommand.class);
        switch (command.getCommandType()) {
            case CONNECT -> connectPlayer(session, message);
            case MAKE_MOVE -> makeMove(session, message);
            case LEAVE -> leaveGame(session, message);
            case RESIGN -> resignGame(session, message);
        }
    }

    private void connectPlayer(Session session, String message) throws IOException {
        var command = new Gson().fromJson(message, ConnectCommand.class);
        String authToken = command.getAuthToken();
        int gameID = command.getGameID();
        try {
            if (ValidationService.isUnauthorized(authToken) || service.getGame(gameID) == null) {
                connections.add(authToken, session, gameID);
                throw new IOException("Unauthorized");
            }

            String username = service.getUsername(authToken);
            GameData gameData = service.getGame(command.getGameID());

            String type = null;
            if (Objects.equals(gameData.blackUsername(), username)) {
                type = "BLACK";
            } else if (Objects.equals(gameData.whiteUsername(), username)) {
                type = "WHITE";
            } else {
                type = "an OBSERVER";
            }
            var notification = new Notification(username + " has joined as" + type);
            sendGame(gameData, session, authToken);
            connections.notifyOthers(authToken, new Gson().toJson(notification), gameData.gameID());
        } catch (Exception e) {
            error(authToken, e);
        }
    }

    private void sendGame(GameData gameData, Session session, String authToken) throws IOException {
        ChessGame game = gameData.game();
        connections.add(authToken, session, gameData.gameID());

        var loadGame = new LoadGame(new Gson().toJson(game));
        connections.notifyPlayer(authToken, new Gson().toJson(loadGame));
    }

    private void makeMove(Session session, String message) throws IOException, DataAccessException {
//        var command = new Gson().fromJson(message, MakeMove.class);
//        String authToken = command.getAuthString();
//        try {
//            String username = service.getUsername(authToken);
//            GameData gameData = service.getGame(command.getGameID());
//            ChessGame game = gameData.game();
//            ChessGame.TeamColor playerColor = service.checkUserColor(username, gameData);
//            ChessGame.TeamColor pieceColor = game.getBoard().getPiece(command.getMove().startPos).getTeamColor();
//            if (pieceColor != playerColor) {
//                throw new DataAccessException("You can only move your pieces.");
//            }
//            ChessMove move = command.getMove();
//            game.makeMove(move);
//
//            int gameID = gameData.gameID();
//            var gameString = new Gson().toJson(game);
//            service.setGame(gameString, gameID);
//
//            char startCol = (char) (move.startPos.col + 96);
//            char endCol = (char) (move.endPos.col + 96);
//
//            var loadGame = new LoadGame(gameString);
//            connections.notifyOthers(authToken, new Gson().toJson(loadGame), gameID);
//            sendGame(gameData, session, authToken);
//            var notification = new Notification("A move has been made: " + startCol + (9 - move.startPos.row) + " to " + endCol + (9 - move.endPos.row) + ".");
//            connections.notifyOthers(authToken, new Gson().toJson(notification), gameData.gameID());
//
//            boolean checkIfCheckmate = false;
//            if (game.isInCheckmate(ChessGame.TeamColor.WHITE)) {
//                var checkMateWhite = new Notification("WHITE is in checkmate!");
//                connections.broadcast(new Gson().toJson(checkMateWhite), gameID);
//                checkIfCheckmate = true;
//            }
//            if (game.isInCheckmate(ChessGame.TeamColor.BLACK)) {
//                var checkMateBlack = new Notification("BLACK is in checkmate!");
//                connections.broadcast(new Gson().toJson(checkMateBlack), gameID);
//                checkIfCheckmate = true;
//            }
//            if (!checkIfCheckmate) {
//                if (game.isInCheck(ChessGame.TeamColor.WHITE)) {
//                    var checkWhite = new Notification("WHITE is in check!");
//                    connections.broadcast(new Gson().toJson(checkWhite), gameID);
//                }
//                if (game.isInCheck(ChessGame.TeamColor.BLACK)) {
//                    var checkBlack = new Notification("WHITE is in check!");
//                    connections.broadcast(new Gson().toJson(checkBlack), gameID);
//                }
//            }
//
//
//        } catch (Exception e) {
//            error(authToken, e);
//        }
    }

    public void leaveGame(Session session, String message) throws IOException {
//        var command = new Gson().fromJson(message, Leave.class);
//        String authToken = command.getAuthString();
//        try {
//            String username = service.getUsername(authToken);
//            connections.remove(authToken);
//            var notification = new Notification(username + " has left the game.");
//            connections.notifyOthers(authToken, new Gson().toJson(notification), command.getGameID());
//        } catch (Exception e) {
//            error(authToken, e);
//        }
    }

    public void resignGame(Session session, String message) throws IOException {
//        var command = new Gson().fromJson(message, Resign.class);
//        String authToken = command.getAuthString();
//        try {
//            String username = service.getUsername(authToken);
//            service.setGameResign(command.getGameID(), username);
//            var notification = new Notification(username + " has resigned.");
//            connections.broadcast(new Gson().toJson(notification), command.getGameID());
//            connections.remove(authToken);
//        } catch (Exception e) {
//            error(authToken, e);
//        }
    }

    public void error(String authToken, Exception exception) throws IOException {
        var error = new ErrorMessage("Error: " + exception.toString());
        connections.notifyPlayer(authToken, new Gson().toJson(error));
    }

}