package client;

import Responseclass.Games;
import Responseclass.ListgameResponse;
import chess.ChessGame;
import dataAccessError.DataAccessException;
import websocket.NotificationHandler;
import websocket.WebSocketFacade;

import java.util.Arrays;

import static client.Repl.gameUi;
import static client.ServerFacade.authtoken;
import static java.lang.Integer.parseInt;

public class PostLogin {
  private NotificationHandler notificationHandler;

  public PostLogin(NotificationHandler notificationHandler){
    this.notificationHandler = notificationHandler;
  }
  ServerFacade server = new ServerFacade(Repl.url);
  public String eval(String input) {
    try {
      var tokens = input.toLowerCase().split(" ");
      var cmd = (tokens.length > 0) ? tokens[0] : "help";
      var params = Arrays.copyOfRange(tokens, 1, tokens.length);
      return switch (cmd) {
        case "create" -> create(params);
        case "list" -> listGame();
        case "join" -> join(params);
        case "observe" -> observe(params);
        case "logout" -> logout();
        case "clear" -> clear();
        case "quit" -> "quit";
        default -> help();
      };
    } catch (DataAccessException ex) {
      return ex.getMessage();
    }
  }
  public String help(){
    String helpText = """
                    
                    - create <NAME> -a game
                    - list -games
                    - join <ID> [WHITE|BLACK|<empty>] -a game
                    - observe <ID> - a game
                    - logout - when you are done
                    - quit - playing chess
                    - help - with possible commands
                    """;
    return helpText;
  }
  public String create(String[] params) throws DataAccessException{
    try{
      var response = server.createGame(params);
      return "Your GameID is " + response.gameID();
    }
    catch(DataAccessException ex){
      throw ex;
    }

  }
  public String listGame() throws DataAccessException{
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
  public String join(String[] params) throws DataAccessException{
    try{
        server.joinGame(params);
//        chess_board board = new chess_board();
//        board.main();
        Integer gameID = parseInt(params[0]);
        ChessGame.TeamColor playerColor =null;
        if (params[1].equalsIgnoreCase("white")) {
           playerColor=(ChessGame.TeamColor.WHITE);
        }
        else{
           playerColor =(ChessGame.TeamColor.BLACK);
        }
      gameUi.board.main();
      WebSocketFacade webserver = new WebSocketFacade(Repl.url,this.notificationHandler, authtoken);
      webserver.joinPlayer(gameID,playerColor);

      gameUi.color = playerColor;
      gameUi.gameId= gameID;
      gameUi.server = webserver;
      Repl.state = State.Game_UI;
        return "You joined a game";
    }
    catch(DataAccessException ex){
        throw ex;
    }

  }
  public String observe(String[] params) throws DataAccessException{
    try {
      Integer gameID=parseInt(params[0]);
      ChessGame.TeamColor playerColor=null;
      WebSocketFacade webserver=new WebSocketFacade(Repl.url, this.notificationHandler, authtoken);
      webserver.joinObserver(gameID);
      gameUi.color = playerColor;
      gameUi.gameId= gameID;
      gameUi.server = webserver;
      Repl.state = State.Game_UI;
    } catch(DataAccessException ex){
      throw ex;
    }
    return "";

  }
  public String logout() {
    try{

       server.logout();
       Repl.state = State.Pre_login;
      return "logout successfully";
    }
    catch(DataAccessException ex){
      return ex.getMessage();
    }

  }
  public String clear() throws DataAccessException{
    try{
       server.clear();
      Repl.state = State.Pre_login;
      return "clear DB";
    }
    catch(DataAccessException ex){
      throw ex;
    }

  }


}
