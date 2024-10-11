package dataaccess;

import model.UserData;

import java.util.ArrayList;

public class MemoryUserDOA implements UserDOAInterface {

    private static ArrayList<UserData> userDatabase = new ArrayList<>();


    public MemoryUserDOA() {}


    // Adds a user to the database
    @Override
    public void createUser(UserData userData) {

    }

    // Find and return UserData based on username, null if doesn't exist
    @Override
    public UserData getUser(String username) {
        return null;
    }

    // Deletes every user in the database
    @Override
    public boolean deleteAllUsers() {
        return false;
    }
}
