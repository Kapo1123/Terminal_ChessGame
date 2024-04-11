package User_game_commands;

import chess.ChessGame;
import chess.ChessMove;
import webSocketMessages.userCommands.UserGameCommand;

public class Leave extends UserGameCommand {
  public Integer gameID;
  public ChessGame.TeamColor color;
  public Leave(String authToken, Integer gameID, ChessGame.TeamColor color) {
//    super(CommandType.JOIN_PLAYER); // Call superclass constructor
    super(authToken);
    super.commandType = CommandType.LEAVE;
    this.gameID = gameID;
    this.color = color;
  }
}
