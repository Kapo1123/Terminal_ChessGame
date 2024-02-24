package service;

import Requestclasses.Authtoken;
import Requestclasses.GameRequest;
import Requestclasses.joingamerequest;
import Responseclass.ListgameResponse;
import Responseclass.newgameresponse;
import dataAccess.DataAccessException;
import dataAccess.AuthDAo;
public class GameService {

  public static ListgameResponse getGameList(Authtoken auth) throws DataAccessException {
    if (!AuthDAo.is_valid(auth)){
      throw new DataAccessException("Error: unauthorized");
    }
    String username=AuthDAo.getUserName(auth);
    ListgameResponse res=gameDAo.getList(username);
    return res;
  }

  public static Boolean joinGame(Authtoken auth, joingamerequest body) throws DataAccessException {
    if (body.gameID() == null) {
      throw new DataAccessException("Error: bad request");
    }
    if (!AuthDAo.is_valid(auth)){
      throw new DataAccessException("Error: unauthorized");
    }
    String username=AuthDAo.getUserName(auth);
    try {
      gameDAo.joinGame(username, body);
    } catch (DataAccessException e) {
      throw e;
    }
    return true;
  }

  public static newgameresponse createGame(Authtoken auth, GameRequest body) throws DataAccessException {
    if (body.Gamename() == null) {
      throw new DataAccessException("Error: bad request");
    }
      if (!AuthDAo.is_valid(auth)){
        throw new DataAccessException("Error: unauthorized");
      }
    String username=AuthDAo.getUserName(auth);
    newgameresponse res = gameDAo.createGame(username, body);
    return res;
  }
}
