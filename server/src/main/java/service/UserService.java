package service;

import model.UserData;
import model.reponses.ErrorResponse;
import model.reponses.RegisterResponse;
import model.requests.LoginRequest;
import model.results.LoginResult;
import model.results.LogoutResult;
import model.results.RegisterResult;

import java.util.Objects;

import static service.Service.*;

public class UserService {

    public UserService() {
    }

    public RegisterResult register(UserData user) {
        try {
            if (user.username() == null || user.password() == null || user.email() == null) {
                return new RegisterResult(false, 400, new ErrorResponse("Error: missing fields"), null);
            }

            UserData existingUser = userDataAccess.getUser(user.username());
            if (existingUser != null) {
                return new RegisterResult(false, 403, new ErrorResponse("Error: username already taken"), null);
            }

            userDataAccess.createUser(user);
            var auth = authDataAccess.newAuth(user.username());
            authDataAccess.addAuth(auth);
            return new RegisterResult(true, 200, new ErrorResponse("{}"), new RegisterResponse(user.username(), auth.authToken()));

        } catch (Exception e) {
            return new RegisterResult(false, 500, new ErrorResponse("Error: " + e.getMessage()), null);
        }
    }


    public LoginResult login(LoginRequest loginRequest) {
        try {
            if (loginRequest.username() == null || loginRequest.password() == null) {
                return new LoginResult(false, 400, new ErrorResponse("Error: missing fields"), null);
            }

            UserData existingUser = userDataAccess.getUser(loginRequest.username());

            if (existingUser == null || !Objects.equals(existingUser.password(), loginRequest.password())) {
                return new LoginResult(false, 401, new ErrorResponse("Error: unauthorized"), null);
            }

            var auth = authDataAccess.newAuth(loginRequest.username());
            authDataAccess.addAuth(auth);
            return new LoginResult(true, 200, new ErrorResponse("{}"), auth);

        } catch (Exception e) {
            return new LoginResult(false, 500, new ErrorResponse("Error: " + e.getMessage()), null);
        }
    }

    public LogoutResult logout(String authToken) {
        try {
            if (authToken == null || authDataAccess.getAuth(authToken) == null) {
                return new LogoutResult(false, 401, new ErrorResponse("Error: unauthorized"));
            }

            authDataAccess.deleteAuth(authToken);
            return new LogoutResult(true, 200, new ErrorResponse("{}"));

        } catch (Exception e) {
            return new LogoutResult(false, 500, new ErrorResponse("Error: " + e.getMessage()));
        }
    }
}
