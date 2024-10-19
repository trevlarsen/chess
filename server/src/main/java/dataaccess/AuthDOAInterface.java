package dataaccess;

import model.AuthData;

public interface AuthDOAInterface {
    AuthData newAuth(String username); // Creates a new AuthData for a user

    void addAuth(AuthData authData); // Adds an AuthData to the database

    AuthData getAuth(String authToken); // Find and return AuthData based on an auth token

    void deleteAuth(String authToken); // Deletes a AuthData given an auth token

    void deleteAllAuths(); // Deletes every AuthData in the database
}
