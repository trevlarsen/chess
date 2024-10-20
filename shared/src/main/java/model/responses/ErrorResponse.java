package model.responses;

/**
 * Represents an error response with common pre-defined error messages.
 */
public record ErrorResponse(String message) {

    public static ErrorResponse missingFields() {
        return new ErrorResponse("Error: missing fields");
    }

    public static ErrorResponse unauthorized() {
        return new ErrorResponse("Error: unauthorized");
    }

    public static ErrorResponse empty() {
        return new ErrorResponse("{}");
    }

    public static ErrorResponse internal(String message) {
        return new ErrorResponse("Error: " + message);
    }

    public static ErrorResponse gameNotFound() {
        return new ErrorResponse("Error: game not found");
    }

    public static ErrorResponse colorTaken() {
        return new ErrorResponse("Error: player color already taken");
    }

    public static ErrorResponse usernameTaken() {
        return new ErrorResponse("Error: username already taken");
    }
}
