package com.codecool.controllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class LogoutController implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        Common.sendCookies(httpExchange, "");
        Common.redirect(httpExchange, "login");
    }
}
