package dataaccess;

import model.UserData;

import java.util.ArrayList;

public class MemoryUserDAO implements UserDAOInterface {

    public static ArrayList<UserData> userDatabase = new ArrayList<>();


    public MemoryUserDAO() {
    }

    @Override
    public void createUser(UserData userData) {
        userDatabase.add(userData);
    }

    @Override
    public UserData getUser(String username) {
        for (var user : userDatabase) {
            if (user.username().equals(username)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void deleteAllUsers() {
        userDatabase.clear();
    }
}


