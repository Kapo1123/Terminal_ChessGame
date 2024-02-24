package serviceTests;

import Requestclasses.Registerclass;
import dataAccess.DataAccessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.UserService;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
  private String username = "kapo";
  private String password = "1234";
  private String email = "HEllothere@yahoo.com";


  @Test
  public void register_positive() throws DataAccessException {
    Registerclass info = new Registerclass(username,password,email);
    UserService user = new UserService();
    var res = user.register(info);
    Assertions.assertNotNull(res, "Registration should return a valid AuthToken");

  }
  @Test
  public void register_negative() throws DataAccessException {
    Registerclass info = new Registerclass(null,password,email);
    UserService user = new UserService();
    Assertions.assertThrows(DataAccessException.class, () -> {
      user.register(info);
    }, "register with null AuthToken should throw DataAccessException");
  }
  }
