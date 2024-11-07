package ui;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import chess.ChessGame;
import com.google.gson.Gson;
import model.AuthData;
import model.GameData;
import model.UserData;
import model.requests.CreateGameRequest;
import model.requests.JoinGameRequest;
import model.requests.LoginRequest;
import model.responses.CreateGameResponse;
import model.responses.ListGamesResponse;
import model.responses.RegisterResponse;
import model.results.ListGamesResult;
import model.results.LoginResult;
import model.results.RegisterResult;

public class ServerFacade {

    private final String serverUrl;

    public ServerFacade(String url) {
        serverUrl = url;
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
        var path = "/game";
        var joinGameRequest = new JoinGameRequest(playerColor, gameID);
        this.makeRequest("PUT", path, joinGameRequest, authToken, null);
    }

    public ArrayList<GameData> listGames(String authToken) throws IOException {
        var path = "/game";
        var listGamesResponse = this.makeRequest("PUT", path, null, authToken, ListGamesResponse.class);
        return listGamesResponse.games();
    }

    public void clear() throws IOException {
        var path = "/db";
        this.makeRequest("DELETE", path, null, null, null);
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
        var status = http.getResponseCode();
        if (!isSuccessful(status)) {
            throw new IOException("failure: " + status);
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
}

