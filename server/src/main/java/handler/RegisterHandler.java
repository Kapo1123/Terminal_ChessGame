package handler;

import Requestclasses.Authtoken;
import Requestclasses.Registerclass;
import Responseclass.Errorresponse;
import Responseclass.Registerresponse;
import com.google.gson.Gson;
import dataAccessTests.DataAccessException;
import spark.Request;
import spark.Response;
import spark.Route;
import service.UserService;
public class RegisterHandler implements Route {

  @Override
  public Object handle(Request request, Response response) throws DataAccessException {
    var register = new Gson().fromJson(request.body(), Registerclass.class);
    UserService userservice = new UserService();
    Authtoken authtoken =null;
    try{
      authtoken= userservice.register(register);}
     catch(DataAccessException e){
      response.status(e.getErrorCode());
      response.body(new Gson().toJson(new Errorresponse(e.getMessage())));
      return new Gson().toJson(new Errorresponse(e.getMessage()));
    }
    Registerresponse res = new Registerresponse(register.username(), authtoken.authtoken());
    response.status(200);
    response.body(new Gson().toJson(res));
    return (new Gson().toJson(res));
  }
}
