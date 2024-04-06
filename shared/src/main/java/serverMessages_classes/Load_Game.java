package serverMessages_classes;

import chess.ChessBoard;
import chess.ChessGame;
import webSocketMessages.serverMessages.ServerMessage;

public class Load_Game extends ServerMessage {
  private String game_board; // Additional field for notification message content
  public Load_Game(String board) {
    super(ServerMessageType.LOAD_GAME); // Call superclass constructor
    this.game_board = board;
  }

  // Additional methods if needed
  public String getmessage() {
    return game_board;
  }

  public void setMessageContent(String messageContent) {
    this.game_board = messageContent;
  }
}