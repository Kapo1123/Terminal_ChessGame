package service;

import Requestclasses.Authtoken;
import Requestclasses.Registerclass;
import Requestclasses.Userclass;
import dataAccess.*;

import java.util.UUID;

public class UserService {

  public static Authtoken register(Registerclass info) throws DataAccessException {
      if (info.username()==null||info.password()==null||info.email()==null){
        throw new DataAccessException("Error: bad request");
      }
      MysqlUserDao userdao = new MysqlUserDao();
      MysqlAuthDao authdao = new MysqlAuthDao();
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
    MysqlUserDao userdao = new MysqlUserDao();
    if(! userdao.checkCredential(info)){
      throw new DataAccessException("Error: unauthorized");
    }
    MysqlAuthDao authdao = new MysqlAuthDao();
    Authtoken authtoken=new Authtoken(UUID.randomUUID().toString());
    authdao.createAuth(authtoken,info.username());
    return authtoken;

  }
  public static boolean logout(Authtoken auth) throws DataAccessException {
    MysqlAuthDao authdao = new MysqlAuthDao();
    try {
      authdao.deleteAuth(auth);
      return true;
    }
    catch(DataAccessException e){
      throw e;
    }
  }


}
