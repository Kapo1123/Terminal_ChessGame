package client;

import chess.*;
import dataAccessError.DataAccessException;
import ui.ChessBoard;

import java.util.Arrays;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class GameUI {
  static websocket.WebSocketFacade server;
  public static ChessGame.TeamColor color;
  static Integer gameId;
  public static ChessGame game;
  public static ChessBoard board = new ChessBoard();
//  board.main();
  public String eval(String input) {

    try {
      var tokens = input.toLowerCase().split(" ");
      var cmd = (tokens.length > 0) ? tokens[0] : "help";
      var params = Arrays.copyOfRange(tokens, 1, tokens.length);
      return switch (cmd) {
        case "redraw" -> redraw();
        case "leave" -> leave(params);
        case "makemove" -> makeMove(params);
        case "resign" -> resign(params);
        case "valid_moves" -> validMoves(params);
        default -> help();
      };
    } catch (DataAccessException ex) {
      return ex.getMessage();
    }
  }
  public String help(){
    String helpText = """
                    
                    - redraw  -a board
                    - Leave -return to main meau
                    - MakeMove rowcol rowcol type ex: 74 47 type
                    - Resign 
                    - Valid_moves rowcol ex 13
                    - Help 
                    """;
    return helpText;
  }
  public String redraw() throws DataAccessException{

    if (color == null){
      ChessBoard.drawWhite(board.out,null,null);
    }

    else if (color.equals( ChessGame.TeamColor.BLACK)) {
      ChessBoard.drawBlack(board.out,null,null);
    }
    else {
      ChessBoard.drawWhite(board.out,null,null);
    }
    return "";
    }



  public String leave(String[] params) throws DataAccessException{
    String output="\n";
    try{
      server.leave(gameId,color);
      Repl.state = State.Post_login;
      return "You have successfully left the game";
    }
    catch(DataAccessException ex){
      throw ex;
    }

  }
  public String makeMove(String[] params) throws DataAccessException{
    try{
      var start = params[0].toLowerCase().split("");
      if (color == null) {
        return "Observer can not make a move";
      }
      if (!color.equals(game.getTeamTurn())) {
        return "It is not your turn";
      }
      var startPosition = new ChessPosition(parseInt(start[0]),parseInt(start[1]));
      var color = game.getTeamTurn();
      var end = params[1].toLowerCase().split("");
      var endPosition = new ChessPosition(parseInt(end[0]),parseInt(end[1]));
      var chesemove = new ChessMove(startPosition,endPosition,null);
      if (params[2] == null) {}
      else if (params[2].equalsIgnoreCase(ChessPiece.PieceType.BISHOP.toString())){chesemove = new ChessMove(startPosition,endPosition, ChessPiece.PieceType.BISHOP);}
      else if (params[2].equalsIgnoreCase(ChessPiece.PieceType.QUEEN.toString())){chesemove = new ChessMove(startPosition,endPosition, ChessPiece.PieceType.QUEEN);}
      else if (params[2].equalsIgnoreCase(ChessPiece.PieceType.KNIGHT.toString())){chesemove = new ChessMove(startPosition,endPosition, ChessPiece.PieceType.KNIGHT);}
      else if (params[2].equalsIgnoreCase(ChessPiece.PieceType.ROOK.toString())){chesemove = new ChessMove(startPosition,endPosition, ChessPiece.PieceType.ROOK);}
      try{

        game.makeMove(chesemove);

      } catch (InvalidMoveException e) {
        return "You can not make move";
      }
      server.makeMove(gameId,chesemove);
      return "";
    }
    catch(DataAccessException ex){
      throw ex;
    }

  }
  public String resign(String[] params) throws DataAccessException{

    if (color == null) {
      return "Observer can not resign";
    }

    while (true) {
      System.out.println("You are resigning the game  \"yes\" to continue and \"no\" to return");
      Scanner scanner=new Scanner(System.in);
      String line=scanner.nextLine();
      if (line.toLowerCase().equals( "yes")) {
        server.resign(gameId);
        Repl.state = State.Post_login;
        return "You have successfully left the game";
      }else if (line.toLowerCase().equals( "no")) {
        return "";
      }
    }

  }
  public String validMoves(String[] params) {
    var start = params[0].toLowerCase().split("");
    var list = game.validMoves(new ChessPosition(parseInt(start[0]),parseInt(start[1])));
    board.highlight(board.out,color,list);
    return "";
  }


}

