package dataaccess;

import model.AuthData;

import java.util.ArrayList;

public class MemoryAuthDOA implements AuthDOAInterface{

    private static ArrayList<AuthData> authDatabase = new ArrayList<>();


    public MemoryAuthDOA() {}


    // Adds an AuthData to the database
    @Override
    public void createAuth(AuthData authData) {
        authDatabase.add(authData);
    }

    // Find and return AuthData based on an auth token
    @Override
    public AuthData getAuth(String authToken) {
        for (var auth : authDatabase) {
            if (auth.authToken().equals(authToken)) {
                return auth;
            }
        }
        return null;
    }

    // Deletes a AuthData given an auth token
    @Override
    public void deleteAuth(String authToken) {
        authDatabase.removeIf(auth -> auth.authToken().equals(authToken));

    }

    // Deletes every AuthData in the database
    @Override
    public boolean deleteAllAuths() {
        authDatabase.clear();
        return true;
    }
}
