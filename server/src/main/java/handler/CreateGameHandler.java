package handler;

import Requestclasses.Authtoken;
import Requestclasses.GameRequest;
import Responseclass.newgameresponse;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;

public class CreateGameHandler implements Route {
  @Override
  public Object handle(Request request, Response response) throws Exception {
      var body = new Gson().fromJson(request.body(), GameRequest.class);
      Authtoken token = new Authtoken(request.headers("authorization"));
      newgameresponse res = gameServer.creategame(token,body);
      response.status(200);
      response.body(new Gson().toJson(res));
      return new Gson().toJson(res);

  }
}
