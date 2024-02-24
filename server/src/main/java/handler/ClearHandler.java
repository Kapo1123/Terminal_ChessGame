package handler;
import service.dbService;
import spark.Request;
import spark.Response;
import spark.Route;

public class ClearHandler implements Route{

  @Override
  public Object handle(Request request, Response response) throws Exception {
      dbService.deleteall();
      response.status(200);
      return response;
  }
}
