package client;

import Responseclass.Games;
import Responseclass.ListgameResponse;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;
import chess.InvalidMoveException;
import dataAccess.DataAccessException;
import ui.chess_board;

import java.util.Arrays;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Game_UI {
  static websocket.WebSocketFacade server;
  static ChessGame.TeamColor color;
  static Integer GameId;
  static ChessGame game;
  chess_board board = new chess_board(game);
  public String eval(String input) {

    try {
      var tokens = input.toLowerCase().split(" ");
      var cmd = (tokens.length > 0) ? tokens[0] : "help";
      var params = Arrays.copyOfRange(tokens, 1, tokens.length);
      return switch (cmd) {
        case "redraw" -> redraw();
        case "leave" -> Leave(params);
        case "makeMove" -> MakeMove(params);
        case "resign" -> Resign(params);
        case "valid_moves" -> Valid_moves(params);
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
                    - Valid_moves rowcol ex 13
                    - Help 
                    """;
    return help_text;
  }
  public String redraw() throws DataAccessException{

    if (color.equals( ChessGame.TeamColor.BLACK)) {
      chess_board.drawBlack(board.out);
    }
    else {
      chess_board.drawWhite(board.out);
    }
    return "";
    }



  public String Leave(String[] params) throws DataAccessException{
    String output="\n";
    try{
      server.Leave(GameId);
      Repl.state = state.Post_login;
      return "You have successfully left the game";
    }
    catch(DataAccessException ex){
      throw ex;
    }

  }
  public String MakeMove(String[] params) throws DataAccessException{
    try{
      var start = params[0].toLowerCase().split("");
      var start_position = new ChessPosition(parseInt(start[0]),parseInt(start[1]));
      var end = params[1].toLowerCase().split("");
      var end_position = new ChessPosition(parseInt(end[0]),parseInt(end[1]));
      var chesemove = new ChessMove(start_position,end_position,null);
      try{

        game.makeMove(chesemove);

      } catch (InvalidMoveException e) {
        return e.getMessage();
      }
      server.MakeMove(GameId,chesemove);
      return redraw();
    }
    catch(DataAccessException ex){
      throw ex;
    }

  }
  public String Resign(String[] params) throws DataAccessException{

    while (true) {
      System.out.println("You are resigning the game  \"yes\" to continue and \"no\" to return");
      Scanner scanner=new Scanner(System.in);
      String line=scanner.nextLine();
      if (line.toLowerCase() == "yes") {
        server.Resign(GameId);
        Repl.state = state.Post_login;
        return "You have successfully left the game";
      }else if (line.toLowerCase() == "no") {
        return "";
      }
    }

  }
  public String Valid_moves(String[] params) {
    var start = params[0].toLowerCase().split("");
    var list = game.validMoves(new ChessPosition(parseInt(start[0]),parseInt(start[1])));
    return "";

  }



}

