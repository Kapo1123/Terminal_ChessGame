package handler;

import Requestclasses.Authtoken;
import Requestclasses.joingamerequest;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import service.gameServer;
import spark.Request;
import spark.Response;
import spark.Route;

public class JoingameHandler implements Route {
  @Override
  public Object handle(Request request, Response response) throws DataAccessException {
    var body = new Gson().fromJson(request.body(), joingamerequest.class);
    Authtoken token = new Authtoken(request.headers("authorization"));
    try {
      gameServer.joingame(token, body);
    }
    catch(DataAccessException e){
      throw e;
    }
    response.status(200);
    return response;
  }
}
