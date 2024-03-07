package dataAccess;

import Requestclasses.Authtoken;

import java.sql.DriverManager;
import java.sql.SQLException;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class MysqlAuthDao  implements AuthInterface {

  @Override
  public void createAuth(Authtoken authToken, String username) throws DataAccessException {
    try (var conn=DatabaseManager.getConnection()) {
      try (var preparedStatement=conn.prepareStatement("INSERT INTO auth (authToken, username) VALUES(?, ?)")) {
        preparedStatement.setString(1, authToken.authtoken());
        preparedStatement.setString(2, username);
        preparedStatement.executeUpdate();
      }
    } catch (SQLException e) {
      throw new DataAccessException(e.getMessage());
    }
  }

  @Override
  public void deleteAuth(Authtoken auth) throws DataAccessException {
    try (var conn=DatabaseManager.getConnection()) {
      try (var preparedStatement=conn.prepareStatement("DELETE FROM auth WHERE authToken=?")) {
        preparedStatement.setString(1, auth.authtoken());
        preparedStatement.executeUpdate();
      }

    } catch (SQLException e) {
      throw new DataAccessException("Error: unauthorized");
    }
  }

  @Override
  public boolean isValid(Authtoken auth) throws DataAccessException {
    try (var conn=DatabaseManager.getConnection()) {
      try (var preparedStatement=conn.prepareStatement("SELECT * FROM auth WHERE authToken=?")) {
        preparedStatement.setString(1, auth.authtoken());
        try (var rs=preparedStatement.executeQuery()) {
          if (rs.next()) {
            return true;
          }
        }
      }
    } catch (SQLException e) {
      throw new DataAccessException(e.getMessage());
    }
    return false;
  }


  @Override
  public String getUserName(Authtoken auth) throws DataAccessException {
    try (var conn=DatabaseManager.getConnection()) {
      try (var preparedStatement=conn.prepareStatement("SELECT username FROM auth WHERE authToken=?")) {
        preparedStatement.setString(1, auth.authtoken());
        try (var rs=preparedStatement.executeQuery()) {
          if (rs.next()) {
            return rs.getString("username");
          }
        }
      }
    } catch (SQLException e) {
      throw new DataAccessException(e.getMessage());
    }
    return null;
  }

  @Override
  public void deleteall() throws DataAccessException {
    try (var conn=DatabaseManager.getConnection()) {
      try (var preparedStatement=conn.prepareStatement("TRUNCATE auth")) {
        preparedStatement.executeUpdate();
      }
    } catch (SQLException e) {
      throw new DataAccessException(e.getMessage());
    }
  }
}
