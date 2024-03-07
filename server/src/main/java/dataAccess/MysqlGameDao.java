package dataAccess;

import Requestclasses.GameRequest;
import Requestclasses.Joingamerequest;
import Responseclass.ListgameResponse;
import Responseclass.Newgameresponse;
import chess.ChessBoard;
import chess.ChessGame;
import com.google.gson.Gson;

import java.sql.SQLException;

public class MysqlGameDao implements GameInterface {
  @Override
  public ListgameResponse getList(String username) {

  }

  @Override
  public void joinGame(String username, Joingamerequest body) throws DataAccessException {

  }

  @Override
  public Newgameresponse createGame(String username, GameRequest body) throws DataAccessException{
    try (var conn=DatabaseManager.getConnection()) {
      try (var preparedStatement=conn.prepareStatement("INSERT INTO auth (gameName,chess ) VALUES(?, ?)")) {
        preparedStatement.setString(1, body.gameName());
        ChessBoard board = new ChessBoard();
        board.resetBoard();
        ChessGame game = new ChessGame();
        game.setBoard(board);
        preparedStatement.setString(2, new Gson().toJson(game));
        preparedStatement.executeUpdate();
        var resultSet = preparedStatement.getGeneratedKeys();
        var ID = 0;
        if (resultSet.next()) {
          ID = resultSet.getInt(1);
        }
        return new Newgameresponse(ID);
      }
    } catch (SQLException e) {
      throw new DataAccessException(e.getMessage());
    }

  }

  @Override
  public void deleteall() throws DataAccessException {
    try (var conn=DatabaseManager.getConnection()) {
      try (var preparedStatement=conn.prepareStatement("TRUNCATE game")) {
        preparedStatement.executeUpdate();
      }
    } catch (SQLException e) {
      throw new DataAccessException(e.getMessage());
    }


  }
}
