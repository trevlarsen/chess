package server;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import model.UserData;
import service.UserService;
import spark.Request;
import spark.Response;

public class UserHandler {

    private final UserService service = new UserService();
    private static UserHandler instance;

    public UserHandler() throws DataAccessException {
    }

    public static UserHandler getInstance() throws DataAccessException {
        if (instance == null) {
            instance = new UserHandler(); // Ensure the instance is assigned here
        }
        return instance;
    }

    public Object register(Request req, Response res) {
        UserData userReq = new Gson().fromJson(req.body(), UserData.class);

        if (userReq.username() == null || userReq.password() == null || userReq.email() == null) {
            res.status(400);
            return new Gson().toJson(new FailureResult("Error: bad request"));
        }

        String authToken;
        try {
            authToken = service.register(userReq);
        } catch (DataAccessException e) {
            res.status(403);
            return new Gson().toJson(new FailureResult("Error: username or email already taken"));
        }

        res.status(200); // Set success status
        return new Gson().toJson(new UserResult(userReq.username(), authToken));
    }

    public Object login(Request req, Response res) {
        // Implement login logic here
        res.status(501); // Not implemented
        return new Gson().toJson(new FailureResult("Error: login not implemented"));
    }

    public Object logout(Request req, Response res) {
        // Implement logout logic here
        res.status(501); // Not implemented
        return new Gson().toJson(new FailureResult("Error: logout not implemented"));
    }

    // Inner classes for consistent JSON responses
    private static class FailureResult {
        private final String message;

        public FailureResult(String message) {
            this.message = message;
        }
    }

    private static class UserResult {
        private final String username;
        private final String authToken;

        public UserResult(String username, String authToken) {
            this.username = username;
            this.authToken = authToken;
        }
    }
}
