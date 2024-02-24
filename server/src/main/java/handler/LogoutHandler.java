package handler;

import Requestclasses.Authtoken;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import service.UserService;
import spark.Request;
import spark.Response;
import spark.Route;

public class LogoutHandler implements Route {
  @Override
  public Object handle(Request request, Response response) throws DataAccessException {
    var auth =new Gson().fromJson(request.headers("authorization"), Authtoken.class);
    try{
    UserService.logout(auth);}
    catch(DataAccessException e){
      response.status(401);
      throw e;
    }
    response.status(200);
    return response;
  }
}
