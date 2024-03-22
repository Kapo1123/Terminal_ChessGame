package clientTests;

import client.ServerFacade;
import dataAccess.DataAccessException;
import org.junit.jupiter.api.*;
import server.Server;

import static com.google.gson.JsonParser.parseString;
import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServerFacadeTests {

    private static Server server;
    static ServerFacade serverFacade;
    static Integer ID;

    @BeforeAll
    public static void init() {
        server=new Server();
        var port=server.run(0);
        System.out.println(port);
        System.out.println("Started test HTTP server on " + port);
        serverFacade=new ServerFacade("http://localhost:" + port);
    }

    @AfterAll
    static void stopServer() throws DataAccessException {
        serverFacade.clear();
        server.stop();
    }


    @Test
    public void sampleTest() {
        Assertions.assertTrue(true);
    }


    @Test
    @Order(1)
    public void Register_True() throws DataAccessException {
        String[] params = {"username", "password", "email"};;
        var response=serverFacade.register(params);
        assertNotNull(response);
    }
    @Test
    @Order(2)
    public void Register_False() throws DataAccessException {
        String[] params={"username", "password", "email"};
        Assertions.assertThrows(DataAccessException.class, () -> {
            serverFacade.register(params);
        }, "id 0 should never be a game ID");
    }
    @Test
    @Order(3)
    public void Login_False() throws DataAccessException {
        String[] params={"username", "123"};
        Assertions.assertThrows(DataAccessException.class, () -> {
            serverFacade.login(params);
        }, "Should be Error: unauthorized");
    }
    @Test
    @Order(4)
    public void Login_True() throws DataAccessException {
        String[] params={"username", "password"};
        var response=serverFacade.login(params);
        assertNotNull(response);
    }
    @Test
    @Order(5)
    public void CreateGame_True() throws DataAccessException {
        String[] params={"HelloThere"};
        var response=serverFacade.createGame(params);
        ID=response.gameID();
        assertNotNull(response);
    }
    @Test
    @Order(5)
    public void CreateGame_False() throws DataAccessException {
        String[] params={};
        Assertions.assertThrows(java.lang.ArrayIndexOutOfBoundsException.class, () -> {
            serverFacade.createGame(params);
        }, "Should be Error: unauthorized");
    }
    @Test
    @Order(6)
    public void JoinGame_True() throws DataAccessException {
        String[] params={Integer.toString(ID),"WHITE"};
        serverFacade.joinGame(params);
        Assertions.assertTrue(true);
    }
    @Test
    @Order(7)
    public void JoinGame_False() throws DataAccessException {
        String[] params={Integer.toString(ID),"WHITE"};
        Assertions.assertThrows(DataAccessException.class, () -> {
            serverFacade.joinGame(params);
        }, "Should be Error: Already taken");
    }
    @Test
    @Order(8)
    public void List_True() throws DataAccessException {
      var response = serverFacade.listGame();
        assertEquals(1,response.games().size());
    }
    @Test
    @Order(8)
    public void List_False() throws DataAccessException {
        var response = serverFacade.listGame();
        assertNotEquals(0,response.games().size());
    }
    @Test
    @Order(9)
    public void Logout_True() throws DataAccessException {
        String[] params={};
        serverFacade.logout();
        Assertions.assertTrue(true);
    }
    @Test
    @Order(10)
    public void Logout_True2() throws DataAccessException {
        String[] params={"username", "password"};
        serverFacade.login(params);
        serverFacade.logout();
        Assertions.assertTrue(true);
    }




    }







