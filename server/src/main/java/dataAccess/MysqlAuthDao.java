package dataAccess;

import Requestclasses.Authtoken;

import java.sql.SQLException;

public class MysqlAuthDao  implements AuthInterface{
  public MysqlAuthDao() throws DataAccessException {
    configureDatabase();
  }

  @Override
  public Authtoken createAuth(String username) throws DataAccessException{
    return null;
  }

  @Override
  public void deleteAuth(Authtoken auth) throws DataAccessException {

  }

  @Override
  public boolean isValid(Authtoken auth) throws DataAccessException{
    return false;
  }

  @Override
  public String getUserName(Authtoken auth) throws DataAccessException{
    return null;
  }

  @Override
  public void deleteall() throws DataAccessException{

  }
  private void configureDatabase() throws  DataAccessException {
    DatabaseManager.createDatabase();
    try (var conn = DatabaseManager.getConnection()) {
      for (var statement : createStatements) {
        try (var preparedStatement = conn.prepareStatement(statement)) {
          preparedStatement.executeUpdate();
        }
      }
    } catch (SQLException ex) {
      throw new DataAccessException(String.format("Unable to configure database: %s", ex.getMessage()));
    }
  }
}
