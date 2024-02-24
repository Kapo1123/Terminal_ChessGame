package handler;
import service.DbService;
import spark.Request;
import spark.Response;
import spark.Route;


import java.io.FileDescriptor;

public class ClearHandler implements Route{


  @Override
  public Object handle(Request request, Response response) {
    DbService dbService = new DbService();
    dbService.deleteAll();
      response.status(200);
//      response.body("");
      return "{}";
  }
}
