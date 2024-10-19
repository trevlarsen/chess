package model.results;

import model.AuthData;
import model.reponses.ErrorResponse;

public record LoginResult(boolean success,
                          int statusCode,
                          ErrorResponse errorMessage,
                          AuthData loginResponse) {
}