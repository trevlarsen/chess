package ui;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import chess.ChessGame;
import chess.ChessMove;
import com.google.gson.Gson;
import model.AuthData;
import model.GameData;
import model.UserData;
import model.requests.*;
import model.responses.*;
import ui.websocket.NotificationHandler;
import ui.websocket.WebSocketFacade;

import static chess.ChessGame.TeamColor.BLACK;
import static chess.ChessGame.TeamColor.WHITE;

public class ServerFacade {

    private final String serverUrl;
    private final WebSocketFacade ws;

    public ServerFacade(String url, NotificationHandler notificationHandler, boolean testing) throws IOException {
        serverUrl = url;
//        if (testing) {
//            ws = null;
//        } else {
//            ws = new WebSocketFacade(url, notificationHandler);
//        }
        ws = new WebSocketFacade(url, notificationHandler);
    }

    public RegisterResponse register(String username, String password, String email) throws IOException {
        var path = "/user";
        var userData = new UserData(username, password, email);
        return this.makeRequest("POST", path, userData, null, RegisterResponse.class);
    }

    public AuthData login(String username, String password) throws IOException {
        var path = "/session";
        var loginRequest = new LoginRequest(username, password);
        return this.makeRequest("POST", path, loginRequest, null, AuthData.class);
    }

    public void logout(String authToken) throws IOException {
        var path = "/session";
        this.makeRequest("DELETE", path, null, authToken, null);
    }

    public int createGame(String gameName, String authToken) throws IOException {
        var path = "/game";
        var createGameRequest = new CreateGameRequest(gameName);
        var createGameResponse = this.makeRequest("POST", path, createGameRequest, authToken, CreateGameResponse.class);
        return createGameResponse.gameID();
    }

    public void joinGame(ChessGame.TeamColor playerColor, Integer gameID, String authToken) throws IOException {
        try {
            var path = "/game";
            var joinGameRequest = new JoinGameRequest(playerColor, gameID);
            this.makeRequest("PUT", path, joinGameRequest, authToken, null);
            ws.connectPlayer(authToken, gameID);
        } catch (Exception e) {
            ws.connectPlayer(authToken, gameID);
        }
    }

    public void observeGame(Integer gameID, String authToken) throws IOException {
        ws.connectPlayer(authToken, gameID);
    }

    public ArrayList<GameData> listGames(String authToken) throws IOException {
        var path = "/game";
        var listGamesResponse = this.makeRequest("GET", path, null, authToken, ListGamesResponse.class);
        return listGamesResponse.games();
    }

    public void clear() throws IOException {
        var path = "/db";
        this.makeRequest("DELETE", path, null, null, null);
    }

    public void makeMove(String authToken, int gameID, ChessMove move) throws IOException {
//        ws.makeMove(authToken, gameID, move);
    }

    public void leaveGame(String authToken, int gameID) throws IOException {
//        ws.leaveGame(authToken, gameID);
    }

    public void resignGame(String authToken, int gameID) throws IOException {
//        ws.resignGame(authToken, gameID);
    }

    private <T> T makeRequest(String method, String path, Object request, String authToken, Class<T> responseClass) throws IOException {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);

            writeBody(request, http, authToken);
            http.connect();
            throwIfNotSuccessful(http);
            return readBody(http, responseClass);
        } catch (Exception ex) {
            throw new IOException(ex.getMessage());
        }
    }

    private static void writeBody(Object request, HttpURLConnection http, String authToken) throws IOException {
        if (authToken != null) {
            http.setRequestProperty("authorization", authToken);
        }
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
    }

    private void throwIfNotSuccessful(HttpURLConnection http) throws IOException {
        int status = http.getResponseCode();
        if (!isSuccessful(status)) {
            // Read the error body from the server's response
            ErrorResponse errorResponse = readErrorStream(http, ErrorResponse.class);

            // If there's an error message, include it in the exception
            String errorMessage = (errorResponse != null && errorResponse.message() != null)
                    ? errorResponse.message()
                    : "Unknown error occurred";

            // Throw an IOException with the error message
            throw new IOException(errorMessage);
        }
    }


    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
                    response = new Gson().fromJson(reader, responseClass);
                }
            }
        }
        return response;
    }

    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }

    private <T> T readErrorStream(HttpURLConnection http, Class<T> responseClass) throws IOException {
        InputStream errorStream = http.getErrorStream();  // Get the error stream
        if (errorStream == null) {
            return null;  // If no error body is provided, return null
        }

        try (InputStreamReader reader = new InputStreamReader(errorStream);
             BufferedReader bufferedReader = new BufferedReader(reader)) {

            // Read the entire error stream into a single string
            StringBuilder errorMessage = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                errorMessage.append(line);
            }

            // Deserialize the error message into the provided response class
            return new Gson().fromJson(errorMessage.toString(), responseClass);
        }
    }

}

