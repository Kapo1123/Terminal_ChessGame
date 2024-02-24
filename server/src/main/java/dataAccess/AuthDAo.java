package dataAccess;

import Requestclasses.Authtoken;

public class AuthDAo implements AuthInterface {
  @Override
  public Authtoken createauth(String username) {
    return null;
  }

  @Override
  public void logout(Authtoken auth) throws DataAccessException {

  }

  @Override
  public void is_valid(Authtoken auth) throws DataAccessException {

  }

  @Override
  public String getusername(Authtoken auth) {
    return null;
  }
}
