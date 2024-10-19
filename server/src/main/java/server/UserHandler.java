package server;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import model.UserData;
import model.reponses.ErrorResponse;
import model.reponses.UserResponse;
import model.requests.LoginRequest;
import model.results.LoginResult;
import model.results.UserResult;
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


    public Object register(Request request, Response response) {
        UserData userRequest = new Gson().fromJson(request.body(), UserData.class);

        UserResult result = service.register(userRequest);

        response.status(result.statusCode());
        if (result.success()) {
            return new Gson().toJson(result.userResponse());
        } else {
            return new Gson().toJson(result.errorMessage());
        }
    }


    public Object login(Request request, Response response) {
        LoginRequest loginRequest = new Gson().fromJson(request.body(), LoginRequest.class);

        LoginResult result = service.login(loginRequest);

        response.status(result.statusCode());
        if (result.success()) {
            return new Gson().toJson(result.loginResponse());
        } else {
            return new Gson().toJson(result.errorMessage());
        }
    }


    public Object logout(Request request, Response response) {
        response.status(501); // Not implemented
        return new Gson().toJson(new ErrorResponse("Error: logout not implemented"));
    }
}
