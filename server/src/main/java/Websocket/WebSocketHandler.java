package server.websocket;

import Requestclasses.Authtoken;
import User_game_commands.*;
import Websocket.Connection;
import Websocket.ConnectionManager;
import chess.ChessGame;
import chess.InvalidMoveException;
import com.google.gson.Gson;
import dataAccessError.DataAccessException;
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
      case RESIGN -> Resign(session,message);
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
       connections.send_error(action.gameID,connect,error_message);
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
      var return_message = String.format("%s has joined the game as Observer ", visitorName);
      var notification = new Notification(return_message);
      Load_Game load = new Load_Game(chess_game);
      connections.broadcast(action.gameID,action.getAuthString(), notification);
      connections.send_one(action.gameID,action.getAuthString(),load);
    }
    catch (DataAccessException | IOException e) {
      Error_message error_message = new Error_message(e.getMessage());
      connections.send_error(action.gameID,connect,error_message);
    }

  }

  public void Make_Move(Session session, String message) throws DataAccessException, IOException {
    Make_Move action = new Gson().fromJson(message, Make_Move.class);
    String visitorName;
    Connection connect = new Connection(action.getAuthString(),session);
    try {
      if (!auth.isValid(new Authtoken(action.getAuthString()))) {
        throw new DataAccessException("Error:not valid authtoken");
      }
      game.check_gameID(action.gameID, null, action.getAuthString());
      visitorName= auth.getUserName(new Authtoken(action.getAuthString()));
      ChessGame chess_game=game.getGame(action.gameID);
      ChessGame.TeamColor color = game.getcolor(action.gameID, visitorName);
      if (!color.equals(chess_game.getTeamTurn())){
        throw new DataAccessException("Error:not valid team turn");
      }
      chess_game.makeMove(action.move);
      game.update_game(action.gameID,chess_game);
      var return_message = String.format("%s made a move from %s to %s", visitorName,action.move.getStartPosition().toString(),action.move.getEndPosition().toString());
      var notification=new Notification(return_message);
      connections.broadcast(action.gameID, action.getAuthString(), notification);
      connections.send_game(action.gameID, null, new Load_Game(chess_game));
      String mycolor="";
      String othercolor="";
      if(color.equals(ChessGame.TeamColor.WHITE)){
        mycolor = "White";
        othercolor = "Black";
      }
      else{
        mycolor = "Black";
        othercolor = "White";
      }
      if(chess_game.isInStalemate(color)){
        var return_message1 = String.format("%sPlayer is isInStalemate. The game is now ended %sPlayer win",mycolor, othercolor);
        var notification1=new Notification(return_message1);
        connections.broadcast(action.gameID, action.getAuthString(), notification1);
        game.check_gameID(action.gameID, null, action.getAuthString());
        game.delete_gameID(action.gameID);
        connections.remove_gameid(action.gameID);
      }
      if(chess_game.isInCheckmate(color)){

        var return_message2 = String.format("%sPlayer is isInStalemate. The game is now ended %sPlayer win",mycolor, othercolor );
        var notification2=new Notification(return_message2);
        connections.broadcast(action.gameID, action.getAuthString(), notification2);
        game.check_gameID(action.gameID, null, action.getAuthString());
        game.delete_gameID(action.gameID);
        connections.remove_gameid(action.gameID);
      }

    }
    catch (DataAccessException | InvalidMoveException e) {
      Error_message error_message =null;
      if(e.getMessage() == null){
       error_message=new Error_message("Error: Invalid move");
      }
      else {
        error_message=new Error_message(e.getMessage());
      }
      connections.send_error(action.gameID,connect,error_message);
    }

  }
  private void Leave(Session session, String message) throws DataAccessException, IOException {
    Leave action = new Gson().fromJson(message, Leave.class);
    Connection connect = new Connection(action.getAuthString(),session);
    connections.remove(action.gameID, connect);
    try {
      if(!auth.isValid(new Authtoken(action.getAuthString()))){
        throw new DataAccessException("Error:not valid authtoken");
      }
      String visitorName=auth.getUserName(new Authtoken(action.getAuthString()));
      if (action.color != null){
        game.check_gameID(action.gameID,action.color,visitorName);
        game.leave_player(action.gameID,action.color);
      }
      connections.remove(action.gameID,connect);
      var return_message=String.format("%s has left the game ", visitorName);
      var notification=new Notification(return_message);
      connections.broadcast(action.gameID, action.getAuthString(), notification);
    }catch (DataAccessException | IOException e) {
      Error_message error_message = new Error_message(e.getMessage());
      connections.send_error(action.gameID,connect,error_message);
    }

  }
  private void Resign(Session session, String message) throws DataAccessException, IOException {
    Resign action = new Gson().fromJson(message, Resign.class);
    Connection connect = new Connection(action.getAuthString(),session);
    try {
      if(!auth.isValid(new Authtoken(action.getAuthString()))){
        throw new DataAccessException("Error:not valid authtoken");
      }
      game.check_gameID(action.gameID, null, action.getAuthString());
      String visitorName=auth.getUserName(new Authtoken(action.getAuthString()));
      game.getcolor(action.gameID,visitorName);
      game.delete_gameID(action.gameID);
      var return_message=String.format("%s has resigned the game", visitorName);
      var notification=new Notification(return_message);
      connections.broadcast(action.gameID, null, notification);
      connections.remove_gameid(action.gameID);
    }catch (DataAccessException | IOException e) {
      Error_message error_message = new Error_message(e.getMessage());
      connections.send_error(action.gameID,connect,error_message);
    }


  }


}
