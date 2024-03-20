package client;

import dataAccess.DataAccessException;

import java.util.Arrays;

public class Pre_login {
  String url;
  public String eval(String input, String url) {
    try {
      url = url;
      var tokens = input.toLowerCase().split(" ");
      var cmd = (tokens.length > 0) ? tokens[0] : "help";
      var params = Arrays.copyOfRange(tokens, 1, tokens.length);
      return switch (cmd) {
        case "register" -> register(params);
        case "login" -> login(params);
        case "quit" -> "quit";
        default -> help();
      };
    } catch (DataAccessException ex) {
      return ex.getMessage();
    }
  }
  public String help(){
    String help_text = """
                    - register <USERNAME> <PASSWORD> <EMAIL> - to create an account
                    - login <USERNAME> <PASSWORD> - to play chess
                    - help
                    - quit
                    """;
    return help_text;
  }
  public String register(String[] params) throws DataAccessException{
  try{
    ServerFacade server = new ServerFacade(url);
    server.register(params);

  }


  }

  public String login(String[] params) throws DataAccessException{

  }

}
