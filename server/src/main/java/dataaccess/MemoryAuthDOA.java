package dataaccess;

import model.AuthData;

import java.util.ArrayList;
import java.util.UUID;

public class MemoryAuthDOA implements AuthDOAInterface {

    public static ArrayList<AuthData> authDatabase = new ArrayList<>();

    public MemoryAuthDOA() {
    }

    @Override
    public AuthData newAuth(String username) {
        String authToken = UUID.randomUUID().toString();
        return new AuthData(authToken, username);
    }

    @Override
    public void addAuth(AuthData authData) {
        authDatabase.add(authData);
    }

    @Override
    public AuthData getAuth(String authToken) {
        for (var auth : authDatabase) {
            if (auth.authToken().equals(authToken)) {
                return auth;
            }
        }
        return null;
    }

    @Override
    public void deleteAuth(String authToken) {
        authDatabase.removeIf(auth -> auth.authToken().equals(authToken));
    }

    @Override
    public void deleteAllAuths() {
        authDatabase.clear();
    }
}
