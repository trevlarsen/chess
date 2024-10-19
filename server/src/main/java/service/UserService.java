package service;

import dataaccess.DataAccessException;
import model.AuthData;
import model.UserData;
import model.reponses.ErrorResponse;
import model.reponses.UserResponse;
import model.results.UserResult;
import org.eclipse.jetty.server.Authentication;

import static service.Service.authDataAccess;
import static service.Service.userDataAccess;

public class UserService {

    public UserService() {
    }

    public UserResult register(UserData user) {
        // Validate input fields (username, password, email)
        if (user.username() == null || user.password() == null || user.email() == null) {
            return new UserResult(false, 400, new ErrorResponse("Error: missing fields"), null);
        }

        try {
            // Check if the user already exists
            UserData existingUser = userDataAccess.getUser(user.username());
            if (existingUser != null) {
                return new UserResult(false, 403, new ErrorResponse("Error: username already taken"), null);
            }

            // Register the new user and generate an auth token
            userDataAccess.createUser(user);
            var auth = authDataAccess.newAuth(user.username());
            authDataAccess.createAuth(auth);

            // Return a success response with the username and token
            return new UserResult(true, 200, null, new UserResponse(user.username(), auth.authToken()));

        } catch (Exception e) {
            // Handle any unexpected errors
            return new UserResult(false, 500, new ErrorResponse("Error: " + e.getMessage()), null);
        }
    }


    public void login() throws DataAccessException {
        try {
            var i = 9;
        } catch (Exception e) {
            throw new DataAccessException("Error:" + e.getMessage());
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
