package com.codecool.controllers;

import com.codecool.model.FactoryDAO;
import com.codecool.model.Mentor;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.util.List;

public class MentorController implements HttpHandler {
    private Mentor mentor;
    private FactoryDAO factoryDAO = new FactoryDAO();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        login(httpExchange);
        String response = createResponse("static/templates/mentor_profile.twig");


        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }

    private String createResponse(String filepath) {
        JtwigModel jtwigModel = JtwigModel.newModel();
        jtwigModel.with("mentor", mentor);
        JtwigTemplate jtwigTemplate = JtwigTemplate.classpathTemplate(filepath);
        return jtwigTemplate.render(jtwigModel);
    }

    private void login(HttpExchange httpExchange) {
        if (mentor != null) {
            return;
        }
        String login = getLogin(getCookie(httpExchange));
        List<Mentor> mentors = factoryDAO.getMentorDAO().getList();
        for (Mentor mentor : mentors) {
            if (mentor.getLogin().equals(login)) {
                this.mentor = mentor;
                return;
            }
        }
        redirect(httpExchange, "login");
    }

    private HttpCookie getCookie(HttpExchange httpExchange) {
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        HttpCookie cookie;

        if (cookieStr != null) {  // Cookie already exists
            cookie = HttpCookie.parse(cookieStr).get(0);
        } else { // Create a new cookie
            cookie = null;
            redirect(httpExchange, "login");
        }
        return cookie;
    }

    private String getLogin(HttpCookie cookie) {
        return cookie.toString().split("=")[1];
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
