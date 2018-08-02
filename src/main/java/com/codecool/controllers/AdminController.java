package com.codecool.controllers;

import com.codecool.model.*;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.HttpCookie;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminController implements HttpHandler {

    //nie dziala update mentora
    // nie dziala validacja

    private FactoryDAO factoryDAO = new FactoryDAO();

    private List<Mentor> mentors= new ArrayList<>();
    private List<Degree> degrees = new ArrayList<>();
    private List<Classroom> classrooms = new ArrayList<>();
    private List<AppUser> appUsers = new ArrayList<>();

    private String response = "";
    private String mainSubpage;
    private String[] uri;
    private JtwigModel model = JtwigModel.newModel();
    private AppUser admin;
    private Mentor mentor;
    private String login;


    public void handle(HttpExchange httpExchange) throws IOException {
        login(httpExchange);
        uri = getSplittedURI(httpExchange);
        if(uri.length==2){
            redirect(httpExchange,"admin/profile");
        }
        String method = httpExchange.getRequestMethod();
        if(isGetMethod(method)){
            response = constructResponse();
        }
        else{
            servingPostMethod(httpExchange);
        }
        sendResponse(httpExchange);
    }

    private boolean isGetMethod(String method) {
        return method.equals("GET");
    }

    private String constructResponse(){
        mainSubpage = uri[2];
        model = JtwigModel.newModel();
        switch (mainSubpage) {
            case "profile":
                displayProfileSubpage();
                break;
            case "mentors":
                displayMentorsSubpage(uri);
                break;
            case "classes":
                displayClassesSubpage(uri);
                break;
            case "degrees":
                displayDegreesSubpage(uri);
                break;
        }
        return response;
    }

    private void displayProfileSubpage(){
        loadAppUsersFromDAO();
        response = getResponse("/static/templates/admins/admin_profile.twig", getAdminModel(login));
    }

    private void displayMentorsSubpage(String[] uri) {
        loadMentorsListFromDAO();
        response = getResponse("/static/templates/admins/admin_mentors.twig", getMentorsModel());
        if(uri.length==4 && uri[3].equals("create_mentor")){
            response = getResponse("/static/templates/admins/admin_create_mentor.twig");
        }
        else if(uri.length==5){
            response = getResponse("/static/templates/admins/admin_one_mentor.twig", getOneMentorModel(Integer.parseInt(uri[4])));
        }
        else if(uri.length==6){
            response = getResponse("/static/templates/admins/admin_one_mentor_codecoolers.twig", getOneMentorModel(Integer.parseInt(uri[4]))); // Z TEGO MENTORA TRZEBA JESZCZE WYCIAGNAC LISTE CODECOOLERSÓW
        }
    }

    private void displayDegreesSubpage(String[] uri) {
        loadDegreesListFromDAO();
        response = getResponse("/static/templates/admins/admin_degrees.twig", getDegreesModel());
        if(uri.length==4 ){
            response = getResponse("/static/templates/admins/admin_create_degree.twig");
        }
        else if(uri.length==5){
            response = getResponse("/static/templates/admins/admin_one_degree.twig", getDegreeById(Integer.parseInt(uri[4])));
        }
    }

    private void displayClassesSubpage(String[] uri) {
        loadClassesListFromDAO();
        response = getResponse("/static/templates/admins/admin_classes.twig", getClassesModel());
        if(uri.length==4){
            response = getResponse("/static/templates/admins/admin_create_class.twig");
        }
        else if(uri.length==5){
            response = getResponse("/static/templates/admins/admin_one_class.twig", getClassById(Integer.parseInt(uri[4])));
        }
    }

    private void servingPostMethod(HttpExchange httpExchange) throws IOException{
        String[] uri = getSplittedURI(httpExchange);
        Map inputs = getInputsMap(httpExchange);
        mainSubpage = uri[2];

        switch (mainSubpage) {
            case "profile":
                manageProfileSubpage(inputs);
                break;
            case "mentors":
                manageMentorsSubpage(uri, inputs);
                break;
            case "classes":
                manageClassesSubpage(uri, inputs);
                break;
            case "degrees":
                manageDegreesSubpage(uri, inputs);
                break;
        }
    }

    private Map getInputsMap(HttpExchange httpExchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String formData = br.readLine();
        return parseFormData(formData);
    }

    private void manageProfileSubpage(Map inputs){
        getAdminData(login);
        editAdminProfile(admin, inputs);
        displayProfileSubpage();
    }

    private void manageMentorsSubpage(String[]uri, Map inputs){
        MentorDAO mentorDAO = factoryDAO.getMentorDAO();
        if(uri.length==4 && uri[3].equals("create_mentor")){
            createMentor(inputs, mentorDAO);
        }
        else if(uri.length==5){
            Mentor mentor = mentorDAO.get(Integer.parseInt(uri[4]));

            editMentorProfile(mentor,inputs, mentorDAO);
        }
        loadMentorsListFromDAO();
        response = getResponse("/static/templates/admins/admin_mentors.twig", getMentorsModel());
    }

    private void manageDegreesSubpage(String[] uri, Map inputs){
        DegreeDAO degreeDAO = factoryDAO.getDegreeDAO();
        if(uri.length==4){
            createDegree(inputs, degreeDAO);
        }
        else if(uri.length==5){
            Degree degree = degreeDAO.get(Integer.parseInt(uri[4]));
            editDegree(degree, inputs, degreeDAO);
        }
        loadDegreesListFromDAO();
        response = getResponse("/static/templates/admins/admin_degrees.twig", getDegreesModel());
    }

    private void manageClassesSubpage(String[] uri, Map inputs){
        ClassroomDAO classroomDAO = factoryDAO.getClassroomDAO();
        if(uri.length==4){
            createClassroom(inputs, classroomDAO);
        }
        else if(uri.length==5){
            Classroom classroom = classroomDAO.get(Integer.parseInt(uri[4]));
            editClassroom(classroom,inputs, classroomDAO);
        }
        loadClassesListFromDAO();
        response = getResponse("/static/templates/admins/admin_classes.twig", getClassesModel());
    }

    private String[] getSplittedURI(HttpExchange httpExchange){
        return httpExchange.getRequestURI().toString().split("/");
    }

    private void sendResponse(HttpExchange httpExchange) throws IOException{
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private String getResponse(String templatePath, JtwigModel jtwigModel){
        JtwigTemplate template = JtwigTemplate.classpathTemplate(templatePath);
        return template.render(jtwigModel);
    }

    private String getResponse(String templatePath){
        JtwigTemplate template = JtwigTemplate.classpathTemplate(templatePath);
        model = JtwigModel.newModel();
        return template.render(model);
    }

    private static Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException {
        HashMap map = new HashMap();
        String[] pairs = formData.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            String value = new URLDecoder().decode(keyValue[1], "UTF-8");
            map.put(keyValue[0], value);
        }
        return map;
    }

    //METHOD GET
    private void getAdminData(String login) {
        admin = factoryDAO.getAppUserDAO().get(login);
    }

    private JtwigModel getAdminModel(String login){
        getAdminData(login);
        model.with("admin", admin);
        return model;
    }

    private void loadAppUsersFromDAO() {
        appUsers = factoryDAO.getAppUserDAO().getList();
    }

    private void loadMentorsListFromDAO(){
        MentorDAO mentorDAO = factoryDAO.getMentorDAO();
        mentors = mentorDAO.getList();
    }

    private JtwigModel getMentorsModel(){
        model.with("mentors", mentors);
        return model;
    }
    private void getMentorData(Integer id) {
        mentor = factoryDAO.getMentorDAO().get(id);
    }
    private JtwigModel getOneMentorModel(Integer id){
        getMentorData(id);
        model.with("mentor", mentor);
        return model;
    }

    private void loadClassesListFromDAO(){
        ClassroomDAO classroomDAO = factoryDAO.getClassroomDAO();
        classrooms = classroomDAO.getList();
    }

    private JtwigModel getClassesModel(){
        model.with("classrooms", classrooms);  //SKAD TUTAJ WZIAC ILOSC MENTORÓW I CODECOOLERÓW W DANEJ KLASIE
        return model;
    }

    private void loadDegreesListFromDAO(){
        DegreeDAO degreeDAO = factoryDAO.getDegreeDAO();
        degrees = degreeDAO.getList();
    }

    private JtwigModel getDegreesModel(){
        model.with("degrees", degrees);
        return model;
    }

    private JtwigModel getClassById(Integer id){
        ClassroomDAO classroomDAO= factoryDAO.getClassroomDAO();
        model.with("classroom", classroomDAO.get(id) );
        return model;
    }

    private JtwigModel getDegreeById(Integer id){
        DegreeDAO degreeDAO = factoryDAO.getDegreeDAO();
        model.with("degree", degreeDAO.get(id) );
        return model;
    }

    //METHOD POST
    private void editAdminProfile(AppUser admin, Map inputs){
        admin.setPassword(inputs.get("password").toString());
        admin.setEmail(inputs.get("email").toString());
        AppUserDAO adminDAO = factoryDAO.getAppUserDAO();
        adminDAO.update(admin);
    }

    private void createMentor(Map inputs, MentorDAO mentorDAO){
        Mentor mentor = new Mentor(inputs.get("login").toString(), inputs.get("password").toString(), inputs.get("firstName").toString(), inputs.get("lastName").toString(), inputs.get("email").toString(),"mentor");
        mentorDAO.add(mentor);
    }

    private void editMentorProfile(Mentor mentor, Map inputs, MentorDAO mentorDAO){

        mentor.setLastName(inputs.get("lastName").toString());
        mentor.setPassword(inputs.get("password").toString());
        mentor.setEmail(inputs.get("email").toString());
        mentorDAO.update(mentor);
    }

    private void createClassroom(Map inputs, ClassroomDAO classroomDAO){
        Classroom classroom = new Classroom(inputs.get("classroomName").toString());
        classroomDAO.add(classroom);
    }

    private void editClassroom(Classroom classroom, Map inputs, ClassroomDAO classroomDAO){
        classroom.setName(inputs.get("className").toString());
        classroomDAO.update(classroom);
    }

    private void createDegree(Map inputs, DegreeDAO degreeDAO){
        Degree degree = new Degree(inputs.get("degreeName").toString(), Integer.parseInt(inputs.get("minCoolcoins").toString()));
        degreeDAO.add(degree);
    }

    private void editDegree(Degree degree, Map inputs, DegreeDAO degreeDAO){
        degree.setName(inputs.get("degreeName").toString());
        degree.setMinEarnedCoolcoins(Integer.parseInt(inputs.get("minCoolcoins").toString()));
        degreeDAO.update(degree);
    }

    // Loging
    private void login(HttpExchange httpExchange) {
        if (admin != null) {
            return;
        }
        loadAppUsersFromDAO();
        login = getLogin(getCookie(httpExchange));

        for (AppUser admin : appUsers) {
            if (admin.getLogin().equals(login)) {
                this.admin = admin;
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