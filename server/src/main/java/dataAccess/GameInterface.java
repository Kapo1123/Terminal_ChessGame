package dataAccess;

import Requestclasses.GameRequest;
import Requestclasses.Joingamerequest;
import Responseclass.ListgameResponse;
import Responseclass.Newgameresponse;
import chess.ChessGame;
import dataAccessError.DataAccessException;

public interface GameInterface {
  public  ListgameResponse getList(String username) throws DataAccessException;


  public  void joinGame(String username, Joingamerequest body) throws DataAccessException;

  public Newgameresponse createGame(String username, GameRequest body) throws DataAccessException;

  public ChessGame getGame(Integer gameID) throws DataAccessException;

  public void leavePlayer(Integer gameID, ChessGame.TeamColor color)throws DataAccessException;
  public  void deleteall() throws DataAccessException;
  public  void checkGameID(Integer gameID, ChessGame.TeamColor color, String username) throws DataAccessException;
  public  void deleteGameID(Integer gameID) throws DataAccessException;
  public ChessGame.TeamColor getcolor(Integer gameID,String username) throws DataAccessException;
  public void updateGame(Integer gameID, ChessGame game) throws DataAccessException;
}

