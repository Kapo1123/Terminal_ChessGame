package Websocket;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;

import java.io.IOException;

public class Connection {
  public String Authtoken;
  public Session session;

  public Connection(String Authtoken, Session session) {
    this.Authtoken = Authtoken;
    this.session = session;
  }
  @OnWebSocketMessage
  public void send(String msg) throws IOException {
    session.getRemote().sendString(msg);
  }
}
