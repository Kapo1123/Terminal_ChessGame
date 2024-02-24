package service;

import Requestclasses.Authtoken;
import Requestclasses.Registerclass;
import Requestclasses.Userclass;
import dataAccess.DataAccessException;
import dataAccess.AuthDAo;
import dataAccess.UserDAo;

public class UserService {

  public static Authtoken register(Registerclass info) throws DataAccessException {
      if (info.username()==null||info.password()==null||info.email()==null){
        throw new DataAccessException("Error: bad request");
      }
    UserDAo userdao = new UserDAo();
      AuthDAo authdao = new AuthDAo();
      try {
        userdao.createUser(info);
        Authtoken Auth =  authdao.createAuth(info.username());
        return Auth;
    }
      catch(DataAccessException e){
        throw e;
    }
  }
  public static Authtoken login(Userclass info) throws DataAccessException {
    UserDAo userdao = new UserDAo();
    if(! userdao.checkCredential(info)){
      throw new DataAccessException("Error: unauthorized");
    }
    AuthDAo authdao = new AuthDAo();
    Authtoken Auth = null;
//    if(authdao.checkAuthExist(info.username())!=null){
//      Auth = authdao.checkAuthExist(info.username());
//      authdao.deleteAuth(Auth);
//    }
     Auth =  authdao.createAuth(info.username());
    return Auth;

  }
  public static boolean logout(Authtoken auth) throws DataAccessException {
    AuthDAo authdao = new AuthDAo();
    try {
      authdao.deleteAuth(auth);
      return true;
    }
    catch(DataAccessException e){
      throw e;
    }
  }


}
