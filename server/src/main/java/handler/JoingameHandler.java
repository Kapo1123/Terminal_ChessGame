package handler;

import Requestclasses.Authtoken;
import Requestclasses.Joingamerequest;
import Responseclass.Errorresponse;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import service.GameService;
import spark.Request;
import spark.Response;
import spark.Route;

public class JoingameHandler implements Route {
  @Override
  public Object handle(Request request, Response response) throws DataAccessException {
    var body = new Gson().fromJson(request.body(), Joingamerequest.class);
    Authtoken token = new Authtoken(request.headers("authorization"));
    GameService gameservice = new GameService();
    try {
      gameservice.joinGame(token, body);
    }
    catch(DataAccessException e){
      response.status(e.getErrorCode());
      Errorresponse error = new Errorresponse(e.getMessage());
      response.body(new Gson().toJson(error));
      return new Gson().toJson(error);
    }
    response.status(200);
    return "{}";
  }
}
