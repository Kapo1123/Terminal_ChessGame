package service;

import dataAccess.GameDAo;
public class DbService {
  public  boolean deleteAll(){
    GameDAo gamedao = new GameDAo();
    gamedao.deleteall();
    return true;
  }
}
