package handler;

import Requestclasses.Authtoken;
import Responseclass.Errorresponse;
import Responseclass.ListgameResponse;
import com.google.gson.Gson;
import dataAccessError.DataAccessException;
import service.GameService;
import spark.Request;
import spark.Response;
import spark.Route;

public class ListGameHandler implements Route{
  @Override
  public Object handle(Request request, Response response) throws DataAccessException {
    var auth = new Authtoken(request.headers("authorization"));
    GameService gameservice=new GameService();
    ListgameResponse games =null;
    try {
      games=gameservice.getGameList(auth);
      response.status(200);
      response.body(new Gson().toJson(games));
      return new Gson().toJson(games);
    } catch (DataAccessException e) {
      if(e.getMessage().equals("Error: unauthorized" )){
        response.status(401);}
      Errorresponse error = new Errorresponse(e.getMessage());
      response.body(new Gson().toJson(error));
      return new Gson().toJson(error);
    }
  }



}
