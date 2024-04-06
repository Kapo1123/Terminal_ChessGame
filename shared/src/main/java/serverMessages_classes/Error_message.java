package serverMessages_classes;

import webSocketMessages.serverMessages.ServerMessage;

public class Error_message extends ServerMessage {
  private String messageContent; // Additional field for notification message content

  public Error_message(String messageContent) {
    super(ServerMessageType.ERROR); // Call superclass constructor
    this.messageContent = messageContent;
  }

  // Additional methods if needed
  public String getmessage() {
    return messageContent;
  }

  public void setMessageContent(String messageContent) {
    this.messageContent = messageContent;
  }
}