package service;

import dataaccess.DataAccessException;
import model.AuthData;
import model.UserData;
import model.reponses.ErrorResponse;
import model.reponses.UserResponse;
import model.requests.LoginRequest;
import model.results.LoginResult;
import model.results.UserResult;
import org.eclipse.jetty.server.Authentication;

import java.util.Objects;

import static service.Service.authDataAccess;
import static service.Service.userDataAccess;

public class UserService {

    public UserService() {
    }

    public UserResult register(UserData user) {
        try {
            if (user.username() == null || user.password() == null || user.email() == null) {
                return new UserResult(false, 400, new ErrorResponse("Error: missing fields"), null);
            }

            UserData existingUser = userDataAccess.getUser(user.username());
            if (existingUser != null) {
                return new UserResult(false, 403, new ErrorResponse("Error: username already taken"), null);
            }

            userDataAccess.createUser(user);
            var auth = authDataAccess.newAuth(user.username());
            authDataAccess.createAuth(auth);
            return new UserResult(true, 200, null, new UserResponse(user.username(), auth.authToken()));

        } catch (Exception e) {
            return new UserResult(false, 500, new ErrorResponse("Error: " + e.getMessage()), null);
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
            authDataAccess.createAuth(auth);
            return new LoginResult(true, 200, null, auth);

        } catch (Exception e) {
            return new LoginResult(false, 500, new ErrorResponse("Error: " + e.getMessage()), null);
        }
    }

    public void logout() throws DataAccessException {
        try {
            var i = 9;
        } catch (Exception e) {
            throw new DataAccessException("Error:" + e.getMessage());
        }
    }
}
