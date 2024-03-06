package dataAccess;

import Requestclasses.GameRequest;
import Requestclasses.Joingamerequest;
import Responseclass.ListgameResponse;
import Responseclass.Newgameresponse;

public class MysqlGameDao implements GameInterface{
  @Override
  public ListgameResponse getList(String username) {
    return null;
  }

  @Override
  public void joinGame(String username, Joingamerequest body) throws DataAccessException {

  }

  @Override
  public Newgameresponse createGame(String username, GameRequest body) {
    return null;
  }

  @Override
  public void deleteall() {

  }
}
