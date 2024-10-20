package model.results;

import model.responses.ErrorResponse;
import model.responses.ListGamesResponse;

public record ListGamesResult(boolean success,
                              int statusCode,
                              ErrorResponse errorMessage,
                              ListGamesResponse listGamesResponse) {
}
