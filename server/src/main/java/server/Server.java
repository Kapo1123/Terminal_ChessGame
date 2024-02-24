package server;

import spark.*;
import handler.*;
public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");
        Spark.delete("/db", new ClearHandler());
        Spark.post("/user", new RegisterHandler());
        Spark.post("/session", new loginHandler());
        Spark.delete("/session", new LogoutHandler());
        Spark.get("/game", (req, res) -> new ListGameHandler().handle(req, res));
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
