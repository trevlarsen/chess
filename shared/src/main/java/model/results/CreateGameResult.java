package model.results;

import model.reponses.CreateGameResponse;
import model.reponses.ErrorResponse;

public record CreateGameResult(boolean success,
                               int statusCode,
                               ErrorResponse errorMessage,
                               CreateGameResponse createGameResponse) {
}
