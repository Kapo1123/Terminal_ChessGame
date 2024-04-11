package Websocket;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;

import java.io.IOException;

public class Connection {
  public String authtoken;
  public Session session;

  public Connection(String authtoken, Session session) {
    this.authtoken= authtoken;
    this.session = session;
  }
  @OnWebSocketMessage
  public void send(String msg) throws IOException {
    session.getRemote().sendString(msg);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Connection that = (Connection) obj;
    return authtoken.equals(that.authtoken) && session.equals(that.session);
  }
}
