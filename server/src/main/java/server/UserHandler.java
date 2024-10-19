package server;

import com.google.gson.Gson;
import model.UserData;
import model.reponses.EmptyResponse;
import model.requests.LoginRequest;
import model.results.LoginResult;
import model.results.LogoutResult;
import model.results.RegisterResult;
import service.UserService;
import spark.Request;
import spark.Response;

public class UserHandler {

    private final UserService service = new UserService();
    private static UserHandler instance;


    public UserHandler() {
    }


    public static UserHandler getInstance() {
        if (instance == null) {
            instance = new UserHandler(); // Ensure the instance is assigned here
        }
        return instance;
    }


    public Object register(Request request, Response response) {
        UserData userRequest = new Gson().fromJson(request.body(), UserData.class);

        RegisterResult result = service.register(userRequest);

        response.status(result.statusCode());
        if (result.success()) {
            return new Gson().toJson(result.registerResponse());
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
        String authToken = request.headers("authorization");

        LogoutResult result = service.logout(authToken);

        response.status(result.statusCode());
        if (result.success()) {
            return new Gson().toJson(new EmptyResponse());
        } else {
            return new Gson().toJson(result.errorMessage());
        }
    }
}
