package service;

import dataAccess.AuthDAo;
import dataAccess.GameDAo;
import dataAccess.UserDAo;

public class DbService {
  public  boolean deleteAll(){
    GameDAo gamedao = new GameDAo();
    gamedao.deleteall();
    AuthDAo authdao = new AuthDAo();
    authdao.deleteall();
    UserDAo userdao = new UserDAo();
    userdao.deleteall();
    return true;
  }
}
