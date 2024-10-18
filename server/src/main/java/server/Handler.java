package server;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import service.Service;
import spark.Request;
import spark.Response;

public class Handler {

    private final Service service = new Service();

    private static Handler instance;

    public Handler() throws DataAccessException {
    }

    public static Handler getInstance() throws DataAccessException {
        if (instance == null) {
            instance = new Handler(); // Ensure the instance is assigned here
        }
        return instance;
    }

    public Object clear(Request req, Response res) {
        try {
            service.clear(); // Clear service data
            res.status(200); // Success status
            return new Gson().toJson(new ResponseMessage("Success"));
        } catch (Exception e) {
            res.status(500); // Error status
            return new Gson().toJson(new ResponseMessage("Error: " + e.getMessage()));
        }
    }

    private static class ResponseMessage {
        public ResponseMessage(String message) {
        }
    }
}
