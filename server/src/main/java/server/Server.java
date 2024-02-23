package server;

import spark.*;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");
        Spark.delete("/db", (req, res) ->
                (new ClearHandler()).handleRequest(req,
                        res));
        Spark.post("/user", (req, res) ->
                (new RegisterHandler()).handleRequest(req,
                        res));
        Spark.post("/session", (req, res) ->
                (new LoginHandler()).handleRequest(req,
                        res));
        Spark.delete("/session", (req, res) ->
                (new logoutHandler()).handleRequest(req,
                        res));
        Spark.get("/game", (req, res) ->
                (new ListgameHandler()).handleRequest(req,
                        res));
        Spark.post("/game", (req, res) ->
                (new creategameHandler()).handleRequest(req,
                        res));
        Spark.put("/game", (req, res) ->
                (new joingameHandler()).handleRequest(req,
                        res));
        // Register your endpoints and handle exceptions here.

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
