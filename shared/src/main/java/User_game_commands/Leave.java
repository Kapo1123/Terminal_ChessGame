package User_game_commands;

import chess.ChessMove;
import webSocketMessages.userCommands.UserGameCommand;

public class Leave extends UserGameCommand {
  Integer gameID;
  public Leave(String authToken, Integer GameID) {
//    super(CommandType.JOIN_PLAYER); // Call superclass constructor
    super(authToken);
    super.commandType = CommandType.LEAVE;
    this.gameID = GameID;
  }
}
