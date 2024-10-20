package model.results;

import model.responses.ErrorResponse;

public record JoinGameResult(boolean success,
                             int statusCode,
                             ErrorResponse errorMessage) {
}
