package handler;

import Requestclasses.Authtoken;
import Requestclasses.joingamerequest;
import spark.Request;
import spark.Response;
import spark.Route;

public class JoingameHandler implements Route {
  @Override
  public Object handle(Request request, Response response) throws Exception {
    var body = new Gson.fromJson(request.body(), joingamerequest.class);
    Authtoken token = new Authtoken(request.headers("authorization"));
    GameServer.joingame(token,body);
    response.status(200);
    return response;
  }
}
