package dataAccess;

import Requestclasses.Authtoken;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AuthDAo implements AuthInterface {
  static Map<Authtoken, String> authodb=new HashMap<>();


  @Override
  public Authtoken createAuth(String username) {
    Authtoken authtoken=new Authtoken(UUID.randomUUID().toString());
    authodb.put(authtoken, username);
    return authtoken;
  }

  @Override
  public void deleteAuth(Authtoken auth) throws DataAccessException {
    if (!authodb.isEmpty() && authodb.get(auth) != null) {
      authodb.remove(auth);
    }else {
      throw new DataAccessException("Error: unauthorized");
    }
  }

  @Override
  public boolean isValid(Authtoken auth) {
    if (authodb.isEmpty() || authodb.get(auth) == null) {
      return false;
    }
    return true;
  }

  @Override
  public String getUserName(Authtoken auth) {
    return authodb.get(auth);
  }

  @Override
  public void deleteall() {
    if (authodb.isEmpty()) {
      return;
    }
    authodb.clear();

  }



}
