package handler;

import Requestclasses.Authtoken;
import Requestclasses.Userclass;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Set;

public class LogoutHandler implements Route {
  @Override
  public Object handle(Request request, Response response) throws Exception {
    var auth =new Gson().fromJson(request.headers("authorization"), Authtoken.class);
    userService.logout(auth);
    response.status(200);
    return response;
  }
}
