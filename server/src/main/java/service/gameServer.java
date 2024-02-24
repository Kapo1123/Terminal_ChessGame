package service;

import Requestclasses.Authtoken;
import Requestclasses.GameRequest;
import Requestclasses.Registerclass;
import Requestclasses.joingamerequest;
import Responseclass.ListgameResponse;
import Responseclass.newgameresponse;
import dataAccess.DataAccessException;

public class gameServer {

  public static ListgameResponse getgamelist(Authtoken auth) throws DataAccessException {
    try {
      AuthDAo.is_valid(auth);
    } catch (DataAccessException e) {
      throw e;
    }
    String username=AuthDAo.getusername(auth);
    ListgameResponse res=gameDAO.getlist(username);
    return res;
  }

  public static Boolean joingame(Authtoken auth, joingamerequest body) throws DataAccessException {
    if (body.gameID() == null) {
      throw new DataAccessException("Error: bad request");
    }
    try {
      AuthDAo.is_valid(auth);
    } catch (DataAccessException e) {
      throw e;
    }
    String username=AuthDAo.getusername(auth);
    try {
      gameDAo.joingame(username, body);
    } catch (DataAccessException e) {
      throw e;
    }
    return true;
  }

  public static newgameresponse creategame(Authtoken auth, GameRequest body) throws DataAccessException {
    if (body.Gamename() == null) {
      throw new DataAccessException("Error: bad request");
    }
    try {
      AuthDAo.is_valid(auth);
    } catch (DataAccessException e) {
      throw e;
    }
    String username=AuthDAo.getusername(auth);
    newgameresponse res = gameDAo.creategame(username, body);
    return res;
  }
}
