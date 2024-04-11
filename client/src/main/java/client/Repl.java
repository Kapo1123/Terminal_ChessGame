package client;

import webSocketMessages.serverMessages.ServerMessage;
import websocket.NotificationHandler;

import java.util.Scanner;

import static ui.EscapeSequences.*;

public class Repl implements NotificationHandler{
  static  state state;
  static String  url;
  Pre_login preLogin;
  Post_login postLogin;
  static Game_UI gameUi = new Game_UI();

    public Repl(String serverUrl)
    {
      state=client.state.Pre_login;
      url=serverUrl;
      preLogin=new Pre_login();
      postLogin=new Post_login(this);
    }

  public void run(){
    System.out.println("\uD83D\uDC36 Welcome to the chess game. Sign in to start.");

    Scanner scanner = new Scanner(System.in);
    var result = "";
    while (!result.equals("quit")) {

      if (state == client.state.Pre_login){
        System.out.print(preLogin.help());
      }
      else if (state == client.state.Post_login){
        System.out.print(postLogin.help());
      }
      printPrompt();
      String line = scanner.nextLine();
      try {
        if (state == client.state.Pre_login) {

          result=preLogin.eval(line);
        }
        else if (state == client.state.Post_login ){
          result=postLogin.eval(line);
        }
        else if (state == client.state.Game_UI) {
          result = gameUi.eval(line);
        }
        if (result.equals("quit")){
          break;
        }

        System.out.print(  result);
      } catch (Throwable e) {
        var msg = e.toString();
        System.out.print(msg);
      }
    }
    System.out.println();
  }




  public static void printPrompt() {
    System.out.print("\n" + SET_TEXT_BOLD +SET_TEXT_COLOR_RED+ ">>>");
    System.out.print("\u001B[49m");
    System.out.print(SET_TEXT_COLOR_WHITE);
    System.out.print(SET_TEXT_BOLD);
  }


  @Override
  public void notify(String notification) {
    System.out.println(notification);
    printPrompt();
  }
}
