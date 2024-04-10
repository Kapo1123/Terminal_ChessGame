package serviceTests;

import Requestclasses.*;
import Responseclass.ListgameResponse;
import Responseclass.Newgameresponse;
import dataAccessError.DataAccessException;
import org.junit.jupiter.api.*;
import service.DbService;
import service.GameService;
import service.UserService;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ServiceTest {
  private String username = "kapo";
  private String password = "1234";
  private String email = "HEllothere@yahoo.com";

  static Authtoken auth;
  static Newgameresponse id;
  UserService user = new UserService();
  GameService game = new GameService();
  DbService db = new DbService();
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
    Assertions.assertSame( id.getClass(), Newgameresponse.class, "Types should be the same");

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
    Joingamerequest req = new Joingamerequest("WHITE", id.gameID());
    var ans = game.joinGame(auth,req);
    Assertions.assertTrue(ans,"The function should return True");
  }
  @Order(10)
  @Test
  public void joingame_negative() throws DataAccessException {
    Joingamerequest req = new Joingamerequest("WHITE", 0);
    Assertions.assertThrows(DataAccessException.class, () -> {
      game.joinGame(auth,req);
    }, "id 0 should never be a game ID");
  }
  @Order(11)
  @Test
  public void getGameList_positive() throws DataAccessException {
    GameRequest game_body1 = new GameRequest("StarWar");
    var id2 = game.createGame(auth,game_body1);
    GameRequest game_body2 = new GameRequest("DarkVader");
    var id3 = game.createGame(auth,game_body2);
    GameRequest game_body3 = new GameRequest("SkyWalker");
    var id4 = game.createGame(auth,game_body3);
    Joingamerequest req = new Joingamerequest("WHITE", id4.gameID());
    game.joinGame(auth,req);
    ListgameResponse list_ = game.getGameList(auth);
    Assertions.assertEquals(2, list_.games().size());
  }
  @Order(12)
  @Test
  public void getGameList_negative() throws DataAccessException {
    user.logout(auth);
    Assertions.assertThrows(DataAccessException.class, () -> {
      game.getGameList(auth);
    }, "Invalid auth should return no list");
  }
  @Order(13)
  @Test
  public void clear_positive() throws DataAccessException{
    Authtoken token1 = user.register(new Registerclass("what","is","this"));
    Authtoken token2 = user.register(new Registerclass("I","don't","know"));
    auth = user.login(new Userclass(username,password));
    game.createGame(auth, new GameRequest("HongKong"));
    game.createGame(token1, new GameRequest("Macau"));
    game.createGame(token2, new GameRequest("LA"));
    db.deleteAll();
    Assertions.assertThrows(DataAccessException.class, () -> {
      user.login(new Userclass("what","is"));
    }, "No user data in database");
  }

  }
