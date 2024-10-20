package model.results;

import model.responses.ErrorResponse;

public record LogoutResult(boolean success,
                           int statusCode,
                           ErrorResponse errorMessage) {

    public static LogoutResult error(int statusCode, ErrorResponse errorResponse) {
        return new LogoutResult(false, statusCode, errorResponse);
    }
}
