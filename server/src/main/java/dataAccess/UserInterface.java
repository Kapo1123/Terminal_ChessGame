package dataAccess;

import Requestclasses.Registerclass;
import Requestclasses.Userclass;

public interface UserInterface {
  public  void createuser(Registerclass info) throws DataAccessException;
  public void checkcredential(Userclass info) throws DataAccessException;
}
