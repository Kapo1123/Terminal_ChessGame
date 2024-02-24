package dataAccess;

import Requestclasses.Authtoken;

public interface AuthInterface {
 public Authtoken createAuth(String username);
 public void deleteAuth(Authtoken auth) throws DataAccessException;

 public static boolean is_valid(Authtoken auth) {
  return false;
 }

 public String getusername(Authtoken auth);
}
