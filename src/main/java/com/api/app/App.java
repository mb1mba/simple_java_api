package com.api.app;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import com.api.app.dao.UserDAOImpl;
import com.api.app.entities.User;
import com.api.app.services.Service;
import com.api.app.services.UserService;
import com.api.app.utils.Utils;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.List;

public class App {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        Service<User> userService = new UserService(new UserDAOImpl());

        server.createContext("/api/users", new Handler<>(userService));

        server.setExecutor(null);
        server.start();
        System.out.println("Server started on http://localhost:8000");
    }
}

class Handler<T> implements HttpHandler {
    private final Service<T> service;

    public Handler(Service<T> service) {
        this.service = service;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();

        switch (method) {
            case "GET":
                List<T> entities = service.getEntities();
                System.out.print(new Gson().toJson(entities));
                sendResponse(exchange, 200, new Gson().toJson(entities));
                break;

            case "POST":
                try {
                    InputStream is = exchange.getRequestBody();
                    String requestBody = Utils.readRequestBody(is);

                    T entity = Utils.deserializeJsonToEntity(requestBody, service.getEntityType());

                    service.createEntity(entity);
                    sendResponse(exchange, 201, "Entity created successfully.");
                } catch (Exception e) {
                    System.out.print(e);
                    sendResponse(exchange, 400, "Error processing request.");
                } finally {
                    exchange.getRequestBody().close();
                }
                break;

            case "DELETE":
                try {
                    URI u = exchange.getRequestURI();
                    String path = u.getPath();
                    String[] segments = path.split("/");
                    String entityId = segments[segments.length - 1];

                    service.deleteEntity(entityId);
                    sendResponse(exchange, 200, "Entity deleted successfully.");
                    break;
                } catch (Exception e) {
                    System.out.println(e);
                }
            default:
                sendResponse(exchange, 405, "Method Not Allowed.");
                break;
        }

    }

    private void sendResponse(HttpExchange exchange, int statusCode, Object response) throws IOException {
        Gson gson = new Gson();
        String jsonResponse = gson.toJson(response);

        exchange.sendResponseHeaders(statusCode, jsonResponse.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(jsonResponse.getBytes());
        os.close();
    }
}
