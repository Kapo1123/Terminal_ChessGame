package dataAccess;

import Requestclasses.Authtoken;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AuthDAo implements AuthInterface {
  static Map<Authtoken,String> userdb = new HashMap<>();


  @Override
  public  Authtoken createAuth(String username) {
   Authtoken authtoken = new Authtoken(UUID.randomUUID().toString());
   userdb.put(authtoken,username);
   return authtoken;
  }

  @Override
  public  void deleteAuth(Authtoken auth) throws DataAccessException {
    if (!userdb.isEmpty() && userdb.get(auth) != null) {
      userdb.remove(auth);
    }
    else{
      throw new DataAccessException("Error: unauthorized");
    }
  }
  @Override
  public  boolean is_valid(Authtoken auth)  {
    if(userdb.isEmpty()||userdb.get(auth) == null ){
      return false;
    }
    return true;
  }

  @Override
  public  String getUserName(Authtoken auth) {
    return userdb.get(auth);
  }
}
