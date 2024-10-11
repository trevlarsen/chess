package dataaccess;

import model.UserData;

public interface UserDOAInterface {
    void createUser(UserData userData); // Adds a user to the database

    UserData getUser(String username); // Find and return UserData based on username, null if doesn't exist

    boolean deleteAllUsers(); // Deletes every user in the database
}
