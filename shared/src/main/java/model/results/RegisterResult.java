package model.results;

import model.reponses.ErrorResponse;
import model.reponses.RegisterResponse;

public record RegisterResult(boolean success,
                             int statusCode,
                             ErrorResponse errorMessage,
                             RegisterResponse registerResponse) {
}
