package User_game_commands;

import chess.ChessMove;
import webSocketMessages.userCommands.UserGameCommand;

public class MakeMove extends UserGameCommand {
  public Integer gameID;
  public ChessMove move;
  public MakeMove(String authToken, Integer gameId, ChessMove move) {
//    super(CommandType.JOIN_PLAYER); // Call superclass constructor
    super(authToken);
    super.commandType = CommandType.MAKE_MOVE;
    this.gameID = gameId;
    this.move =move;
  }
}
