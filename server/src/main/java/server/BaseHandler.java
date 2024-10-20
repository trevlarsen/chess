package server;

import com.google.gson.Gson;
import model.responses.EmptyResponse;
import model.responses.ErrorResponse;
import service.BaseService;
import spark.Response;

public class BaseHandler {

    private final BaseService baseService = new BaseService();
    private static volatile BaseHandler instance;
    private static final Gson GSON = new Gson();

    private BaseHandler() {
    }

    /**
     * Provides a singleton instance of BaseHandler.
     *
     * @return the single instance of BaseHandler
     */
    public static BaseHandler getInstance() {
        if (instance == null) {
            synchronized (BaseHandler.class) {
                if (instance == null) {
                    instance = new BaseHandler();
                }
            }
        }
        return instance;
    }

    /**
     * Clears all data from the base service.
     *
     * @param response the HTTP response object
     * @return JSON response indicating the outcome of the clear operation
     */
    public Object clear(Response response) {
        try {
            baseService.clear();
            return handleResponse(response, 200, true, new EmptyResponse(), null);
        } catch (Exception e) {
            return handleResponse(response, 500, false, null, new ErrorResponse("Error: " + e.getMessage()));
        }
    }

    /**
     * Handles the response based on the success or failure of an operation.
     *
     * @param response        the HTTP response object
     * @param statusCode      the HTTP status code
     * @param success         boolean indicating success or failure
     * @param successResponse the response object for success
     * @param errorMessage    the response object for error
     * @return JSON response
     */
    public static Object handleResponse(Response response, int statusCode, boolean success, Object successResponse, Object errorMessage) {
        response.status(statusCode);
        return GSON.toJson(success ? successResponse : errorMessage);
    }
}
