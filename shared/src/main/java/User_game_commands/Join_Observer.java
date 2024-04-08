package User_game_commands;

import chess.ChessGame;
import webSocketMessages.userCommands.UserGameCommand;

public class Join_Observer extends UserGameCommand {
  private String messageContent; // Additional field for notification message content
  Integer gameID;
  public Join_Observer(String authToken, Integer GameID ) {
//    super(CommandType.JOIN_PLAYER); // Call superclass constructor
    super(authToken);
    super.commandType = UserGameCommand.CommandType.JOIN_OBSERVER;
    this.gameID = GameID;

  }
}
