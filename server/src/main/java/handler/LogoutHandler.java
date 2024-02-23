package handler;

import Requestclasses.Authtoken;
import Requestclasses.Userclass;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import service.userService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Set;

public class LogoutHandler implements Route {
  @Override
  public Object handle(Request request, Response response) throws DataAccessException {
    var auth =new Gson().fromJson(request.headers("authorization"), Authtoken.class);
    try{
    userService.logout(auth);}
    catch(DataAccessException e){
      response.status(401);
      throw e;
    }
    response.status(200);
    return response;
  }
}
