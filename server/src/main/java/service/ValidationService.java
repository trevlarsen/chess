package service;

import org.mindrot.jbcrypt.BCrypt;

import java.lang.reflect.Field;
import java.util.Objects;

import static service.BaseService.AUTH_DAO;
import static service.BaseService.usingSql;

/**
 * A utility service responsible for validating inputs and checking authorization.
 */
public class ValidationService {

    /**
     * Validates the provided input objects by checking for null values
     * and empty strings in their fields. This method uses reflection
     * to access the fields of each input object.
     *
     * @param inputs Varargs of objects to be validated.
     * @return {@code true} if all input objects and their fields are valid (not
     * null and not empty); {@code false} if any input object is null,
     * or if any field in the objects is null or an empty string.
     */
    public static boolean inputIsInvalid(Object... inputs) throws IllegalAccessException {
        for (Object input : inputs) {
            if (input == null) {
                return true;
            }

            if (input instanceof String) {
                if (((String) input).isEmpty()) {
                    return true;
                }
                continue;
            }

            // Check for fields in the object
            for (Field field : input.getClass().getDeclaredFields()) {
                field.setAccessible(true);

                Object fieldValue = field.get(input);
                if (fieldValue == null) {
                    return true;
                }
                // Additional check for empty Strings
                if (fieldValue instanceof String && ((String) fieldValue).isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if the provided authentication token is valid.
     *
     * @param authToken The authentication token to validate.
     * @return {@code true} if the token is unauthorized (not found), {@code false} otherwise.
     */
    public static boolean isUnauthorized(String authToken) {
        return AUTH_DAO.getAuth(authToken) == null;
    }

    public static boolean passwordsMatch(String inputtedPassword, String storedEncryptedPassword) {
        if (usingSql) {
            return BCrypt.checkpw(inputtedPassword, storedEncryptedPassword);
        }
        return Objects.equals(inputtedPassword, storedEncryptedPassword);
    }
}
