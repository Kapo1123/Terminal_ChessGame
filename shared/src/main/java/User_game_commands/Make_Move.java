package User_game_commands;

import chess.ChessMove;
import webSocketMessages.userCommands.UserGameCommand;

public class Make_Move extends UserGameCommand {
  Integer gameID;
  ChessMove move;
  public Make_Move(String authToken, Integer GameID, ChessMove move) {
//    super(CommandType.JOIN_PLAYER); // Call superclass constructor
    super(authToken);
    super.commandType = CommandType.MAKE_MOVE;
    this.gameID = GameID;
    move =move;
  }
}
