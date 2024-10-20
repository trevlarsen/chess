package server;

import com.google.gson.Gson;
import model.responses.EmptyResponse;
import model.responses.ErrorResponse;
import service.BaseService;
import spark.Response;

public class BaseHandler {

    private final BaseService baseService = new BaseService();

    private static BaseHandler instance;

    public BaseHandler() {
    }

    public static BaseHandler getInstance() {
        if (instance == null) {
            instance = new BaseHandler();
        }
        return instance;
    }

    public Object clear(Response response) {
        try {
            baseService.clear();
            response.status(200);
            return new Gson().toJson(new EmptyResponse());
        } catch (Exception e) {
            response.status(500);
            return new Gson().toJson(new ErrorResponse("Error: " + e.getMessage()));
        }
    }
}
