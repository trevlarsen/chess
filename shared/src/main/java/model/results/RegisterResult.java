package model.results;

import model.responses.ErrorResponse;
import model.responses.RegisterResponse;

public record RegisterResult(boolean success,
                             int statusCode,
                             ErrorResponse errorMessage,
                             RegisterResponse registerResponse) {
}
