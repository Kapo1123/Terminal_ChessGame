package dataAccessTests;

import Requestclasses.Authtoken;

import java.util.HashMap;
import java.util.Map;

public class AuthDAo implements AuthInterface {
  static Map<Authtoken, String> authodb=new HashMap<>();


  @Override
  public void createAuth(Authtoken authtoken,String username) {
    authodb.put(authtoken, username);

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
