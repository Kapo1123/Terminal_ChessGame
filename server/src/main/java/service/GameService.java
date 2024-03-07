package service;

import Requestclasses.Authtoken;
import Requestclasses.GameRequest;
import Requestclasses.Joingamerequest;
import Responseclass.ListgameResponse;
import Responseclass.Newgameresponse;
import dataAccess.*;

public class GameService {


  public ListgameResponse getGameList(Authtoken auth) throws DataAccessException {
    MysqlAuthDao authdao = new MysqlAuthDao();
    if (!authdao.isValid(auth)){
      throw new DataAccessException("Error: unauthorized");
    }
    MysqlGameDao gamedao = new MysqlGameDao();
    String username=authdao.getUserName(auth);
    ListgameResponse res=gamedao.getList(username);
    return res;
  }

  public Boolean joinGame(Authtoken auth, Joingamerequest body) throws DataAccessException {
    if (body.gameID() == null) {
      throw new DataAccessException("Error: bad request");
    }
    MysqlAuthDao authdao = new MysqlAuthDao();
    if (!authdao.isValid(auth)){
      throw new DataAccessException("Error: unauthorized");
    }
    String username=authdao.getUserName(auth);
    MysqlGameDao gamedao = new MysqlGameDao();
    try {
      gamedao.joinGame(username, body);
    } catch (DataAccessException e) {
      throw e;
    }
    return true;
  }

  public Newgameresponse createGame(Authtoken auth, GameRequest body) throws DataAccessException {
    if (body.gameName() == null) {
      throw new DataAccessException("Error: bad request");
    }
    MysqlAuthDao authdao = new MysqlAuthDao();
      if (!authdao.isValid(auth)){
        throw new DataAccessException("Error: unauthorized");
      }
    MysqlGameDao gamedao = new MysqlGameDao();
    String username=authdao.getUserName(auth);
    Newgameresponse res = gamedao.createGame(username, body);
    return res;
  }
}
