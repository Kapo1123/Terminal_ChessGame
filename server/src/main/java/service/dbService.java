package service;

import Requestclasses.Authtoken;
import Responseclass.ListgameResponse;
import dataAccess.DataAccessException;

public class dbService {
  public static boolean deleteall(){
    gameDAo.deleteall();
    return true;
  }
}
