package com.codecool.controllers;

import com.codecool.model.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminController implements HttpHandler {


    // 1. mentor dao i mentors bedzie działało
    // 2. dostac sie do dobrego obiektu admina i profile bedzie działac
    // 4. w classess zliaczac mentorów i cc przypsianych

    private FactoryDAO factoryDAO = new FactoryDAO();


    private List<Mentor> mentors= new ArrayList<>();
    private List<Degree> degrees = new ArrayList<>();
    private List<Classroom> classrooms = new ArrayList<>();


    private String response = "";
    private String mainSubpage;
    private String[] uri;
    private JtwigModel model;


    public void handle(HttpExchange httpExchange) throws IOException {
        response = "";
        uri = getSplittedURI(httpExchange);
        String method = httpExchange.getRequestMethod();
        if(isGetMethod(method)){

            response = constructResponse(httpExchange);
        }

        else{
            System.out.println("post");
            servingPostMethod(httpExchange);
        }
        sendResponse(httpExchange);
    }

    private boolean isGetMethod(String method) {
        return method.equals("GET");
    }

    private String constructResponse(HttpExchange httpExchange){
        mainSubpage = uri[2];
        model = JtwigModel.newModel();

        // /login/admin/mentors/edit/1
        if (mainSubpage.equals("profile")) {
            response = getResponse("admin_profile.twig", getAdminData(1));
        }
        else if (mainSubpage.equals("mentors")) {
            displayMentorsSubpage(uri);
        }
        else if (mainSubpage.equals("classes")) {
            displayClassesSubpage(uri);
        }
        else if (mainSubpage.equals("degrees")) {
            displayDegreesSubpage(uri);
        }
        else {
            response = getResponse("admin_profile.twig", getAdminData(1));
        }
        return response;
    }

    private void displayMentorsSubpage(String[] uri) {
        loadMentorsListFromDAO();
        response = getResponse("admin_mentors.twig", getMentorsModel());
        if(uri.length==4 && uri[3].equals("admin_create_mentor")){
            response = getResponse("admin_create_mentor.twig");
        }
        else if(uri.length==5){
            response = getResponse("admin_one_mentor.twig", getMentorById(Integer.parseInt(uri[4])));
        }
        else if(uri.length==6){
            response = getResponse("admin_one_mentor_codecoolers.twig", getMentorById(Integer.parseInt(uri[4]))); // Z TEGO MENTORA TRZEBA JESZCZE WYCIAGNAC LISTE CODECOOLERSÓW
        }
    }

    private void displayDegreesSubpage(String[] uri) {
        loadDegreesListFromDAO();
        response = getResponse("admin_degrees.twig", getDegreesModel());
        if(uri.length==4 ){
            System.out.println(uri.length);
            System.out.println("weszlo w create degree");
            response = getResponse("admin_create_degree.twig");
        }
        else if(uri.length==5){
            System.out.println(uri.length);
            System.out.println("weszlo w one degree");
            response = getResponse("admin_one_degree.twig", getDegreeById(Integer.parseInt(uri[4])));
            //Integer.parseInt(uri[5])
        }
    }

    private void displayClassesSubpage(String[] uri) {
        loadClassesListFromDAO();
        response = getResponse("admin_classes.twig", getClassesModel());
        if(uri.length==4){
            response = getResponse("admin_create_class.twig");
        }
        else if(uri.length==5){
            response = getResponse("admin_one_class.twig", getClassById(Integer.parseInt(uri[4])));
        }
    }

    private void servingPostMethod(HttpExchange httpExchange) throws IOException{
        String[] uri = getSplittedURI(httpExchange);

        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String formData = br.readLine();
        Map inputs = parseFormData(formData);

        mainSubpage = uri[2];

        if (mainSubpage.equals("profile")) {
            manageProfileSubpage(uri, inputs);
        }
        else if (mainSubpage.equals("mentors")) {
            manageMentorsSubpage(uri, inputs);
        }
        else if (mainSubpage.equals("classes")) {
            manageClassesSubpage(uri, inputs);
        }
        else if (mainSubpage.equals("degrees")) {
            manageDegreesSubpage(uri, inputs);
        }
    }
    private void manageProfileSubpage(String[] uri, Map inputs){
        //AdminDAO adminDAO = factoryDAO.getAdminDAO();
        //editAdminProfile(adminDAO.get(1), inputs);
        response = getResponse("admin_profile.twig", getAdminData(1));
    }

    private void manageMentorsSubpage(String[]uri, Map inputs){
        MentorDAO mentorDAO = factoryDAO.getMentorDAO();
        if(uri.length==4 && uri[3].equals("admin_create_mentor")){
            createMentor(inputs, mentorDAO);
        }
        else if(uri.length==5){
            //Mentor mentor = mentorDAO.get(Integer.parseInt(uri[4]));
            //editMentorProfile(mentor,inputs, mentorDAO);
        }
        loadMentorsListFromDAO();
        response = getResponse("admin_mentors.twig", getMentorsModel());
    }

    private void manageDegreesSubpage(String[] uri, Map inputs){
        DegreeDAO degreeDAO = factoryDAO.getDegreeDAO();
        if(uri.length==4){
            createDegree(inputs, degreeDAO);
        }
        else if(uri.length==5){
            Degree degree = degreeDAO.get(Integer.parseInt(uri[4]));
            System.out.println(degree.getName());
            System.out.println(degree.getId());
            editDegree(degree, inputs, degreeDAO);
        }
        loadDegreesListFromDAO();
        response = getResponse("admin_degrees.twig", getDegreesModel());
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
        response = getResponse("admin_classes.twig", getClassesModel());
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
        Map<String, String> map = new HashMap();
        String[] pairs = formData.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            String value = new URLDecoder().decode(keyValue[1], "UTF-8");
            map.put(keyValue[0], value);
        }
        return map;
    }

    //METHOD GET
    private JtwigModel getAdminData(Integer id){
        Admin admin = new Admin("ziutekk","adam","adam","adamowski","adam@gmail.com","admin");
        model.with("admin", admin);
        return model;
    }

    private void loadMentorsListFromDAO(){
        MentorDAO mentorDAO = factoryDAO.getMentorDAO();
        //mentors = mentorDAO.getList();
    }

    private JtwigModel getMentorsModel(){
        model.with("mentors", mentors);
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

    private JtwigModel getMentorById(Integer id){
        MentorDAO mentorDAO = factoryDAO.getMentorDAO();
        //model.with("mentor", mentorDAO.get(id));
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
    private void editAdminProfile(Admin admin, Map inputs){
        admin.setPassword(inputs.get("password").toString());
        admin.setEmail(inputs.get("email").toString());
        //AdminDAO adminDAO = factoryDAO.getAdminDAO();
        //adminDAO.update(admin);
    }

    private void createMentor(Map inputs, MentorDAO mentorDAO){
        Mentor mentor = new Mentor(inputs.get("login").toString(), inputs.get("password").toString(), inputs.get("firstName").toString(), inputs.get("lastName").toString(), inputs.get("email").toString(),"mentor");
        mentorDAO.add(mentor);
    }

    private void editMentorProfile(Mentor mentor, Map inputs, MentorDAO mentorDAO){
        mentor.setLastName(inputs.get("lastName").toString());
        mentor.setPassword(inputs.get("password").toString());
        mentor.setEmail(inputs.get("email").toString());
        //mentor.setLastName(inputs.get("class1").toString());
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
}
