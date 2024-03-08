package dataAccessTests;

import Requestclasses.Authtoken;
import Requestclasses.GameRequest;
import Requestclasses.Joingamerequest;
import Requestclasses.Registerclass;
import dataAccess.DataAccessException;
import dataAccess.MysqlGameDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

class MysqlGameDaoTest {


  MysqlGameDao game=new MysqlGameDao();

  @BeforeEach
  public void clear() throws DataAccessException {
    game.deleteall();
  }

  @Test
  @Order(1)
  public void join_positive() throws DataAccessException {
    var id=game.createGame("kapo", new GameRequest("HelloThere"));
    Joingamerequest info=new Joingamerequest("WHITE", id.gameID());
    game.joinGame("kapo", info);
    Assertions.assertEquals("kapo", game.getList("kapo").games().get(0).whiteUsername());
  }

  @Test
  public void join_negative() throws DataAccessException {
    Joingamerequest info=new Joingamerequest("WHITE", 0);
    Assertions.assertThrows(DataAccessException.class, () -> {
      game.joinGame("kapo", info);
    }, "id 0 should never be a game ID");
  }

  @Test
  public void getlist_positive() throws DataAccessException {
    var id=game.createGame("kapo", new GameRequest("HelloThere"));
    var id2=game.createGame("kapo", new GameRequest("Hello"));
    Assertions.assertEquals(2, game.getList("kapo").games().size());
  }

  @Test
  public void getlist_positive2() throws DataAccessException {
    Assertions.assertEquals(0, game.getList("kapo").games().size());
  }

  @Test
  public void create_positive() throws DataAccessException {
    var id=game.createGame("kapo", new GameRequest("Hello"));
    var id2=game.createGame("kapo", new GameRequest("Hello"));
    Assertions.assertEquals(2, game.getList("kapo").games().size());
  }

  @Test
  public void create_positive2() throws DataAccessException {
    var id=game.createGame("kapo", new GameRequest("HelloThere"));

    Joingamerequest info=new Joingamerequest("WHITE", id.gameID());
    game.joinGame("kapo", info);
    Assertions.assertEquals("kapo", game.getList("kapo").games().get(0).whiteUsername());


  }
}