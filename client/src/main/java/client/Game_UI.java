package client;

import Responseclass.Games;
import Responseclass.ListgameResponse;
import chess.ChessGame;
import dataAccess.DataAccessException;
import ui.chess_board;

import java.util.Arrays;

public class Game_UI {
  websocket.WebSocketFacade server;
  ChessGame.TeamColor color;
  chess_board board = new chess_board();
  public String eval(String input) {

    try {
      var tokens = input.toLowerCase().split(" ");
      var cmd = (tokens.length > 0) ? tokens[0] : "help";
      var params = Arrays.copyOfRange(tokens, 1, tokens.length);
      return switch (cmd) {
        case "redraw" -> redraw(params);
        case "leave" -> Leave();
        case "makeMove" -> MakeMove(params);
        case "resign" -> Resign(params);
        case "valid_moves" -> Valid_moves();
        default -> help();
      };
    } catch (DataAccessException ex) {
      return ex.getMessage();
    }
  }
  public String help(){
    String help_text = """
                    
                    - redraw  -a board
                    - Leave -return to main meau
                    - MakeMove rowcol rowcol  ex: 74 47
                    - Resign 
                    - Valid_moves
                    - Help 
                    """;
    return help_text;
  }
  public void redraw(String[] params) throws DataAccessException{

    if (color.equals( ChessGame.TeamColor.BLACK)) {
      chess_board.drawBlack(board.out);
    }
    else {
      chess_board.drawWhite(board.out);
    }
    }



  public String Leave() throws DataAccessException{
    String output="\n";
    try{
      ListgameResponse response = server.listGame();
      for (Games game : response.games()){
        output+= ("Name: "+ game.gameName() +" ID: " + game.gameID()+ " WhitePlayer: " + game.whiteUsername() + " BlackPlayer: " + game.blackUsername());
        output +="\n";
      }
      return output;
    }
    catch(DataAccessException ex){
      throw ex;
    }

  }
  public String MakeMove(String[] params) throws DataAccessException{
    try{
      server.joinGame(params);
      chess_board board = new chess_board();
      board.main();
      return "You joined a game";
    }
    catch(DataAccessException ex){
      throw ex;
    }

  }
  public String Resign(String[] params) throws DataAccessException{
    chess_board board = new chess_board();
    board.main();
    return "";

  }
  public String Valid_moves() {
    try{

      server.logout();
      Repl.state = state.Pre_login;
      return "logout successfully";
    }
    catch(DataAccessException ex){
      return ex.getMessage();
    }

  }
  public String clear() throws DataAccessException{
    try{
      server.clear();
      Repl.state = state.Pre_login;
      return "clear DB";
    }
    catch(DataAccessException ex){
      throw ex;
    }

  }


}
}
