package model.results;

import model.AuthData;
import model.reponses.ErrorResponse;

public record LogoutResult(boolean success,
                           int statusCode,
                           ErrorResponse errorMessage) {
}
