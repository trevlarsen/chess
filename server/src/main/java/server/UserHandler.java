package server;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import model.UserData;
import model.reponses.ErrorResponse;
import model.reponses.UserResponse;
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
        // Parse the request body into a UserData object
        UserData userRequest = new Gson().fromJson(request.body(), UserData.class);

        // Call the service to register the user
        UserResult result = service.register(userRequest);

        response.status(result.statusCode());
        // Map the result to an appropriate HTTP response
        if (result.success()) {
            // Success
            return new Gson().toJson(result.userResponse());
        } else {
            // Bad Request or Username Already Taken
            return new Gson().toJson(result.errorMessage());
        }
    }


    public Object login(Request request, Response response) {
        // Implement login logic here
        response.status(501); // Not implemented
        return new Gson().toJson(new ErrorResponse("Error: login not implemented"));
    }


    public Object logout(Request request, Response response) {
        // Implement logout logic here
        response.status(501); // Not implemented
        return new Gson().toJson(new ErrorResponse("Error: logout not implemented"));
    }
}
