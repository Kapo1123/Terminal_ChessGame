package dataAccess;

import Requestclasses.Registerclass;
import Requestclasses.Userclass;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface UserInterface {
  public  void createUser(Registerclass info) throws DataAccessException;
  public boolean checkCredential(Userclass info) throws DataAccessException;
  public  void deleteall() throws DataAccessException;
}
