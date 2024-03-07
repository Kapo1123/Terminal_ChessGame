package service;

import dataAccess.*;

public class DbService {
  public  boolean deleteAll() throws DataAccessException {
    MysqlGameDao gamedao = new MysqlGameDao();
    gamedao.deleteall();
    MysqlAuthDao auth = new MysqlAuthDao();
    auth.deleteall();
    MysqlUserDao userDao= new MysqlUserDao();
    userDao.deleteall();
    return true;
  }
}
