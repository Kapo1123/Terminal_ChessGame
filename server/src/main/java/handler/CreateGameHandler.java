package handler;

import Requestclasses.Authtoken;
import Requestclasses.GameRequest;
import Responseclass.Errorresponse;
import Responseclass.Newgameresponse;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import service.GameService;
import spark.Request;
import spark.Response;
import spark.Route;

public class CreateGameHandler implements Route {
  @Override
  public Object handle(Request request, Response response) throws DataAccessException {
      var body = new Gson().fromJson(request.body(), GameRequest.class);
      GameService gameservice = new GameService();
      Authtoken token = new Authtoken(request.headers("authorization"));
      Newgameresponse res =null;
      try{
      res = gameservice.createGame(token,body);
        response.status(200);
        response.body(new Gson().toJson(res));
        return new Gson().toJson(res);
      }
      catch(DataAccessException e){
        if(e.getMessage().equals("Error: unauthorized" )){
          response.status(401);
        }else if (e.getMessage().equals("Error: bad request" )) {
          response.status(400);

        }
        Errorresponse error = new Errorresponse(e.getMessage());
        response.body(new Gson().toJson(error));
        return new Gson().toJson(error);
      }

  }
}
