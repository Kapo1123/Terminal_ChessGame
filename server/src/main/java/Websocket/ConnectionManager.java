package Websocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import serverMessages_classes.Error_message;
import serverMessages_classes.Load_Game;
import serverMessages_classes.Notification;
import webSocketMessages.serverMessages.ServerMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
  public final ConcurrentHashMap<Integer, List<Connection>> connections=new ConcurrentHashMap<>();

  public void add(Integer GameID, Connection connect) {
    if(connections.get(GameID) == (null)){
      connections.put(GameID,new ArrayList<>());
      connections.get(GameID).add(connect);
    }
    else{
      connections.get(GameID).add(connect);
    }
  }

  public void remove(Integer GameID, Connection connect) {
    List<Connection> connectionList = connections.get(GameID);
    if (connectionList != null) {
      connectionList.remove(connect);
      if (connectionList.isEmpty()) {
        connections.remove(GameID); // Remove the entry if the list becomes empty
      }
    }
  }

  public void remove_gameid(Integer GameID) {
    connections.remove(GameID);
  }

  public void broadcast(Integer GameID, String excludeAuthtoken, Notification notification) throws IOException {
    for (var c : connections.get(GameID)) {
      if (c.session.isOpen()) {
        if (!c.Authtoken.equals(excludeAuthtoken)) {
          c.send(new Gson().toJson(notification));
        }
      }
    }

    // Clean up any connections that were left open.
  }

  public void send_one(Integer GameID, String excludeAuthtoken, Load_Game notification) throws IOException {
    for (var c : connections.get(GameID)) {
      if (c.session.isOpen()) {
        if (c.Authtoken.equals(excludeAuthtoken)) {
          c.send(new Gson().toJson(notification));
        }
      }
    }

    // Clean up any connections that were left open.
  }

  public void send_error(Integer GameID, String excludeAuthtoken, Error_message error) throws IOException {
    for (var c : connections.get(GameID)) {
      if (c.session.isOpen()) {
        if (c.Authtoken.equals(excludeAuthtoken)) {
          c.send(new Gson().toJson(error));
        }
      }
    }

    // Clean up any connections that were left open.

  }
}
