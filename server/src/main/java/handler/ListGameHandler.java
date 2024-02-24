package handler;

import Requestclasses.Authtoken;
import Responseclass.ListgameResponse;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import service.gameServer;
import spark.Request;
import spark.Response;
import spark.Route;

public class ListGameHandler implements Route {
  @Override
  public Object handle(Request request, Response response) throws DataAccessException {
    var auth =new Gson().fromJson(request.headers("authorization"), Authtoken.class);
    try{
    ListgameResponse games = gameServer.getgamelist(auth);
    response.status(200);
    response.body(new Gson().toJson(games));
    return new Gson().toJson(games);}
    catch(DataAccessException e){
      throw e;
    }

  }


}
