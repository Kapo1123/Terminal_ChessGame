package server;

import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import spark.*;
import handler.*;
public class Server {
    server.websocket.WebSocketHandler webSocketHandler = new server.websocket.WebSocketHandler();
    public int run(int desiredPort) {
        Spark.port(desiredPort);
        Spark.webSocket("/connect", webSocketHandler);
        Spark.staticFiles.location("web");
        Spark.delete("/db", new ClearHandler());
        Spark.post("/user", new RegisterHandler());
        Spark.post("/session", new LoginHandler());
        Spark.delete("/session", new LogoutHandler());
        Spark.get("/game",new ListGameHandler());
        Spark.post("/game", new CreateGameHandler());
        Spark.put("/game", new JoingameHandler());
        // Register your endpoints and handle exceptions here.

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }


}
