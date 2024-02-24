package service;

import Requestclasses.Authtoken;
import Requestclasses.Registerclass;
import Requestclasses.Userclass;
import dataAccess.DataAccessException;
import dataAccess.AuthDAo;

public class UserService {

  public static Authtoken register(Registerclass info) throws DataAccessException {
      if (info.username()==null||info.password()==null||info.email()==null){
        throw new DataAccessException("Error: bad request");
      }

      try {
        UserDAo.createuser(info);
        Authtoken Auth =  AuthDAo.createAuth(info.username());
        return Auth;
    }
      catch(DataAccessException e){
        throw e;
    }
  }
  public static Authtoken login(Userclass info) throws DataAccessException {
    if(! UserDAo.checkCredential(info)){
      throw new DataAccessException("Error: unauthorized");
    }

    Authtoken Auth =  AuthDAo.createAuth(info.username());
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
