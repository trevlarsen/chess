package model.results;

import model.responses.CreateGameResponse;
import model.responses.ErrorResponse;

public record CreateGameResult(boolean success,
                               int statusCode,
                               ErrorResponse errorMessage,
                               CreateGameResponse createGameResponse) {

    public static CreateGameResult error(int statusCode, ErrorResponse errorResponse) {
        return new CreateGameResult(false, statusCode, errorResponse, null);
    }
}
