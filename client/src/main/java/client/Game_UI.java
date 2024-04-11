package client;

import chess.*;
import dataAccessError.DataAccessException;
import ui.chess_board;

import java.util.Arrays;
import java.util.Scanner;

import static java.lang.Integer.TYPE;
import static java.lang.Integer.parseInt;

public class Game_UI {
  static websocket.WebSocketFacade server;
  public static ChessGame.TeamColor color;
  static Integer GameId;
  public static ChessGame game;
  public static chess_board board = new chess_board();
//  board.main();
  public String eval(String input) {

    try {
      var tokens = input.toLowerCase().split(" ");
      var cmd = (tokens.length > 0) ? tokens[0] : "help";
      var params = Arrays.copyOfRange(tokens, 1, tokens.length);
      return switch (cmd) {
        case "redraw" -> redraw();
        case "leave" -> Leave(params);
        case "makemove" -> MakeMove(params);
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
                    - MakeMove rowcol rowcol type ex: 74 47 type
                    - Resign 
                    - Valid_moves rowcol ex 13
                    - Help 
                    """;
    return help_text;
  }
  public String redraw() throws DataAccessException{

    if (color == null){
      chess_board.drawWhite(board.out,null,null);
    }

    else if (color.equals( ChessGame.TeamColor.BLACK)) {
      chess_board.drawBlack(board.out,null,null);
    }
    else {
      chess_board.drawWhite(board.out,null,null);
    }
    return "";
    }



  public String Leave(String[] params) throws DataAccessException{
    String output="\n";
    try{
      server.Leave(GameId,color);
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
      if (color == null) {
        return "Observer can not make a move";
      }
      if (!color.equals(game.getTeamTurn())) {
        return "It is not your turn";
      }
      var start_position = new ChessPosition(parseInt(start[0]),parseInt(start[1]));
      var color = game.getTeamTurn();
      var end = params[1].toLowerCase().split("");
      var end_position = new ChessPosition(parseInt(end[0]),parseInt(end[1]));
      var chesemove = new ChessMove(start_position,end_position,null);
      if (params[2] == null) {}
      else if (params[2].equalsIgnoreCase(ChessPiece.PieceType.BISHOP.toString())){chesemove = new ChessMove(start_position,end_position, ChessPiece.PieceType.BISHOP);}
      else if (params[2].equalsIgnoreCase(ChessPiece.PieceType.QUEEN.toString())){chesemove = new ChessMove(start_position,end_position, ChessPiece.PieceType.QUEEN);}
      else if (params[2].equalsIgnoreCase(ChessPiece.PieceType.KNIGHT.toString())){chesemove = new ChessMove(start_position,end_position, ChessPiece.PieceType.KNIGHT);}
      else if (params[2].equalsIgnoreCase(ChessPiece.PieceType.ROOK.toString())){chesemove = new ChessMove(start_position,end_position, ChessPiece.PieceType.ROOK);}
      try{

        game.makeMove(chesemove);

      } catch (InvalidMoveException e) {
        return "You can not make move";
      }
      server.MakeMove(GameId,chesemove);
      return "";
    }
    catch(DataAccessException ex){
      throw ex;
    }

  }
  public String Resign(String[] params) throws DataAccessException{

    if (color == null) {
      return "Observer can not resign";
    }

    while (true) {
      System.out.println("You are resigning the game  \"yes\" to continue and \"no\" to return");
      Scanner scanner=new Scanner(System.in);
      String line=scanner.nextLine();
      if (line.toLowerCase().equals( "yes")) {
        server.Resign(GameId);
        Repl.state = state.Post_login;
        return "You have successfully left the game";
      }else if (line.toLowerCase().equals( "no")) {
        return "";
      }
    }

  }
  public String Valid_moves(String[] params) {
    var start = params[0].toLowerCase().split("");
    var list = game.validMoves(new ChessPosition(parseInt(start[0]),parseInt(start[1])));
    board.highlight(board.out,color,list);
    return "";
  }


}

