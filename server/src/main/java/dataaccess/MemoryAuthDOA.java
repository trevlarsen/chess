package dataaccess;

import model.AuthData;

import java.util.ArrayList;

public class MemoryAuthDOA implements AuthDOAInterface{

    private static ArrayList<AuthData> authDatabase = new ArrayList<>();


    public MemoryAuthDOA() {}


    // Adds an AuthData to the database
    @Override
    public void createAuth(AuthData authData) {

    };

    // Find and return AuthData based on an auth token
    @Override
    public AuthData getAuth(String authToken) {
        return null;
    };

    // Deletes a AuthData given an auth token
    @Override
    public void deleteAuth(String authToken) {

    };

    // Deletes every AuthData in the database
    @Override
    public boolean deleteAllAuths() {
        return false;
    };
}
