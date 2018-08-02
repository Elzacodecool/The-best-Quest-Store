package com.codecool.controllers;

import com.codecool.model.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.apache.commons.lang3.StringUtils;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MentorController implements HttpHandler {
    private Mentor mentor;
    private FactoryDAO factoryDAO = new FactoryDAO();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        login(httpExchange);

        String method = httpExchange.getRequestMethod();

        if (method.equals("GET")) {
            String response = createResponse(httpExchange);
            Common.sendResponse(httpExchange, response);
        }
        if (method.equals("POST")) {
            updateData(httpExchange);
        }
    }

    private void updateData(HttpExchange httpExchange) {
        String [] uriData = getUriData(httpExchange);

        if (isHomePage(uriData) || isMenu(uriData)) {
            editProfile(httpExchange);
            return;
        }

        String title = uriData[1];

        if (isAdd(uriData)) {
            switch (title) {
                case "codecoolers":
                    addCodecooler(httpExchange);
                case "quests":
                    addQuest(httpExchange);
                case "artifacts":
                    addArtifact(httpExchange);
            }
        }
        else if (isEdit(uriData)) {
            switch (title) {
                case "codecoolers":
                    editCodecooler(httpExchange);
                case "quests":
                    editQuest(httpExchange);
                case "artifacts":
                    editArtifact(httpExchange);
            }
        }
    }

    private void editArtifact(HttpExchange httpExchange) {

    }

    private void editQuest(HttpExchange httpExchange) {

    }

    private void editCodecooler(HttpExchange httpExchange) {

    }

    private void addArtifact(HttpExchange httpExchange) {

    }

    private void addQuest(HttpExchange httpExchange) {

    }

    private void addCodecooler(HttpExchange httpExchange) {

    }

    private void editProfile(HttpExchange httpExchange) {


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



    private String createResponse(HttpExchange httpExchange) {
        String [] uriData = getUriData(httpExchange);
        String filepath = chooseTwigFileByUri(uriData);
        JtwigModel jtwigModel = getJtwigModel(uriData);
        JtwigTemplate jtwigTemplate = JtwigTemplate.classpathTemplate(filepath);
        return jtwigTemplate.render(jtwigModel);
    }

    private JtwigModel getJtwigModel(String[] uridata) {
        List<Codecooler> codecoolers = factoryDAO.getCodecoolerDAO().getList();
        List<Quest> quests = factoryDAO.getQuestDAO().getList();
        List<Artifact> artifacts = factoryDAO.getArtifactDAO().getList();
        JtwigModel jtwigModel = JtwigModel.newModel();

        jtwigModel.with("mentor", mentor);
        jtwigModel.with("codecoolers", codecoolers);
        jtwigModel.with("quests", quests);
        jtwigModel.with("artifacts", artifacts);

        if (isEdit(uridata)) {
            int id = getId(uridata);
            int index = id - 1;
            jtwigModel.with("codecooler", codecoolers.get(index));
            jtwigModel.with("quest", quests.get(index));
            jtwigModel.with("artifact", artifacts.get(index));
        }

        return jtwigModel;
    }

    private int getId(String[] uridata) {
        return Integer.valueOf(uridata[3]);
    }

    private void login(HttpExchange httpExchange) {
        if (mentor != null) {
            return;
        }
        String login = Common.getLogin(Common.getCookie(httpExchange));
        List<Mentor> mentors = factoryDAO.getMentorDAO().getList();
        for (Mentor mentor : mentors) {
            if (mentor.getLogin().equals(login)) {
                this.mentor = mentor;
                return;
            }
        }
        Common.redirect(httpExchange, "login");
    }
}
