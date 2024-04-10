package server.websocket;

import Requestclasses.Authtoken;
import Requestclasses.Joingamerequest;
import User_game_commands.*;
import Websocket.Connection;
import Websocket.ConnectionManager;
import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.MysqlAuthDao;
import dataAccess.MysqlGameDao;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import serverMessages_classes.Error_message;
import serverMessages_classes.Load_Game;
import serverMessages_classes.Notification;

import webSocketMessages.userCommands.UserGameCommand;

import java.io.IOException;
import java.util.Timer;


@WebSocket
public class WebSocketHandler {
  MysqlGameDao game = new MysqlGameDao();
  MysqlAuthDao auth = new MysqlAuthDao();
  private final ConnectionManager connections = new ConnectionManager();

  @OnWebSocketMessage
  public void onMessage(Session session, String message) throws DataAccessException, IOException {
    UserGameCommand action = new Gson().fromJson(message, UserGameCommand.class);
    switch (action.getCommandType()) {
      case JOIN_PLAYER ->  Join_Player(session,message);
      case JOIN_OBSERVER ->  Join_Observer(session,message);
      case MAKE_MOVE ->  Make_Move(session,message);
      case LEAVE ->  Leave(session,message);
//      case RESIGN -> new Resign(session,message);
    }
  }

  private void Join_Player( Session session, String message) throws DataAccessException, IOException {
    Join_Player action = new Gson().fromJson(message, Join_Player.class);
    Connection connect = new Connection(action.getAuthString(),session);
    connections.add(action.gameID, connect);
    String color ="";
    if (action.playerColor == null){
      throw new DataAccessException("color can't be empty");
    }
    if (action.playerColor.equals(ChessGame.TeamColor.WHITE)){
      color = "white";
    }
    else{
      color = "black";
    }
    try {
      if(!auth.isValid(new Authtoken(action.getAuthString()))){
        throw new DataAccessException("Error:not valid authtoken");
      }
      String visitorName= auth.getUserName(new Authtoken(action.getAuthString()));
      game.check_gameID(action.gameID,action.playerColor,visitorName);
      ChessGame chess_game = game.getGame(action.gameID);
      var return_message = String.format("%s has joined the game as %s player", visitorName,color);
      var notification = new Notification(return_message);
      Load_Game load = new Load_Game(chess_game);
      connections.broadcast(action.gameID,action.getAuthString(), notification);
      connections.send_one(action.gameID,action.getAuthString(),load);
    }
    catch (DataAccessException | IOException e) {
       Error_message error_message = new Error_message(e.getMessage());
       connections.send_error(action.gameID,action.getAuthString(),error_message);
    }
  }

  private void Join_Observer(Session session, String message) throws DataAccessException, IOException {
    Join_Observer action = new Gson().fromJson(message, Join_Observer.class);
    Connection connect = new Connection(action.getAuthString(),session);
    connections.add(action.gameID, connect);
//    connections.remove(visitorName);
    try {
      if(!auth.isValid(new Authtoken(action.getAuthString()))){
        throw new DataAccessException("Error:not valid authtoken");
      }
      String visitorName= auth.getUserName(new Authtoken(action.getAuthString()));
      ChessGame chess_game = game.getGame(action.gameID);
      var return_message = String.format("%s has joined the game as ", visitorName);
      var notification = new Notification(return_message);
      Load_Game load = new Load_Game(chess_game);
      connections.broadcast(action.gameID,action.getAuthString(), notification);
      connections.send_one(action.gameID,action.getAuthString(),load);
    }
    catch (DataAccessException | IOException e) {
      Error_message error_message = new Error_message(e.getMessage());
      connections.send_error(action.gameID,action.getAuthString(),error_message);
    }

  }

  public void Make_Move(Session session, String message) throws DataAccessException {
    Make_Move action = new Gson().fromJson(message, Make_Move.class);



//    try {
//      var message = String.format("%s says %s", petName, sound);
//      var notification = new Notification(Notification.Type.NOISE, message);
//      connections.broadcast("", notification);
//    } catch (Exception ex) {
//      throw new ResponseException(500, ex.getMessage());
//    }
  }
  private void Leave(Session session, String message) throws DataAccessException, IOException {
    Leave action = new Gson().fromJson(message, Leave.class);
    Connection connect = new Connection(action.getAuthString(),session);
    connections.remove(action.gameID, connect);
    try {
      String visitorName=auth.getUserName(new Authtoken(action.getAuthString()));
      if (action.color != null){
        game.leave_player(action.gameID,action.color);
      }
      connections.remove(action.gameID,connect);
      var return_message=String.format("%s has left the game ", visitorName);
      var notification=new Notification(return_message);
      connections.broadcast(action.gameID, action.getAuthString(), notification);
    }catch (DataAccessException | IOException e) {
      Error_message error_message = new Error_message(e.getMessage());
      connections.send_error(action.gameID,action.getAuthString(),error_message);
    }

  }
  private void Resign(Session session, String message) throws DataAccessException {
    Resign action = new Gson().fromJson(message, Resign.class);
    try {
      String visitorName=auth.getUserName(new Authtoken(action.getAuthString()));
      var return_message=String.format("%s has resigned the game", visitorName);
      var notification=new Notification(return_message);
      connections.broadcast(action.gameID, null, notification);
      connections.remove_gameid(action.gameID);
    }
  }


}
