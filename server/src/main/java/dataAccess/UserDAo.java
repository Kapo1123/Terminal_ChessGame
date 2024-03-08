package dataAccess;

import Requestclasses.Registerclass;
import Requestclasses.Userclass;

import java.util.*;

public class UserDAo implements UserInterface{

   static Map<String, List<String>> userdb = new HashMap<>();


  @Override
  public void createUser(Registerclass info) throws DataAccessException {
    if (userdb.isEmpty() || userdb.get(info.username()) == null) {
      List<String> list = new ArrayList<>(Arrays.asList(info.password(), info.email()));
      userdb.put(info.username(), list);
      return;
    }
    else {
      throw new DataAccessException("Error: already taken");
    }
  }

  @Override
  public boolean checkCredential(Userclass info) {
    if(userdb.get(info.username()) != null){
      String password = userdb.get(info.username()).get(0);
      if(password.equals(info.password())){
        return true;
      }
      return false;
    }
    return false;
  }
  @Override
  public void deleteall(){
    if(userdb.isEmpty()){
      return;
    }
    userdb.clear();

  }
}
