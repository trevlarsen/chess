package model.results;

import model.AuthData;
import model.responses.ErrorResponse;

public record LoginResult(boolean success,
                          int statusCode,
                          ErrorResponse errorMessage,
                          AuthData loginResponse) {

    public static LoginResult error(int statusCode, ErrorResponse errorResponse) {
        return new LoginResult(false, statusCode, errorResponse, null);
    }
}