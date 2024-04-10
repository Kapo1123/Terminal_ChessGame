package serverMessages_classes;

import webSocketMessages.serverMessages.ServerMessage;

public class Error_message extends ServerMessage {
  private String errorMessage; // Additional field for notification message content

  public Error_message(String messageContent) {
    super(ServerMessageType.ERROR); // Call superclass constructor
    this.errorMessage = messageContent;
  }

  // Additional methods if needed
  public String getmessage() {
    return errorMessage;
  }

  public void setMessageContent(String messageContent) {
    this.errorMessage = messageContent;
  }
}