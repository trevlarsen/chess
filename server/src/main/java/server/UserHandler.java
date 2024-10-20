package server;

import com.google.gson.Gson;
import model.UserData;
import model.responses.EmptyResponse;
import model.requests.LoginRequest;
import model.results.LoginResult;
import model.results.LogoutResult;
import model.results.RegisterResult;
import service.UserService;
import spark.Request;
import spark.Response;

import static server.BaseHandler.handleResponse;

public class UserHandler {

    private final UserService service = new UserService();
    private static final Gson GSON = new Gson();
    private static volatile UserHandler instance;

    private UserHandler() {
    }

    /**
     * Provides a singleton instance of UserHandler.
     *
     * @return the single instance of UserHandler
     */
    public static UserHandler getInstance() {
        if (instance == null) {
            synchronized (UserHandler.class) {
                if (instance == null) {
                    instance = new UserHandler();
                }
            }
        }
        return instance;
    }

    /**
     * Handles the registration of a new user.
     *
     * @param request  the HTTP request containing user data
     * @param response the HTTP response object
     * @return JSON response indicating success or failure
     */
    public Object register(Request request, Response response) {
        UserData userRequest = GSON.fromJson(request.body(), UserData.class);
        RegisterResult result = service.register(userRequest);
        return handleResponse(response, result.statusCode(), result.success(), result.registerResponse(), result.errorMessage());
    }

    /**
     * Handles user login requests.
     *
     * @param request  the HTTP request containing login credentials
     * @param response the HTTP response object
     * @return JSON response indicating success or failure
     */
    public Object login(Request request, Response response) {
        LoginRequest loginRequest = GSON.fromJson(request.body(), LoginRequest.class);
        LoginResult result = service.login(loginRequest);
        return handleResponse(response, result.statusCode(), result.success(), result.loginResponse(), result.errorMessage());
    }

    /**
     * Handles user logout requests.
     *
     * @param request  the HTTP request containing the authorization token
     * @param response the HTTP response object
     * @return JSON response indicating success or failure
     */
    public Object logout(Request request, Response response) {
        String authToken = request.headers("authorization");
        LogoutResult result = service.logout(authToken);
        return handleResponse(response, result.statusCode(), result.success(), new EmptyResponse(), result.errorMessage());
    }
}
