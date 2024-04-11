package serverMessages_classes;

import chess.ChessGame;
import com.google.gson.Gson;
import webSocketMessages.serverMessages.ServerMessage;

public class LoadGame extends ServerMessage {
  public ChessGame game; // Additional field for notification message content
  public LoadGame(ChessGame game) {
    super(ServerMessageType.LOAD_GAME); // Call superclass constructor
    this.game = game;
  }

  // Additional methods if needed
  public String getmessage() {
    return new Gson().toJson(game.getBoard());
  }

}