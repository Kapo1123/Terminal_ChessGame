package dataAccessTests;

import Requestclasses.*;
import dataAccessError.DataAccessException;
import dataAccess.MysqlAuthDao;
import org.junit.jupiter.api.*;

import java.util.UUID;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MysqlAuthDaoTest {
  MysqlAuthDao auth=new MysqlAuthDao();

  @BeforeEach
  public void clear() throws DataAccessException {
    auth.deleteall();
  }

  @Test
  @Order(1)
  public void create_auth_positive() throws DataAccessException {
    Authtoken authtoken=new Authtoken(UUID.randomUUID().toString());
    auth.createAuth(authtoken,"kapo");
    var ans = auth.isValid(authtoken);
    Assertions.assertTrue(ans,"The function should return True");
  }
  @Test
  @Order(1)
  public void create_auth_negative() throws DataAccessException {
    Authtoken authtoken=new Authtoken(UUID.randomUUID().toString());
    auth.createAuth(authtoken,"kapo");
    Assertions.assertThrows(DataAccessException.class, () -> {
      auth.createAuth(authtoken, "kapo");
    }, "id 0 should never be a game ID");
  }
  @Test
  @Order(1)
  public void deleteAuth_positive() throws DataAccessException {
    Authtoken authtoken=new Authtoken(UUID.randomUUID().toString());
    auth.createAuth(authtoken,"kapo");
    auth.deleteAuth(authtoken);
    var ans = auth.isValid(authtoken);
    Assertions.assertFalse(ans,"The function should return False");
  }


  @Test
  public void deleteAuth_negative() throws DataAccessException {
    Authtoken authtoken=new Authtoken(UUID.randomUUID().toString());
    auth.deleteAuth(authtoken);
    var ans = auth.isValid(authtoken);
    Assertions.assertFalse(ans,"The function should return False");
  }

  @Test
  public void isValid_positive() throws DataAccessException {
    Authtoken authtoken=new Authtoken(UUID.randomUUID().toString());
    auth.createAuth(authtoken,"kapo");
    var ans = auth.isValid(authtoken);
    Assertions.assertTrue(ans,"The function should return True");
  }

  @Test
  public void isValid_negative() throws DataAccessException {
    Authtoken authtoken=new Authtoken(UUID.randomUUID().toString());
    var ans = auth.isValid(authtoken);
    Assertions.assertFalse(ans,"The function should return False");
  }

  @Test
  public void getUserName_positive() throws DataAccessException {
    Authtoken authtoken=new Authtoken(UUID.randomUUID().toString());
    auth.createAuth(authtoken,"kapo");
    var ans = auth.getUserName(authtoken);
    Assertions.assertEquals(ans,"kapo");
  }

  @Test
  public void getUserName_negative() throws DataAccessException {
    Authtoken authtoken=new Authtoken(UUID.randomUUID().toString());
    var ans = auth.getUserName(authtoken);
    Assertions.assertNull(ans,"This should be null");
  }
  @Test
  public void delete_positive() throws DataAccessException {
    auth.deleteall();
  }
}