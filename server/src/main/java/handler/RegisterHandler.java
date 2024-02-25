package handler;

import Requestclasses.Authtoken;
import Requestclasses.Registerclass;
import Responseclass.Errorresponse;
import Responseclass.Registerresponse;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
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
      if(e.getMessage().equals("Error: unauthorized" )){
        response.status(401);
      }else if (e.getMessage().equals("Error: bad request" )) {
        response.status(400);

      }
      else if(e.getMessage().equals("Error: already taken" )){response.status(403);}
      Errorresponse error = new Errorresponse(e.getMessage());
      response.body(new Gson().toJson(error));
      return new Gson().toJson(error);
    }
    Registerresponse res = new Registerresponse(register.username(), authtoken.authtoken());
    response.status(200);
    response.body(new Gson().toJson(res));
    return (new Gson().toJson(res));
  }
}
