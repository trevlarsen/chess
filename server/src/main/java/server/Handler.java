package server;

import com.google.gson.Gson;
import model.reponses.EmptyResponse;
import model.reponses.ErrorResponse;
import service.Service;
import spark.Response;

public class Handler {

    private final Service service = new Service();

    private static Handler instance;

    public Handler() {
    }

    public static Handler getInstance() {
        if (instance == null) {
            instance = new Handler();
        }
        return instance;
    }

    public Object clear(Response response) {
        try {
            service.clear();
            response.status(200);
            return new Gson().toJson(new EmptyResponse());
        } catch (Exception e) {
            response.status(500);
            return new Gson().toJson(new ErrorResponse("Error: " + e.getMessage()));
        }
    }
}
