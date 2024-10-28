package service;

import dataaccess.DataAccessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class ClearTests {
    private final BaseService baseService = new BaseService();

    @Test
    @DisplayName("Clear - Successful")
    public void clearSuccess() throws DataAccessException {
        baseService.clear();
    }
}
