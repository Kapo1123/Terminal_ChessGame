package User_game_commands;

import chess.ChessGame;
import webSocketMessages.userCommands.UserGameCommand;

public class JoinPlayer extends UserGameCommand {
  private String messageContent; // Additional field for notification message content
  public Integer gameID;
  public ChessGame.TeamColor playerColor;
  public JoinPlayer(String authToken, Integer gameID, ChessGame.TeamColor color ) {
//    super(CommandType.JOIN_PLAYER); // Call superclass constructor
    super(authToken);
    super.commandType = CommandType.JOIN_PLAYER;
    this.gameID = gameID;
    this.playerColor = color;

  }


  // Additional methods if needed

}