package service;

import model.UserData;
import model.responses.ErrorResponse;
import model.responses.RegisterResponse;
import model.requests.LoginRequest;
import model.results.*;

import java.util.Objects;

import static service.BaseService.*;

/**
 * Provides user-related operations such as registration, login, and logout.
 */
public class UserService {

    public UserService() {
    }

    /**
     * Registers a new user with the provided user data.
     *
     * @param user A {@link UserData} object containing the user's information.
     * @return A {@link RegisterResult} representing the outcome of the registration.
     */
    public RegisterResult register(UserData user) {
        try {
            if (ValidationService.inputIsInvalid(user)) {
                return RegisterResult.error(400, ErrorResponse.missingFields());
            }

            UserData existingUser = USER_DAO.getUser(user.username());
            if (existingUser != null) {
                return RegisterResult.error(403, ErrorResponse.usernameTaken());
            }

            USER_DAO.createUser(user);
            var auth = AUTH_DAO.newAuth(user.username());
            AUTH_DAO.addAuth(auth);
            return new RegisterResult(true, 200, ErrorResponse.empty(), new RegisterResponse(user.username(), auth.authToken()));

        } catch (Exception e) {
            return RegisterResult.error(500, ErrorResponse.internal(e.getMessage()));
        }
    }

    /**
     * Logs in a user by verifying the provided credentials.
     *
     * @param loginRequest A {@link LoginRequest} containing the user's username and password.
     * @return A {@link LoginResult} representing the outcome of the login.
     */
    public LoginResult login(LoginRequest loginRequest) {
        try {
            if (ValidationService.inputIsInvalid(loginRequest)) {
                return LoginResult.error(400, ErrorResponse.missingFields());
            }

            UserData existingUser = USER_DAO.getUser(loginRequest.username());

            if (existingUser == null || !Objects.equals(existingUser.password(), loginRequest.password())) {
                return LoginResult.error(401, ErrorResponse.unauthorized());
            }

            var auth = AUTH_DAO.newAuth(loginRequest.username());
            AUTH_DAO.addAuth(auth);
            return new LoginResult(true, 200, ErrorResponse.empty(), auth);

        } catch (Exception e) {
            return LoginResult.error(500, ErrorResponse.internal(e.getMessage()));
        }
    }

    /**
     * Logs out a user by deleting the associated authentication token.
     *
     * @param authToken The token used to authenticate the user.
     * @return A {@link LogoutResult} representing the outcome of the logout.
     */
    public LogoutResult logout(String authToken) {
        try {
            if (ValidationService.inputIsInvalid(authToken)) {
                return LogoutResult.error(400, ErrorResponse.missingFields());
            }
            if (ValidationService.isUnauthorized(authToken)) {
                return LogoutResult.error(401, ErrorResponse.unauthorized());
            }

            AUTH_DAO.deleteAuth(authToken);
            return new LogoutResult(true, 200, ErrorResponse.empty());

        } catch (Exception e) {
            return LogoutResult.error(500, ErrorResponse.internal(e.getMessage()));
        }
    }
}
