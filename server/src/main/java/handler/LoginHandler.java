package handler;

import Requestclasses.Authtoken;
import Requestclasses.Userclass;
import Responseclass.Errorresponse;
import Responseclass.Registerresponse;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import service.UserService;
import spark.Request;
import spark.Response;
import spark.Route;

public class LoginHandler implements Route {

  @Override
  public Object handle(Request request, Response response) throws DataAccessException {
    var user = new Gson().fromJson(request.body(), Userclass.class);
    UserService userservice = new UserService();
    Authtoken auth=null;
    try {
     auth=userservice.login(user);
    }catch(DataAccessException e){
      response.status(401);
      Errorresponse error = new Errorresponse(e.getMessage());
      response.body(new Gson().toJson(error));
      return (new Gson().toJson(error));
    }
    Registerresponse res = new Registerresponse(user.username(),auth.authtoken());
    response.status(200);
    response.body(new Gson().toJson(res));
    return new Gson().toJson(res);
  }
}
