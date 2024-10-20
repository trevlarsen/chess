package dataaccess;

import model.UserData;

/**
 * Interface for managing user data access operations.
 * Implementations of this interface handle the creation, retrieval, and deletion of user records.
 */
public interface UserDOAInterface {

    /**
     * Creates a new user in the data store.
     *
     * @param userData the {@link UserData} object containing the user's information
     */
    void createUser(UserData userData);

    /**
     * Retrieves the user associated with the given username.
     *
     * @param username the username of the user to retrieve
     * @return the {@link UserData} object if the user exists, or {@code null} if not found
     */
    UserData getUser(String username);

    /**
     * Deletes all users from the data store.
     */
    void deleteAllUsers();
}
