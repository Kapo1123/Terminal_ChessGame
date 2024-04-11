package User_game_commands;

import webSocketMessages.userCommands.UserGameCommand;

public class JoinObserver extends UserGameCommand {
  private String messageContent; // Additional field for notification message content
  public Integer gameID;
  public JoinObserver(String authToken, Integer gameid ) {
//    super(CommandType.JOIN_PLAYER); // Call superclass constructor
    super(authToken);
    super.commandType = UserGameCommand.CommandType.JOIN_OBSERVER;
    this.gameID = gameid;

  }
}
