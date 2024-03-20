package client;

import Responseclass.Registerresponse;

import java.util.Arrays;

public class ServerFacade {
  private final String serverUrl;

  public ServerFacade(String url) {
    serverUrl = url;
  }
  public Registerresponse  register(String[] params){
    var path = "/user";
    return this.makeRequest("POST", path, params, Registerresponse.class);
  }
  public Registerresponse  login(String[] params){
    var path = "/session";
    return this.makeRequest("POST", path, params, Registerresponse.class);
  }

  public Registerresponse  creategame(String[] params){
    var path = "/game";
    return this.makeRequest("POST", path, params, Registerresponse.class);
  }
  public Registerresponse  listGame(String[] params){
    var path = "/game";
    return this.makeRequest("GET", path, params, Registerresponse.class);
  }
  public Registerresponse  logout(){
    var path = "/session";
    return this.makeRequest("DELETE", path, Registerresponse.class);
  }
  public Registerresponse  joinGame(String[] params){
    var path = "/game";
    return this.makeRequest("PUT", path, params, Registerresponse.class);
  }











































}
