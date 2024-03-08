package dataAccess;

import Requestclasses.GameRequest;
import Requestclasses.Joingamerequest;
import Responseclass.Games;
import Responseclass.ListgameResponse;
import Responseclass.Newgameresponse;

import java.util.ArrayList;
import java.util.List;

public class GameDAo implements GameInterface {

  static List<Games> gamedb = new ArrayList<Games>();

  @Override
  public  ListgameResponse getList(String username) {
    List<Games> list = new ArrayList<Games>();
    for (Games game : gamedb){
      if(game.whiteUsername() != null){
        if (game.whiteUsername().equals(username)){
          list.add(game);
        }
      }
      else if (game.blackUsername() != null){
        if(game.blackUsername().equals(username)){
          list.add(game);
        }
      }

    }
    if(list.size() == 0){
      return new ListgameResponse(gamedb);
    }
    return new ListgameResponse(list);
  }

  @Override
  public  void joinGame(String username, Joingamerequest body) throws DataAccessException {
    if(body.playerColor() ==null){
      for (Games game : gamedb) {
        if (game.gameID().equals(body.gameID())) {
        return;}
      }
      throw new DataAccessException( "Error: bad request");
    }
    String color = body.playerColor();
    Integer i =0;
    for (Games game : gamedb) {
      if (game.gameID().equals(body.gameID())) {
        // Found the game with the specified gameID
        if (color.equals("BLACK") && game.blackUsername() == null) {
          Games temp = new Games(game.gameID(), game.whiteUsername(), username, game.gameName());
           gamedb.remove(gamedb.get(i));
           gamedb.add(i,temp);
           return;
          // Perform any additional actions needed for joining as black
        } else if (color.equals("WHITE") && game.whiteUsername() == null) {
          Games temp = new Games(game.gameID(), username, game.blackUsername(), game.gameName());
          gamedb.remove(gamedb.get(i));
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
  public Newgameresponse createGame(String username, GameRequest body) {
    Integer gameId = gamedb.size()+1;
    Games game = new Games(gameId, null,null,body.gameName());
    gamedb.add(game);
    return new Newgameresponse(gameId);
  }
  @Override
  public void deleteall(){
    if(gamedb.isEmpty()){
      return;
    }
    gamedb.clear();

  }
}
