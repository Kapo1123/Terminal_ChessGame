package dataAccess;

import Requestclasses.Registerclass;
import Requestclasses.Userclass;

import java.sql.SQLException;

public class MysqlUserDao implements UserInterface {
  @Override
  public void createUser(Registerclass info) throws DataAccessException {
    try (var conn=DatabaseManager.getConnection()) {
      try (var preparedStatement=conn.prepareStatement("INSERT INTO user (username, password,email) VALUES(?, ?, ?)")) {
        preparedStatement.setString(1, info.username());
        preparedStatement.setString(2, info.password());
        preparedStatement.setString(3, info.email());
        preparedStatement.executeUpdate();
      }
    } catch (SQLException e) {
      throw new DataAccessException("Error: already taken");
    }
  }

  @Override
  public boolean checkCredential(Userclass info) throws DataAccessException {
    try (var conn=DatabaseManager.getConnection()) {
      try (var preparedStatement=conn.prepareStatement("SELECT * FROM user WHERE username=?")) {
        preparedStatement.setString(1, info.username());
        try (var rs=preparedStatement.executeQuery()) {
          if (rs.next()) {
            String Password = rs.getString("password");
            return Password.equals(info.password());
          }
        }
      }
    } catch (SQLException e) {
      return false;
    }
    return false;
  }


  @Override
  public void deleteall() throws DataAccessException{
    try (var conn=DatabaseManager.getConnection()) {
      try (var preparedStatement=conn.prepareStatement("TRUNCATE user")) {
        preparedStatement.executeUpdate();
      }
    } catch (SQLException e) {
      throw new DataAccessException(e.getMessage());
    }
  }
}
