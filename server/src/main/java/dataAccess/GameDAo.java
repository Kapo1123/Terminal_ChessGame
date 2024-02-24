package dataAccess;

import Requestclasses.GameRequest;
import Requestclasses.joingamerequest;
import Responseclass.Games;
import Responseclass.ListgameResponse;
import Responseclass.newgameresponse;

import java.util.ArrayList;
import java.util.List;

public class GameDAo implements GameInterface {

  static List<Games> gamedb = new ArrayList<Games>();

  @Override
  public  ListgameResponse getList(String username) {
    return new ListgameResponse(gamedb);
  }

  @Override
  public  void joinGame(String username, joingamerequest body) throws DataAccessException {
    String color = body.playerColor();
    Integer i =0;
    for (Games game : gamedb) {
      if (game.gameID().equals(body.gameID())) {
        // Found the game with the specified gameID
        if (color.equals("BLACK") && game.blackUsername() == null) {
          Games temp = new Games(game.gameID(), game.whiteUsername(), username, game.gameName());
           gamedb.remove(i);
           gamedb.add(i,temp);
           return;
          // Perform any additional actions needed for joining as black
        } else if (color.equals("WHITE") && game.whiteUsername() == null) {
          Games temp = new Games(game.gameID(), username, game.blackUsername(), game.gameName());
          gamedb.remove(i);
          gamedb.add(i,temp);
          return;
          // Perform any additional actions needed for joining as white
        } else {
          throw new DataAccessException("Error: already taken");
        }
        // Exit the loop after processing the game

      }
      i++;
    }
    throw new DataAccessException( "Error: bad request");


  }

  @Override
  public newgameresponse createGame(String username, GameRequest body) {
    Integer game_ID = gamedb.size()+1;
    Games game = new Games(game_ID, null,null,body.gameName());
    gamedb.add(game);
    return new newgameresponse(game_ID);
  }
  @Override
  public void deleteall(){
    if(gamedb.isEmpty()){
      return;
    }
    gamedb.clear();

  }
}