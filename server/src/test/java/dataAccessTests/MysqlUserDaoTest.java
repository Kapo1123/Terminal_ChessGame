package dataAccessTests;

import Requestclasses.Registerclass;
import Requestclasses.Userclass;
import dataAccessError.DataAccessException;
import dataAccess.MysqlUserDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

class MysqlUserDaoTest {
  MysqlUserDao user=new MysqlUserDao();
  private String username = "kapo";
  private String password = "1234";
  private String email = "HEllothere@yahoo.com";
  @BeforeEach
  public void clear() throws DataAccessException {
    user.deleteall();
  }
  @Test
  @Order(1)
  public void createUser_positive() throws DataAccessException {
    Registerclass register = new Registerclass(username,password,email);
    user.createUser(register);
    Userclass info = new Userclass(username,password);
    var ans  = user.checkCredential(info);
    Assertions.assertTrue(ans,"The function should return True");
  }
  @Test
  @Order(1)
  public void createUser_negative() throws DataAccessException {
    Registerclass register = new Registerclass(username,password,email);
    user.createUser(register);
    Assertions.assertThrows(DataAccessException.class, () -> {
      user.createUser(register);
    }, "id 0 should never be a game ID");
  }
  @Test
  @Order(1)
  public void checkCredential_positive() throws DataAccessException {
    Registerclass register = new Registerclass(username,password,email);
    user.createUser(register);
    Userclass info = new Userclass(username,password);
    var ans  = user.checkCredential(info);
    Assertions.assertTrue(ans,"The function should return True");
  }
  @Test
  @Order(1)
  public void checkCredential_negative() throws DataAccessException {
    Userclass info = new Userclass(username,password);
    var ans  = user.checkCredential(info);
    Assertions.assertFalse(ans,"The function should return True");
  }
  @Test
  @Order(1)
  public void user_clear() throws DataAccessException {
   user.deleteall();
  }


}