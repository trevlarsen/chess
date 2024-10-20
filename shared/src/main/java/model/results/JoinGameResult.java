package model.results;

import model.responses.ErrorResponse;

public record JoinGameResult(boolean success,
                             int statusCode,
                             ErrorResponse errorMessage) {

    public static JoinGameResult error(int statusCode, ErrorResponse errorResponse) {
        return new JoinGameResult(false, statusCode, errorResponse);
    }
}
