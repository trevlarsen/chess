package dataaccess;

import model.AuthData;

/**
 * Interface for managing authentication data access operations.
 * Implementations of this interface should handle the creation, retrieval,
 * and deletion of authentication records.
 */
public interface AuthDOAInterface {

    /**
     * Creates a new authentication token for the given username.
     *
     * @param username the username for which to create an auth token
     * @return the newly created {@link AuthData} containing the auth token
     */
    AuthData newAuth(String username);

    /**
     * Adds the given authentication data to the data store.
     *
     * @param authData the {@link AuthData} object to be added
     */
    void addAuth(AuthData authData);

    /**
     * Retrieves authentication data associated with the given token.
     *
     * @param authToken the authentication token to search for
     * @return the {@link AuthData} if the token is found, or {@code null} if not found
     */
    AuthData getAuth(String authToken);

    /**
     * Deletes the authentication data associated with the given token.
     *
     * @param authToken the authentication token to delete
     */
    void deleteAuth(String authToken);

    /**
     * Deletes all authentication data from the data store.
     */
    void deleteAllAuths();
}

