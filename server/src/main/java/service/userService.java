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
        Authtoken Auth =  AuthDAo.createauth(info.username());
        return Auth;
    }
      catch(DataAccessException e){
        throw e;
    }
  }
  public static Authtoken login(Userclass info) throws DataAccessException {
    try {
      UserDAo.checkcredential(info.username(), info.Password());{
      }
    }
    catch(DataAccessException e){
      throw e;
    }
    Authtoken Auth =  AuthDAo.createauth(info.username());
    return Auth;

  }
  public static boolean logout(Authtoken auth) throws DataAccessException {
    try {
      AuthDAo.logout(auth);
      return true;
    }
    catch(DataAccessException e){
      throw e;
    }
  }


}
