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
        Map<String, String> data = Common.getDataFromRequest(httpExchange);

        if (isHomePage(uriData) || isMenu(uriData)) {
            editProfile(data);
            return;
        }

        String title = uriData[1];

        if (isAdd(uriData)) {
            switch (title) {
                case "codecoolers":
                    addCodecooler(data);
                case "quests":
                    addQuest(data);
                case "artifacts":
                    addArtifact(data);
            }
        }
        else if (isEdit(uriData)) {
            int id = getId(uriData);
            switch (title) {
                case "codecoolers":
                    editCodecooler(id, data);
                case "quests":
                    editQuest(id, data);
                case "artifacts":
                    editArtifact(id, data);
            }
        }
    }

    private void editArtifact(int id, Map<String, String> data) {
        ArtifactDAO artifactDAO = factoryDAO.getArtifactDAO();
        Artifact artifact = artifactDAO.get(id);
        String name = data.get("artifact_name");
        String description = data.get("artifact_description");
        int cost = Integer.valueOf(data.get("cost"));
        String category = data.get("category");

        artifact.setName(name);
        artifact.setDescription(description);
        artifact.setCost(cost);
        artifact.setCategory(category);

        artifactDAO.update(artifact);
    }

    private void editQuest(int id, Map<String, String> data) {
        QuestDAO questDAO = factoryDAO.getQuestDAO();
        Quest quest = questDAO.get(id);
        String name = data.get("quest_name");
        String description = data.get("quest_description");
        int prize = Integer.valueOf(data.get("prize"));
        String category = data.get("category");

        quest.setName(name);
        quest.setDescription(description);
        quest.setPrize(prize);
        quest.setCategory(category);

        questDAO.update(quest);
    }

    private void editCodecooler(int id, Map<String, String> data) {
        CodecoolerDAO codecoolerDAO = factoryDAO.getCodecoolerDAO();
        Codecooler codecooler = codecoolerDAO.get(id);
        String lastName = data.get("last_name");
        String password = data.get("password");
        String email = data.get("email");
        String classroom = data.get("classroom");

        codecooler.setLastName(lastName);
        codecooler.setPassword(password);
        codecooler.setEmail(email);
//        codecooler.setClassroom(new Classroom(classroom));

        codecoolerDAO.update(codecooler);
    }

    private void addArtifact(Map<String, String> data) {
        ArtifactDAO artifactDAO = factoryDAO.getArtifactDAO();
        String name = data.get("artifact_name");
        String description = data.get("artifact_description");
        int cost = Integer.valueOf(data.get("cost"));
        String category = data.get("category");

        Artifact artifact = new Artifact(name, description, cost, category);

        artifactDAO.add(artifact);

    }

    private void addQuest(Map<String, String> data) {
        QuestDAO questDAO = factoryDAO.getQuestDAO();
        String name = data.get("quest_name");
        String description = data.get("quest_description");
        int prize = Integer.valueOf(data.get("prize"));
        String category = data.get("category");

        Quest quest = new Quest(name, description, prize, category);

        questDAO.add(quest);
    }

    private void addCodecooler(Map<String, String> data) {
        String login = data.get("login");
        String password = data.get("password");
        String firstName = data.get("first_name");
        String lastName = data.get("last_name");
        String email = data.get("email");
        String appuserType = data.get("appuser_type");
        Codecooler codecooler = new Codecooler(login, password, firstName, lastName, email, appuserType);

        factoryDAO.getCodecoolerDAO();
    }

    private void editProfile(Map<String, String> data) {
        mentor.setPassword(data.get("password"));
        mentor.setEmail(data.get("email"));
        factoryDAO.getMentorDAO().update(mentor);
    }

    private String chooseTwigFileByUri(String[] uriData) {
        final String LOGIN = "/static/templates/login_page.twig";
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

        if (isEdit(uriData)) {
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
        CodecoolerDAO codecoolerDAO = factoryDAO.getCodecoolerDAO();
        QuestDAO questDAO = factoryDAO.getQuestDAO();
        ArtifactDAO artifactDAO = factoryDAO.getArtifactDAO();
        List<Codecooler> codecoolers = codecoolerDAO.getList();
        List<Quest> quests = questDAO.getList();
        List<Artifact> artifacts = artifactDAO.getList();
        JtwigModel jtwigModel = JtwigModel.newModel();

        jtwigModel.with("mentor", mentor);
        jtwigModel.with("codecoolers", codecoolers);
        jtwigModel.with("quests", quests);
        jtwigModel.with("artifacts", artifacts);

        if (isEdit(uridata)) {
            String title = uridata[1];
            int id = getId(uridata);
            switch (title) {
                case "codecoolers":
                    jtwigModel.with("codecooler", codecoolerDAO.get(id));
                    break;
                case "quests":
                    jtwigModel.with("quest", questDAO.get(id));
                    break;
                case "artifacts":
                    jtwigModel.with("artifact", artifactDAO.get(id));
                    break;
            }
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
