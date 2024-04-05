package serverMessages;

import webSocketMessages.serverMessages.ServerMessage;

public class Notification extends ServerMessage {
  private String messageContent; // Additional field for notification message content

  public Notification(String messageContent) {
    super(ServerMessageType.NOTIFICATION); // Call superclass constructor
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

