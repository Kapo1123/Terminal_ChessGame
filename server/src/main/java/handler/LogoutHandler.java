package handler;

import Requestclasses.Authtoken;
import Responseclass.Errorresponse;
import com.google.gson.Gson;
import dataAccessTests.DataAccessException;
import service.UserService;
import spark.Request;
import spark.Response;
import spark.Route;

public class LogoutHandler implements Route {
  @Override
  public Object handle(Request request, Response response) throws DataAccessException {
    var auth = new Authtoken(request.headers("authorization"));
    UserService userservice = new UserService();
    try{
      userservice.logout(auth);}
    catch(DataAccessException e){
      response.status(401);
      Errorresponse error = new Errorresponse(e.getMessage());
      response.body(new Gson().toJson(error));
      return new Gson().toJson(error);
    }
    response.status(200);
    return "{}";
  }
}
