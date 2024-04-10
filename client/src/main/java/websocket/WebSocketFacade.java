package websocket;

import User_game_commands.*;
import chess.ChessGame;
import chess.ChessMove;
import client.Game_UI;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import serverMessages_classes.Error_message;
import serverMessages_classes.Load_Game;
import serverMessages_classes.Notification;
import ui.chess_board;
import webSocketMessages.userCommands.UserGameCommand;
import webSocketMessages.serverMessages.ServerMessage;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

//need to extend Endpoint for websocket to work properly
public class WebSocketFacade extends Endpoint {
    static String Authtoken;

    Session session;
    NotificationHandler notificationHandler;


    public WebSocketFacade(String url, NotificationHandler notificationHandler, String Authtoken) throws DataAccessException {
        try {
            url = url.replace("http", "ws");
            URI socketURI = new URI(url + "/connect");
            this.notificationHandler = notificationHandler;
            this.Authtoken = Authtoken;

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);

            //set message handler
            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    ServerMessage return_message = new Gson().fromJson(message, ServerMessage.class);
                    if(return_message.getServerMessageType().equals(ServerMessage.ServerMessageType.NOTIFICATION)) {
                        Notification notification = new Gson().fromJson(message, Notification.class);
                        notificationHandler.notify(notification.getmessage());
                    }
                    else if (return_message.getServerMessageType().equals(ServerMessage.ServerMessageType.LOAD_GAME)){
                        Load_Game game = new Gson().fromJson(message, Load_Game.class);
                        Game_UI.game = game.game;
                        Game_UI.board.updateboard(game.game);
                        if (Game_UI.color.equals(ChessGame.TeamColor.WHITE)){
                            Game_UI.board.drawWhite(Game_UI.board.out);
                        }
                        if (Game_UI.color.equals(ChessGame.TeamColor.BLACK)){
                            Game_UI.board.drawBlack(Game_UI.board.out);
                        }
                    }
                    else if (return_message.getServerMessageType().equals(ServerMessage.ServerMessageType.ERROR)){
                        Error_message error = new Gson().fromJson(message, Error_message.class);
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

    public void Leave(Integer GameID, ChessGame.TeamColor color ) throws DataAccessException {
        try {
            var action = new Leave(Authtoken, GameID,color);
            this.session.getBasicRemote().sendText(new Gson().toJson(action));
        } catch (IOException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }
    public void Resign(Integer GameID) throws DataAccessException {
        try {
            var action = new Resign(Authtoken, GameID);
            this.session.getBasicRemote().sendText(new Gson().toJson(action));
        } catch (IOException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }
    public void MakeMove(Integer GameID, ChessMove move) throws DataAccessException {
        try {
            var action = new Make_Move(Authtoken, GameID, move);
            this.session.getBasicRemote().sendText(new Gson().toJson(action));
        } catch (IOException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }
    public void Join_Observer(Integer GameID) throws DataAccessException {
        try {
            var action = new Join_Observer(Authtoken, GameID);
            this.session.getBasicRemote().sendText(new Gson().toJson(action));
        } catch (IOException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }
    public void Join_Player(Integer GameID, ChessGame.TeamColor playerColor) throws DataAccessException {
        try {
            var action = new Join_Player(Authtoken, GameID,playerColor);
            this.session.getBasicRemote().sendText(new Gson().toJson(action));
        } catch (IOException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }


}

