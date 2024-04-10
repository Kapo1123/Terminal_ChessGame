package dataAccess;

import Requestclasses.Authtoken;
import dataAccessError.DataAccessException;

public interface AuthInterface {
 public  void createAuth(Authtoken authtoken,String username) throws DataAccessException;

 public void deleteAuth(Authtoken auth) throws DataAccessException;


 public boolean isValid (Authtoken auth) throws DataAccessException;

 public String getUserName(Authtoken auth) throws DataAccessException;
 public  void deleteall() throws DataAccessException;

}
