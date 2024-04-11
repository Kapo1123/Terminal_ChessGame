package Websocket;

import com.google.gson.Gson;
import serverMessages_classes.ErrorMessage;
import serverMessages_classes.LoadGame;
import serverMessages_classes.Notification;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
  public final ConcurrentHashMap<Integer, List<Connection>> connections=new ConcurrentHashMap<>();

  public void add(Integer gameID, Connection connect) {
    if(connections.get(gameID) == (null)){
      connections.put(gameID,new ArrayList<>());
      connections.get(gameID).add(connect);
    }
    else{
      connections.get(gameID).add(connect);
    }
  }

  public void remove(Integer gameID, Connection connect) {
    List<Connection> connectionList = connections.get(gameID);
    if (connectionList != null) {
      connectionList.remove(connect);
      if (connectionList.isEmpty()) {
        connections.remove(gameID); // Remove the entry if the list becomes empty
      }
    }
  }

  public void removeGameid(Integer gameID) {
    connections.remove(gameID);
  }

  public void broadcast(Integer gameID, String excludeAuthtoken, Notification notification) throws IOException {
    for (var c : connections.get(gameID)) {
      if (c.session.isOpen()) {
        if (!c.authtoken.equals(excludeAuthtoken)) {
          c.send(new Gson().toJson(notification));
        }
      }
    }

    // Clean up any connections that were left open.
  }
  public void sendGame(Integer gameID, String excludeAuthtoken, LoadGame notification) throws IOException {
    for (var c : connections.get(gameID)) {
      if (c.session.isOpen()) {
        if (!c.authtoken.equals(excludeAuthtoken)) {
          c.send(new Gson().toJson(notification));
        }
      }
    }

    // Clean up any connections that were left open.
  }

  public void sendOne(Integer gameID, String excludeAuthtoken, LoadGame notification) throws IOException {
    for (var c : connections.get(gameID)) {
      if (c.session.isOpen()) {
        if (c.authtoken.equals(excludeAuthtoken)) {
          c.send(new Gson().toJson(notification));
        }
      }
    }

    // Clean up any connections that were left open.
  }

  public void sendError(Integer gameID, Connection session, ErrorMessage error) throws IOException {
      var c = session;
      if (c.session.isOpen()) {
          c.send(new Gson().toJson(error));
        }
      }
    }

    // Clean up any connections that were left open.



