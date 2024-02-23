package service;

import Requestclasses.Authtoken;
import Requestclasses.Registerclass;
import Requestclasses.Userclass;
import dataAccess.DataAccessException;

public class userService {

  public static Authtoken register(Registerclass info) throws DataAccessException {
      if (info.username()==null||info.password()==null||info.email()==null){
        throw new DataAccessException("Error: bad request");
      }

      try {
        UserDAo.createuser(info.username(),info.password(),info.email());
        Authtoken Auth =  UserDAo.createauth(info.username());
        return Auth;
    }
      catch(DataAccessException e){
        throw e;
    }
  }
  public static Authtoken login(Userclass info) throws DataAccessException {
    try {
      UserDAo.checkcredential(info.username(), info.Password());
      Authtoken Auth =  UserDAo.createauth(info.username());
      return Auth;
    }
    catch(DataAccessException e){
      throw e;
    }
  }
  public static boolean logout(Authtoken auth) throws DataAccessException {
    try {
      UserDAo.logout(auth);
      return true;
    }
    catch(DataAccessException e){
      throw e;
    }
  }


}
