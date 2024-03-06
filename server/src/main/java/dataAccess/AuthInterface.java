package dataAccess;

import Requestclasses.Authtoken;

public interface AuthInterface {
 public  Authtoken createAuth(String username) throws DataAccessException;

 public void deleteAuth(Authtoken auth) throws DataAccessException;


 public boolean isValid (Authtoken auth) throws DataAccessException;

 public String getUserName(Authtoken auth) throws DataAccessException;
 public  void deleteall() throws DataAccessException;

}
