package dataAccess;

import Requestclasses.Authtoken;

public interface AuthInterface {
 public  Authtoken createAuth(String username);

 public void deleteAuth(Authtoken auth) throws DataAccessException;


 public boolean is_valid(Authtoken auth);

 public String getUserName(Authtoken auth);
}
