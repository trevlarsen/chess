package model.results;

import model.responses.ErrorResponse;

public record LogoutResult(boolean success,
                           int statusCode,
                           ErrorResponse errorMessage) {
}
