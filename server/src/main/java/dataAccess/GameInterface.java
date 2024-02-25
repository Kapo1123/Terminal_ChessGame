package dataAccess;

import Requestclasses.GameRequest;
import Requestclasses.Joingamerequest;
import Responseclass.ListgameResponse;
import Responseclass.Newgameresponse;

public interface GameInterface {
  public  ListgameResponse getList(String username);


  public  void joinGame(String username, Joingamerequest body) throws DataAccessException;

  public Newgameresponse createGame(String username, GameRequest body);
  public  void deleteall();
}

