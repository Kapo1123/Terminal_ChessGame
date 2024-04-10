package dataAccess;

import Requestclasses.Registerclass;
import Requestclasses.Userclass;
import dataAccessError.DataAccessException;

public interface UserInterface {
  public  void createUser(Registerclass info) throws DataAccessException;
  public boolean checkCredential(Userclass info) throws DataAccessException;
  public  void deleteall() throws DataAccessException;
}
