package websocket;

import User_game_commands.*;
import chess.ChessGame;
import chess.ChessMove;
import client.GameUI;
import client.Repl;
import com.google.gson.Gson;
import dataAccessError.DataAccessException;
import serverMessages_classes.ErrorMessage;
import serverMessages_classes.LoadGame;
import serverMessages_classes.Notification;
import webSocketMessages.serverMessages.ServerMessage;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

//need to extend Endpoint for websocket to work properly
public class WebSocketFacade extends Endpoint {
    static String authtoken;

    Session session;
    NotificationHandler notificationHandler;


    public WebSocketFacade(String url, NotificationHandler notificationHandler, String authtoken) throws DataAccessException {
        try {
            url = url.replace("http", "ws");
            URI socketURI = new URI(url + "/connect");
            this.notificationHandler = notificationHandler;
            this.authtoken= authtoken;

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);

            //set message handler
            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    ServerMessage returnMessage = new Gson().fromJson(message, ServerMessage.class);
                    if(returnMessage.getServerMessageType().equals(ServerMessage.ServerMessageType.NOTIFICATION)) {
                        Notification notification = new Gson().fromJson(message, Notification.class);
                        notificationHandler.notify(notification.getmessage());
                    }
                    else if (returnMessage.getServerMessageType().equals(ServerMessage.ServerMessageType.LOAD_GAME)){
                        LoadGame game = new Gson().fromJson(message, LoadGame.class);
                        GameUI.game = game.game;
                        GameUI.board.updateboard(game.game);
                        if (GameUI.color == null){
                            GameUI.board.drawWhite(GameUI.board.out,null,null);
                            Repl.printPrompt();
                        }
                        else if (GameUI.color.equals(ChessGame.TeamColor.WHITE)){
                            GameUI.board.drawWhite(GameUI.board.out,null,null);
                            Repl.printPrompt();
                        }
                        else if (GameUI.color.equals(ChessGame.TeamColor.BLACK)){
                            GameUI.board.drawBlack(GameUI.board.out,null,null);
                            Repl.printPrompt();
                        }
                    }
                    else if (returnMessage.getServerMessageType().equals(ServerMessage.ServerMessageType.ERROR)){
                        ErrorMessage error = new Gson().fromJson(message, ErrorMessage.class);
                    }

                }
            });
        } catch (DeploymentException | IOException | URISyntaxException ex) {
            throw new DataAccessException( ex.getMessage());
        }
    }

    //Endpoint requires this method, but you don't have to do anything
    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }
    public void send(String msg) throws Exception {
        this.session.getBasicRemote().sendText(msg);
    }

    public void leave(Integer gameID, ChessGame.TeamColor color ) throws DataAccessException {
        try {
            var action = new Leave(authtoken, gameID,color);
            this.session.getBasicRemote().sendText(new Gson().toJson(action));
        } catch (IOException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }
    public void resign(Integer gameID) throws DataAccessException {
        try {
            var action = new Resign(authtoken, gameID);
            this.session.getBasicRemote().sendText(new Gson().toJson(action));
        } catch (IOException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }
    public void makeMove(Integer gameID, ChessMove move) throws DataAccessException {
        try {
            var action = new MakeMove(authtoken, gameID, move);
            this.session.getBasicRemote().sendText(new Gson().toJson(action));
        } catch (IOException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }
    public void joinObserver(Integer gameID) throws DataAccessException {
        try {
            var action = new JoinObserver(authtoken, gameID);
            this.session.getBasicRemote().sendText(new Gson().toJson(action));
        } catch (IOException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }
    public void joinPlayer(Integer gameID, ChessGame.TeamColor playerColor) throws DataAccessException {
        try {
            var action = new JoinPlayer(authtoken, gameID,playerColor);
            this.session.getBasicRemote().sendText(new Gson().toJson(action));
        } catch (IOException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }


}

