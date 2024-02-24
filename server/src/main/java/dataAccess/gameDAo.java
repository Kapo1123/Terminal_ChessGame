package dataAccess;

import Requestclasses.GameRequest;
import Requestclasses.joingamerequest;
import Responseclass.ListgameResponse;
import Responseclass.newgameresponse;

public interface gameDAo {
  public ListgameResponse getlist(String username);
  public  Boolean joingame(String username, joingamerequest body) throws DataAccessException;
  public newgameresponse creategame(String username, GameRequest body);
}
