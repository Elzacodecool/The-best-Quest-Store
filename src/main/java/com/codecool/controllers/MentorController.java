package com.codecool.controllers;

import com.codecool.model.*;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.apache.commons.lang3.StringUtils;
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

        String method = httpExchange.getRequestMethod();
        String [] uriData = getUriData(httpExchange);

        if (method.equals("GET")) {
            String response = createResponse(uriData);
            sendResponse(httpExchange, response);
        }
    }

    private String chooseTwigFileByUri(String[] uriData) {
        final String LOGIN = "/static/templates/mentor/login_page.twig";
        final String PROFILE = "/static/templates/mentor/mentor_profile.twig";
        final String CODECOOLERS = "/static/templates/mentor/mentor_codecoolers.twig";
        final String CODECOOLER = "/static/templates/mentor/mentor_one_codecooler.twig";
        final String ARTIFACTS = "/static/templates/mentor/mentor_artifacts.twig";
        final String ARTIFACT = "/static/templates/mentor/mentor_one_artifact.twig";
        final String QUESTS = "/static/templates/mentor/mentor_quests.twig";
        final String QUEST = "/static/templates/mentor/mentor_one_quest.twig";

        if (isHomePage(uriData)) {
            return PROFILE;
        }

        String title = uriData[1];
        if (isMenu(uriData)) {
            switch (title) {
                case "profile":
                    return PROFILE;
                case "codecoolers":
                    return CODECOOLERS;
                case "artifacts":
                    return ARTIFACTS;
                case "quests":
                    return QUESTS;
                default:
                    return PROFILE;
            }
        }

        if (isAdd(uriData) || isEdit(uriData)) {
            switch (title) {
                case "codecoolers":
                    return CODECOOLER;
                case "quests":
                    return QUEST;
                case "artifacts":
                    return ARTIFACT;
            }
        }

        return LOGIN;
    }

    private boolean isHomePage(String [] uriData) {
        return uriData.length == 1;
    }

    private boolean isMenu(String [] uriData) {
        return uriData.length == 2;
    }

    private boolean isAdd(String [] uriData) {
        String action = uriData[2];
        return uriData.length == 3 && action.equals("add");
    }

    private boolean isEdit(String [] uriData) {
        if (uriData.length != 4) {
            return false;
        }
        String action = uriData[2];
        String id = uriData[3];
        return action.equals("edit") && StringUtils.isNumeric(id);
    }

    private String [] getUriData(HttpExchange httpExchange) {
        String [] uriData = httpExchange.getRequestURI().toString().split("/");
        String [] correctUri = new String [uriData.length - 1];
        if (uriData.length - 1 >= 0) System.arraycopy(uriData, 1, correctUri, 0, uriData.length - 1);
        return correctUri;
    }

    private void sendResponse(HttpExchange httpExchange, String response) throws IOException {
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private String createResponse(String [] uridata) {
        String filepath = chooseTwigFileByUri(uridata);
        JtwigModel jtwigModel = getJtwigModel(uridata);
        JtwigTemplate jtwigTemplate = JtwigTemplate.classpathTemplate(filepath);
        return jtwigTemplate.render(jtwigModel);
    }

    private JtwigModel getJtwigModel(String[] uridata) {
        JtwigModel jtwigModel = JtwigModel.newModel();

        jtwigModel.with("mentor", mentor);

        List<Codecooler> codecoolers = factoryDAO.getCodecoolerDAO().getList();
        jtwigModel.with("codecoolers", codecoolers);
        if (uridata.length == 4) {
            System.out.println("send codecooler to twig");
            jtwigModel.with("codecooler", codecoolers.get(0));
        }

        return jtwigModel;
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
