package serverMessages_classes;

import webSocketMessages.serverMessages.ServerMessage;

public class Notification extends ServerMessage {
  private String message; // Additional field for notification message content

  public Notification(String messageContent) {
    super(ServerMessageType.NOTIFICATION); // Call superclass constructor
    this.message = messageContent;
  }

  // Additional methods if needed
  public String getmessage() {
    return message;
  }

  public void setMessageContent(String messageContent) {
    this.message = messageContent;
  }
}

