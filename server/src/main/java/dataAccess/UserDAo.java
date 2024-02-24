package dataAccess;

import Requestclasses.Registerclass;

public interface UserDAo {
  public  boolean createuser(Registerclass info) throws DataAccessException;
}
