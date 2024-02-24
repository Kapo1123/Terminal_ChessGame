package dataAccess;

import Requestclasses.Authtoken;

public interface AuthInterface {
 public Authtoken createauth(String username);
 public void logout(Authtoken auth) throws DataAccessException;
 public void is_valid(Authtoken auth) throws DataAccessException;
 public String getusername(Authtoken auth);
}
