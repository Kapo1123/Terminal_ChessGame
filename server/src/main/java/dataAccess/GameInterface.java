package dataAccess;

import Requestclasses.GameRequest;
import Requestclasses.Joingamerequest;
import Responseclass.ListgameResponse;
import Responseclass.Newgameresponse;

public interface GameInterface {
  public  ListgameResponse getList(String username) throws DataAccessException;


  public  void joinGame(String username, Joingamerequest body) throws DataAccessException;

  public Newgameresponse createGame(String username, GameRequest body) throws DataAccessException;
  public  void deleteall() throws DataAccessException;
}

