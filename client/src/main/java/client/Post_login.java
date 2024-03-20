package client;

import dataAccess.DataAccessException;

import java.util.Arrays;

public class Post_login {
  public String url;
  public String eval(String input, String url) {
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
        case "quit" -> "quit";
        default -> help();
      };
    } catch (DataAccessException ex) {
      return ex.getMessage();
    }
  }
  public String help(){
    String help_text = """
                    - create <NAME> -a game
                    - list -games
                    - join <ID> [WHITE|BLACK|<empty>] -a game
                    - observe <ID> - a game
                    - logout - when you are done
                    - quit - playing chess
                    - help - with possible commands
                    """;
    return help_text;
  }
  public String create(String[] params) throws DataAccessException{
    try{
      ServerFacade server = new ServerFacade(url);
      var response = server.creategame(params);
      return response;
    }
    catch(DataAccessException ex){
      throw ex;
    }

  }
  public String listGame(String[] params) throws DataAccessException{
    try{
      ServerFacade server = new ServerFacade(url);
      var response = server.listGame(params);
      return response;
    }
    catch(DataAccessException ex){
      throw ex;
    }

  }
  public String join(String[] params) throws DataAccessException{
    try{
      ServerFacade server = new ServerFacade(url);
      var response = server.joinGame(params);
      return response;
    }
    catch(DataAccessException ex){
      throw ex;
    }

  }
  public String observe(String[] params) throws DataAccessException{
    return "";

  }
  public String logout() throws DataAccessException{
    try{
      ServerFacade server = new ServerFacade(url);
      var response = server.logout();
      return response;
    }
    catch(DataAccessException ex){
      throw ex;
    }

  }


}
