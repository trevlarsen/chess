package model.results;

import model.reponses.ErrorResponse;
import model.reponses.UserResponse;

public record UserResult(boolean success,
                         int statusCode,
                         ErrorResponse errorMessage,
                         UserResponse userResponse) {
}
