package serverMessages_classes;

import webSocketMessages.serverMessages.ServerMessage;

public class ErrorMessage extends ServerMessage {
  private String errorMessage; // Additional field for notification message content

  public ErrorMessage(String messageContent) {
    super(ServerMessageType.ERROR); // Call superclass constructor
    this.errorMessage = messageContent;
  }

  // Additional methods if needed
  public String getmessage() {
    return errorMessage;
  }

}