package client;

import Requestclasses.GameRequest;
import Requestclasses.Joingamerequest;
import Requestclasses.Registerclass;
import Requestclasses.Userclass;
import Responseclass.ListgameResponse;
import Responseclass.Newgameresponse;
import Responseclass.Registerresponse;
import com.google.gson.Gson;
import dataAccess.DataAccessException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class ServerFacade {
  static String Authtoken;
  private final String serverUrl;

  public ServerFacade(String url) {
    serverUrl = url;
  }
  public Registerresponse  register(String[] params) throws DataAccessException {
    var path = "/user";
    Registerclass request = new Registerclass(params[0],params[1],params[2]);
    var response = this.makeRequest("POST", path, request, Registerresponse.class);
    Authtoken = response.authToken();
    return response;
  }
  public Registerresponse  login(String[] params) throws DataAccessException {
    var path = "/session";
    Userclass request = new Userclass(params[0],params[1]);
    var response =  this.makeRequest("POST", path, request, Registerresponse.class);
    Authtoken = response.authToken();
    return response;
  }

  public Newgameresponse creategame(String[] params) throws DataAccessException {
    var path = "/game";
    GameRequest request = new GameRequest(params[0]);
    return this.makeRequest("POST", path, request, Newgameresponse.class);
  }
  public ListgameResponse listGame() throws DataAccessException {
    var path = "/game";
    return this.makeRequest("GET", path,null, ListgameResponse.class);
  }
  public void  logout() throws DataAccessException {
    var path = "/session";
    this.makeRequest("DELETE", path,null, null);
  }
  public void  joinGame(String[] params) throws DataAccessException {
    var path = "/game";
    Joingamerequest request = new Joingamerequest(params[1],parseInt(params[0]));
    this.makeRequest("PUT", path, request, null);
  }
  public void clear() throws DataAccessException{
    var path = "/db";
    this.makeRequest("DELETE", path, null, null);
  }
  private <T> T makeRequest(String method, String path, Object request , Class<T> responseClass) throws DataAccessException {
    try{
    URI uri = new URI(Repl.url+path);
//      URI uri = new URI("http://localhost:8080/game");
    HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
    http.setRequestMethod(method);
      if (Authtoken != null){
        http.addRequestProperty("authorization", Authtoken);
      }
    writeRequestBody(request, http);
    http.connect();

    T response =  readResponseBody(http,responseClass);
    return response;
    }
    catch( IOException| URISyntaxException ex) {
      throw new DataAccessException(ex.getMessage());
    }

  }
  private static void writeRequestBody(Object request, HttpURLConnection http) throws IOException {
    if (request != null) {
      http.setDoOutput(true);
      var jsonBody = new Gson().toJson(request);
      try (var outputStream = http.getOutputStream()) {
        outputStream.write(jsonBody.getBytes());
      }
    }
  }
  private static <T> T  readResponseBody(HttpURLConnection http,Class<T> responseClass) throws DataAccessException {
    T responseBody = null;
    try{
    if(http.getResponseCode() == 200) {


      InputStream respBody=http.getInputStream();
        InputStreamReader inputStreamReader=new InputStreamReader(respBody);
        if(responseClass != null) {
          responseBody=new Gson().fromJson(inputStreamReader, responseClass);
        }else{
          return null;
        }


      return responseBody;
    }
    else{
       InputStream respBody=http.getErrorStream();
        InputStreamReader inputStreamReader=new InputStreamReader(respBody);
        var response=new Gson().fromJson(inputStreamReader, Map.class);
        throw new DataAccessException((String) response.get("message"));
      }
    }
    catch(IOException ex){
      throw new DataAccessException(ex.getMessage());
    }
  }













































}
