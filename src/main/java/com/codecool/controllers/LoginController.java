package com.codecool.controllers;

import com.codecool.model.AppUser;
import com.codecool.model.AppUserDAO;
import com.codecool.model.FactoryDAO;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.Map;

public class LoginController implements HttpHandler {
    private AppUserDAO appUserDAO = new FactoryDAO().getAppUserDAO();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        String response = createResponse();

        if (method.equals("POST")) {
            checkLoginAndPassword(httpExchange);
        }

        Common.sendResponse(httpExchange, response);
    }

    private String createResponse() {
        String classpath = "static/templates/login_page.twig";
        JtwigModel jtwigModel = JtwigModel.newModel();
        JtwigTemplate jtwigTemplate = JtwigTemplate.classpathTemplate(classpath);
        return jtwigTemplate.render(jtwigModel);
    }

    private void checkLoginAndPassword(HttpExchange httpExchange) {
        Map<String, String> userData = Common.getDataFromRequest(httpExchange);
        String login = userData.get("login");
        String password = userData.get("password");

        if (isCorrectLoginAndPassword(login, password)) {
            String accountType = appUserDAO.get(login).getAppuserType();
            Common.sendCookies(httpExchange, login);
            Common.redirect(httpExchange, accountType);
        } else {
            Common.redirect(httpExchange, "login");
        }
    }

    private boolean isCorrectLoginAndPassword(String login, String password) {
        AppUser user = appUserDAO.get(login);
        if (user == null) {
            return false;
        }
        return user.getPassword().equals(password);
    }


}
