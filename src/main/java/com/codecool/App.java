package com.codecool;

import com.codecool.controllers.*;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

public class App {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        server.createContext("/login", new LoginController());
        server.createContext("/", new LoginController());
        server.createContext("/logout", new LogoutController());
        server.createContext("/admin", new AdminController());

        server.createContext("/mentor", new MentorController());
        server.createContext("/static", new StaticController());
        server.setExecutor(null);

        server.start();
    }
}