package com.codecool.controllers;

import com.codecool.model.AppUser;
import com.codecool.model.AppUserDAO;
import com.codecool.model.FactoryDAO;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.HttpCookie;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class LoginController implements HttpHandler {
    private AppUserDAO appUserDAO = new FactoryDAO().getAppUserDAO();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        String classpath = "static/templates/login_page.twig";
        JtwigModel jtwigModel = JtwigModel.newModel();
        JtwigTemplate jtwigTemplate = JtwigTemplate.classpathTemplate(classpath);
        String response = jtwigTemplate.render(jtwigModel);

        if (method.equals("POST")) {
            checkLoginAndPassword(httpExchange);
        }
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private void checkLoginAndPassword(HttpExchange httpExchange) {
        Map<String, String> userData = getDataFromRequest(httpExchange);
        String login = userData.get("login");
        String password = userData.get("password");

        if (isCorrectLoginAndPassword(login, password)) {
            String accountType = appUserDAO.get(login).getAppuserType();
            System.out.println("correct log in");
            System.out.println(accountType);
            sendCookies(httpExchange, login);
            redirect(httpExchange, accountType);
        } else {
            System.out.println("wrong data");
            redirect(httpExchange, "login");
        }
    }

    private boolean isCorrectLoginAndPassword(String login, String password) {
        AppUser user = appUserDAO.get(login);
        if (user == null) {
            return false;
        }
        return user.getPassword().equals(password);
    }


    private void sendCookies(HttpExchange httpExchange, String login) {
        HttpCookie cookie = new HttpCookie("login", login);
        httpExchange.getResponseHeaders().add("Set-Cookie", cookie.toString());
    }


    private Map<String, String> getDataFromRequest(HttpExchange httpExchange) {
        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);

        try {
            String formData = br.readLine();
            return parseFormData(formData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        String[] pairs = formData.split("&");
        for(String pair : pairs){
            String[] keyValue = pair.split("=");
            // We have to decode the value because it's urlencoded. see: https://en.wikipedia.org/wiki/POST_(HTTP)#Use_for_submitting_web_forms
            String value = URLDecoder.decode(keyValue[1], "UTF-8");
            map.put(keyValue[0], value);
        }

        return map;
    }
    private void redirect(HttpExchange httpExchange, String location) {
        Headers headers = httpExchange.getResponseHeaders();
        headers.add("Location", location);
        try {
            httpExchange.sendResponseHeaders(302, -1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        httpExchange.close();
    }
}
