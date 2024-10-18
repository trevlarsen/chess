package service;

import dataaccess.DataAccessException;
import model.AuthData;
import model.UserData;
import org.eclipse.jetty.server.Authentication;

import static service.Service.authDataAccess;
import static service.Service.userDataAccess;

public class UserService {

    public UserService() {}

    public String register(UserData user) throws DataAccessException {
        try {
            UserData currentUserData = userDataAccess.getUser(user.username());
            if (currentUserData == null) {
                userDataAccess.createUser(user);
                var auth = authDataAccess.newAuth(user.username());
                authDataAccess.createAuth(auth);
                return auth.authToken();
            } else {
                throw new DataAccessException("Error: username already taken");
            }
        } catch (Exception e) {
            throw new DataAccessException("Error:" + e.getMessage());
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
