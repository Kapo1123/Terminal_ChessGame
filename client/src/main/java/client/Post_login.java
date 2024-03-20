package client;

import dataAccess.DataAccessException;

import java.util.Arrays;

public class Post_login {
  public String URL;
  public String eval(String input, String url) {
    try {
      var tokens = input.toLowerCase().split(" ");
      var cmd = (tokens.length > 0) ? tokens[0] : "help";
      var params = Arrays.copyOfRange(tokens, 1, tokens.length);
      return switch (cmd) {
        case "create" -> create(params);
        case "list" -> listPets();
        case "join" -> join(params);
        case "observe" -> listPets(params);
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
}
