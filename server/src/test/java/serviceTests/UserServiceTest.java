package serviceTests;

import Requestclasses.*;
import Responseclass.newgameresponse;
import dataAccess.DataAccessException;
import dataAccess.UserDAo;
import org.junit.jupiter.api.*;
import service.GameService;
import service.UserService;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTest {
  private String username = "kapo";
  private String password = "1234";
  private String email = "HEllothere@yahoo.com";

  static Authtoken auth;
  static newgameresponse id;
  UserService user = new UserService();
  GameService game = new GameService();
  @Test
  @Order(1)
  public void register_positive() throws DataAccessException {
    Registerclass info = new Registerclass(username,password,email);
    var res = user.register(info);
    Assertions.assertNotNull(res, "Registration should return a valid AuthToken");

  }
  @Test
  @Order(2)
  public void register_negative() throws DataAccessException {
    Registerclass info = new Registerclass(null,password,email);
    Assertions.assertThrows(DataAccessException.class, () -> {
      user.register(info);
    }, "register with null AuthToken should throw DataAccessException");
  }
  @Order(3)
  @Test
  public void login_positive() throws DataAccessException {
    Userclass userInfo = new Userclass(username, password);
    var authtoken = user.login(userInfo);
    auth = authtoken;
    Assertions.assertNotNull(auth, "Login should return a valid AuthToken");
  }
  @Order(4)
  @Test
  public void login_negative() throws DataAccessException {
    String password = "I_don't_know";
    Userclass userInfo = new Userclass(username, password);
    Assertions.assertThrows(DataAccessException.class, () -> {
      user.login(userInfo);
    }, "Incorrect credential should return error");
  }
  @Order(5)
  @Test
  public void logout_negative() throws DataAccessException {
    Authtoken token = new Authtoken("Fake");
    Assertions.assertThrows(DataAccessException.class, () -> {
      user.logout(token);
    }, "Fake token should return error");
  }
  @Order(6)
  @Test
  public void logout_positive() throws DataAccessException {
    boolean ans = user.logout(auth);
   Assertions.assertTrue(ans,"The function should return True");
  }
  @Order(7)
  @Test
  public void creategame_positive() throws DataAccessException {
    Userclass userInfo = new Userclass(username, password);
    var authtoken = user.login(userInfo);
    auth = authtoken;
    GameRequest game_body = new GameRequest("HelloWorld");
    id = game.createGame(auth,game_body);
    Assertions.assertSame( id.getClass(), newgameresponse.class, "Types should be the same");

  }
  @Order(8)
  @Test
  public void creategame_negative() throws DataAccessException {
    GameRequest game_body = new GameRequest(null);
    Assertions.assertThrows(DataAccessException.class, () -> {
      game.createGame(auth,game_body);
    }, "null classname should return error");
  }
  @Order(9)
  @Test
  public void joingame_positive() throws DataAccessException {
    joingamerequest req = new joingamerequest("WHITE", id.gameID());
    var ans = game.joinGame(auth,req);
    Assertions.assertTrue(ans,"The function should return True");
  }
  @Order(10)
  @Test
  public void joingame_negative() throws DataAccessException {
    joingamerequest req = new joingamerequest("WHITE", 0);
    Assertions.assertThrows(DataAccessException.class, () -> {
      game.joinGame(auth,req);
    }, "id 0 should never be a game ID");
  }


  }
