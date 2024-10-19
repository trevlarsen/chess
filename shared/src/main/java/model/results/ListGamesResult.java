package model.results;

import model.reponses.ErrorResponse;
import model.reponses.ListGamesResponse;

public record ListGamesResult(boolean success,
                              int statusCode,
                              ErrorResponse errorMessage,
                              ListGamesResponse listGamesResponse) {
}
