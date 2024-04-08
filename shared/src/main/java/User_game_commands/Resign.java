package User_game_commands;

import chess.ChessMove;
import webSocketMessages.userCommands.UserGameCommand;

public class Resign extends UserGameCommand{
  Integer gameID;
  public Resign(String authToken, Integer GameID) {
//    super(CommandType.JOIN_PLAYER); // Call superclass constructor
    super(authToken);
    super.commandType = CommandType.RESIGN;
    this.gameID = GameID;
  }
}

