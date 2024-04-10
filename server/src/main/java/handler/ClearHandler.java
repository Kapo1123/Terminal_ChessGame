package handler;
import dataAccessError.DataAccessException;
import service.DbService;
import spark.Request;
import spark.Response;
import spark.Route;

public class ClearHandler implements Route{


  @Override
  public Object handle(Request request, Response response) throws DataAccessException {
    DbService dbService = new DbService();
    dbService.deleteAll();
      response.status(200);
//      response.body("");
      return "{}";
  }
}
