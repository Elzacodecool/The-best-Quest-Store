package com.codecool;

import com.codecool.controllers.AdminController;
import com.codecool.controllers.LoginController;
import com.codecool.controllers.MentorController;
import com.codecool.controllers.StaticController;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

public class App {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(9000), 0);

        server.createContext("/login", new LoginController());
        server.createContext("/admin", new AdminController());

        server.createContext("/mentor", new MentorController());
        server.createContext("/static", new StaticController());
        server.setExecutor(null);

        server.start();
    }
}