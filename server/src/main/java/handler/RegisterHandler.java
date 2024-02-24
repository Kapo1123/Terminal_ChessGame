package handler;

import Requestclasses.Authtoken;
import Requestclasses.Registerclass;
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
    Authtoken Authtoken= UserService.register(register);
    var res = new Registerresponse(register.username(), Authtoken);
    response.status(200);
    response.body(new Gson().toJson(res));
    return new Gson().toJson(res);
  }
}
