package service;

import Requestclasses.Authtoken;
import Requestclasses.Registerclass;
import dataAccess.DataAccessException;

public class userService {

  public static Authtoken register(Registerclass info) throws DataAccessException {
      if (UserDAo.getusername(info.username()) ==null){
        UserDAo.createuser(info.username(),info.password(),info.email());
        Authtoken Auth =  UserDAo.createauth(info.username());
        return Auth;
    }
      else{
        throw new DataAccessException("Error: already taken");
    }
  }
}
