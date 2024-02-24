package handler;

import Requestclasses.Authtoken;
import Requestclasses.Registerclass;
import Requestclasses.Userclass;
import Responseclass.Registerresponse;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import service.userService;
import spark.Request;
import spark.Response;
import spark.Route;

public class loginHandler  implements Route {

  @Override
  public Object handle(Request request, Response response) throws DataAccessException {
    var user = new Gson().fromJson(request.body(), Userclass.class);
    Authtoken auth = userService.login(user);
    Registerresponse res = new Registerresponse(user.username(),auth);
    response.body(new Gson().toJson(res));
    response.status(200);
    return new Gson().toJson(res);
  }
}
