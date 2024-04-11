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
import serverMessages_classes.ErrorMessage;
import serverMessages_classes.LoadGame;
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
      case JOIN_PLAYER ->  joinPlayer(session,message);
      case JOIN_OBSERVER ->  joinObserver(session,message);
      case MAKE_MOVE ->  makeMove(session,message);
      case LEAVE ->  leave(session,message);
      case RESIGN -> resign(session,message);
    }
  }

  private void joinPlayer(Session session, String message) throws DataAccessException, IOException {
    JoinPlayer action = new Gson().fromJson(message, JoinPlayer.class);
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
      game.checkGameID(action.gameID,action.playerColor,visitorName);
      ChessGame chessGame = game.getGame(action.gameID);
      var returnMessage = String.format("%s has joined the game as %s player", visitorName,color);
      var notification = new Notification(returnMessage);
      LoadGame load = new LoadGame(chessGame);
      connections.broadcast(action.gameID,action.getAuthString(), notification);
      connections.sendOne(action.gameID,action.getAuthString(),load);
    }
    catch (DataAccessException | IOException e) {
       ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
       connections.sendError(action.gameID,connect,errorMessage);
    }
  }

  private void joinObserver(Session session, String message) throws DataAccessException, IOException {
    JoinObserver action = new Gson().fromJson(message, JoinObserver.class);
    Connection connect = new Connection(action.getAuthString(),session);
    connections.add(action.gameID, connect);
//    connections.remove(visitorName);
    try {
      if(!auth.isValid(new Authtoken(action.getAuthString()))){
        throw new DataAccessException("Error:not valid authtoken");
      }
      String visitorName= auth.getUserName(new Authtoken(action.getAuthString()));
      ChessGame chessGame = game.getGame(action.gameID);
      var returnMessage = String.format("%s has joined the game as Observer ", visitorName);
      var notification = new Notification(returnMessage);
      LoadGame load = new LoadGame(chessGame);
      connections.broadcast(action.gameID,action.getAuthString(), notification);
      connections.sendOne(action.gameID,action.getAuthString(),load);
    }
    catch (DataAccessException | IOException e) {
      ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
      connections.sendError(action.gameID,connect,errorMessage);
    }

  }

  public void makeMove(Session session, String message) throws DataAccessException, IOException {
    MakeMove action = new Gson().fromJson(message, MakeMove.class);
    String visitorName;
    Connection connect = new Connection(action.getAuthString(),session);
    try {
      if (!auth.isValid(new Authtoken(action.getAuthString()))) {
        throw new DataAccessException("Error:not valid authtoken");
      }
      game.checkGameID(action.gameID, null, action.getAuthString());
      visitorName= auth.getUserName(new Authtoken(action.getAuthString()));
      ChessGame chessGame=game.getGame(action.gameID);
      ChessGame.TeamColor color = game.getcolor(action.gameID, visitorName);
      if (!color.equals(chessGame.getTeamTurn())){
        throw new DataAccessException("Error:not valid team turn");
      }
      chessGame.makeMove(action.move);
      game.updateGame(action.gameID,chessGame);
      var returnMessage = String.format("%s made a move from %s to %s", visitorName,action.move.getStartPosition().toString(),action.move.getEndPosition().toString());
      var notification=new Notification(returnMessage);
      connections.broadcast(action.gameID, action.getAuthString(), notification);
      connections.sendGame(action.gameID, new LoadGame(chessGame));
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
      if(chessGame.isInStalemate(color)){
        var returnMessage1 = String.format("%sPlayer is isInStalemate. The game is now ended %sPlayer win",mycolor, othercolor);
        var notification1=new Notification(returnMessage1);
        connections.broadcast(action.gameID, action.getAuthString(), notification1);
        game.checkGameID(action.gameID, null, action.getAuthString());
        game.deleteGameID(action.gameID);
        connections.removeGameid(action.gameID);
      }
      if(chessGame.isInCheckmate(color)){

        var returnMmessage2 = String.format("%sPlayer is isInCheckMate. The game is now ended %sPlayer win",mycolor, othercolor );
        var notification2=new Notification(returnMmessage2);
        connections.broadcast(action.gameID, action.getAuthString(), notification2);
        game.checkGameID(action.gameID, null, action.getAuthString());
        game.deleteGameID(action.gameID);
        connections.removeGameid(action.gameID);
      }

    }
    catch (DataAccessException | InvalidMoveException e) {
      ErrorMessage errorMessage =null;
      if(e.getMessage() == null){
       errorMessage=new ErrorMessage("Error: Invalid move");
      }
      else {
        errorMessage=new ErrorMessage(e.getMessage());
      }
      connections.sendError(action.gameID,connect,errorMessage);
    }

  }
  private void leave(Session session, String message) throws DataAccessException, IOException {
    Leave action = new Gson().fromJson(message, Leave.class);
    Connection connect = new Connection(action.getAuthString(),session);
    connections.remove(action.gameID, connect);
    try {
      if(!auth.isValid(new Authtoken(action.getAuthString()))){
        throw new DataAccessException("Error:not valid authtoken");
      }
      String visitorName=auth.getUserName(new Authtoken(action.getAuthString()));
      if (action.color != null){
        game.checkGameID(action.gameID,action.color,visitorName);
        game.leavePlayer(action.gameID,action.color);
      }
      connections.remove(action.gameID,connect);
      var returnMessage=String.format("%s has left the game ", visitorName);
      var notification=new Notification(returnMessage);
      connections.broadcast(action.gameID, action.getAuthString(), notification);
    }catch (DataAccessException | IOException e) {
      ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
      connections.sendError(action.gameID,connect,errorMessage);
    }

  }
  private void resign(Session session, String message) throws DataAccessException, IOException {
    Resign action = new Gson().fromJson(message, Resign.class);
    Connection connect = new Connection(action.getAuthString(),session);
    try {
      if(!auth.isValid(new Authtoken(action.getAuthString()))){
        throw new DataAccessException("Error:not valid authtoken");
      }
      game.checkGameID(action.gameID, null, action.getAuthString());
      String visitorName=auth.getUserName(new Authtoken(action.getAuthString()));
      game.getcolor(action.gameID,visitorName);
      game.deleteGameID(action.gameID);
      var returnMessage=String.format("%s has resigned the game", visitorName);
      var notification=new Notification(returnMessage);
      connections.broadcast(action.gameID, null, notification);
      connections.removeGameid(action.gameID);
    }catch (DataAccessException | IOException e) {
      ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
      connections.sendError(action.gameID,connect,errorMessage);
    }


  }


}
