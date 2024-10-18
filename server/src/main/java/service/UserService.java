package service;

import dataaccess.DataAccessException;

public class UserService {

    public UserService() {}

    public String register() throws DataAccessException {
        try {

        } catch (Exception e) {
            throw new DataAccessException("Error:" + e.getMessage());
        }
    }

    public void login() throws DataAccessException {
        try {

        } catch (Exception e) {
            throw new DataAccessException("Error:" + e.getMessage());
        }
    }

    public void logout() throws DataAccessException {
        try {

        } catch (Exception e) {
            throw new DataAccessException("Error:" + e.getMessage());
        }
    }
}
