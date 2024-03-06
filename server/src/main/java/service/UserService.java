package service;

import Requestclasses.Authtoken;
import Requestclasses.Registerclass;
import Requestclasses.Userclass;
import dataAccess.DataAccessException;
import dataAccess.AuthDAo;
import dataAccess.UserDAo;

import java.util.UUID;

public class UserService {

  public static Authtoken register(Registerclass info) throws DataAccessException {
      if (info.username()==null||info.password()==null||info.email()==null){
        throw new DataAccessException("Error: bad request");
      }
    UserDAo userdao = new UserDAo();
      AuthDAo authdao = new AuthDAo();
      try {
        userdao.createUser(info);
        Authtoken authtoken=new Authtoken(UUID.randomUUID().toString());
        authdao.createAuth(authtoken,info.username());
        return authtoken;
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
    Authtoken authtoken=new Authtoken(UUID.randomUUID().toString());
    authdao.createAuth(authtoken,info.username());
    return authtoken;

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
