package model.results;

import model.responses.ErrorResponse;
import model.responses.RegisterResponse;

public record RegisterResult(boolean success,
                             int statusCode,
                             ErrorResponse errorMessage,
                             RegisterResponse registerResponse) {

    public static RegisterResult error(int statusCode, ErrorResponse errorResponse) {
        return new RegisterResult(false, statusCode, errorResponse, null);
    }
}
