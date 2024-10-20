package service;

import model.UserData;
import model.responses.ErrorResponse;
import model.responses.RegisterResponse;
import model.requests.LoginRequest;
import model.results.*;

import java.util.Objects;

import static service.Service.*;

public class UserService {

    public UserService() {
    }

    public RegisterResult register(UserData user) {
        try {
            if (user.username() == null || user.password() == null || user.email() == null) {
                return RegisterResult.error(400, ErrorResponse.missingFields());
            }

            UserData existingUser = userDataAccess.getUser(user.username());
            if (existingUser != null) {
                return RegisterResult.error(403, ErrorResponse.usernameTaken());
            }

            userDataAccess.createUser(user);
            var auth = authDataAccess.newAuth(user.username());
            authDataAccess.addAuth(auth);
            return new RegisterResult(true, 200, ErrorResponse.empty(), new RegisterResponse(user.username(), auth.authToken()));

        } catch (Exception e) {
            return RegisterResult.error(500, ErrorResponse.internal(e.getMessage()));
        }
    }

    public LoginResult login(LoginRequest loginRequest) {
        try {
            if (loginRequest.username() == null || loginRequest.password() == null) {
                return LoginResult.error(400, ErrorResponse.missingFields());
            }

            UserData existingUser = userDataAccess.getUser(loginRequest.username());

            if (existingUser == null || !Objects.equals(existingUser.password(), loginRequest.password())) {
                return LoginResult.error(401, ErrorResponse.unauthorized());
            }

            var auth = authDataAccess.newAuth(loginRequest.username());
            authDataAccess.addAuth(auth);
            return new LoginResult(true, 200, ErrorResponse.empty(), auth);

        } catch (Exception e) {
            return LoginResult.error(500, ErrorResponse.internal(e.getMessage()));
        }
    }

    public LogoutResult logout(String authToken) {
        try {
            if (authToken == null || authDataAccess.getAuth(authToken) == null) {
                return LogoutResult.error(401, ErrorResponse.unauthorized());
            }

            authDataAccess.deleteAuth(authToken);
            return new LogoutResult(true, 200, ErrorResponse.empty());

        } catch (Exception e) {
            return LogoutResult.error(500, ErrorResponse.internal(e.getMessage()));
        }
    }
}
