package User_game_commands;

import chess.ChessGame;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;

public class Join_Player extends UserGameCommand {
  private String messageContent; // Additional field for notification message content
  Integer gameID;
  ChessGame.TeamColor color;
  public Join_Player(String authToken, Integer GameID, ChessGame.TeamColor color ) {
//    super(CommandType.JOIN_PLAYER); // Call superclass constructor
    super(authToken);
    super.commandType = CommandType.JOIN_PLAYER;
    this.gameID = GameID;
    this.color = color;

  }


  // Additional methods if needed

}