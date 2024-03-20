package client;

import java.net.URL;
import java.util.Scanner;

import static ui.EscapeSequences.*;

public class Repl {
  static  state state;
  String url;
  Pre_login preLogin = new Pre_login();
  Post_login postLogin = new Post_login();
  Game_UI gameui = new Game_UI();
  public Repl(String serverUrl) {
    state = client.state.Pre_login;
    url = serverUrl;
  }
  public void run(){
    System.out.println("\uD83D\uDC36 Welcome to the chess game. Sign in to start.");
    System.out.print(preLogin.help());

    Scanner scanner = new Scanner(System.in);
    var result = "";
    while (!result.equals("quit")) {
      printPrompt();
      String line = scanner.nextLine();

      try {
        if (state == client.state.Pre_login) {
          result=preLogin.eval(line,url);
        }
        else if (state == client.state.Post_login ){
          result=postLogin.eval(line,url);
        }
        else if (state == client.state.Game_UI) {
          result = gameui.eval(line,url);
        }

        System.out.print(SET_BG_COLOR_BLUE + result);
      } catch (Throwable e) {
        var msg = e.toString();
        System.out.print(msg);
      }
    }
    System.out.println();
  }

  private void printPrompt() {
    System.out.print("\n" + SET_TEXT_BOLD + ">>> " + SET_BG_COLOR_GREEN);
  }


}
