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

    private FactoryDAO factoryDAO = new FactoryDAO();


    private List<Mentor> mentors= new ArrayList<>();
    private List<Degree> degrees = new ArrayList<>();
    private List<Classroom> classrooms = new ArrayList<>();
    private List<Admin> admins = new ArrayList<>();


    private Integer id;
    private String response = "";
    private JtwigModel model;
    public void handle(HttpExchange httpExchange) throws IOException {

        response = "";
        String method = httpExchange.getRequestMethod();

        if(isGetMethod(method)){
            response = constructResponse(httpExchange);
        }

        else{
            servingPostMethod(httpExchange);
        }
        sendResponse(httpExchange);
    }


    private boolean isGetMethod(String method) {
        return method.equals("GET");
    }

    private String constructResponse(HttpExchange httpExchange){
        String[] uri = getSplittedURI(httpExchange);
        String mainSubpage = uri[3];
        model = JtwigModel.newModel();
        if (mainSubpage.equals("profile")) {
            response = getResponse("templates/admin_profile.twig", getAdminData(1));
        }
        else if (mainSubpage.equals("mentors")) {
            loadMentorsListFromDAO();
            response = getResponse("templates/admin_mentors.twig", getMentorsModel());
            if(uri.length==5 && uri[4].equals("admin_create_mentor")){
                response = getResponse("templates/admin_create_mentor.twig");
            }
            else if(uri.length==6){
                response = getResponse("templates/admin_one_mentor.twig", getMentorById(Integer.parseInt(uri[5])));
            }
            else if(uri.length==7){
                response = getResponse("templates/admin_one_mentor_codecoolers.twig", getMentorById(Integer.parseInt(uri[5]))); // Z TEGO MENTORA TRZEBA JESZCZE WYCIAGNAC LISTE CODECOOLERSÓW
            }
        }
        else if (mainSubpage.equals("classes")) {
            loadClassesListFromDAO();
            response = getResponse("templates/admin_classes.twig", getClassesModel());
            if(uri.length==5){
                response = getResponse("templates/admin_create_class.twig");
            }
            else if(uri.length==6){
                response = getResponse("templates/admin_one_class.twig", getClassById(Integer.parseInt(uri[5])));
            }
        }
        else if (mainSubpage.equals("degrees")) {
            loadDegreesListFromDAO();
            response = getResponse("templates/admin_degrees.twig", getDegreesModel());
            if(uri.length==5){
                response = getResponse("templates/admin_create_degree.twig");
            }
            else if(uri.length==6){
                response = getResponse("templates/admin_one_degree.twig", getDegreeById(Integer.parseInt(uri[5])));
            }
        }
        else {
            response = getResponse("templates/admin_profile.twig", getAdminData(1));
        }
        return response;
    }

    private void servingPostMethod(HttpExchange httpExchange) throws IOException{
        String[] uri = getSplittedURI(httpExchange);

        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String formData = br.readLine();
        Map inputs = parseFormData(formData);

        String mainSubpage = uri[3];

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
        response = getResponse("templates/admin_profile.twig", getAdminData(1));
    }

    private void manageMentorsSubpage(String[]uri, Map inputs){
        MentorDAO mentorDAO = factoryDAO.getMentorDAO();
        if(uri.length==5 && uri[4].equals("admin_create_mentor")){
            createMentor(inputs, mentorDAO);
        }
        else if(uri.length==6){
            //Mentor mentor = mentorDAO.get(Integer.parseInt(uri[5]));
            //editMentorProfile(mentor,inputs, mentorDAO);
        }
        loadMentorsListFromDAO();
        response = getResponse("templates/admin_mentors.twig", getMentorsModel());
    }

    private void manageDegreesSubpage(String[] uri, Map inputs){
        DegreeDAO degreeDAO = factoryDAO.getDegreeDAO();
        if(uri.length==5){
            createDegree(inputs, degreeDAO);
        }
        else if(uri.length==6){
            Degree degree = degreeDAO.get(Integer.parseInt(uri[5]));
            editDegree(degree, inputs, degreeDAO);
        }
        loadDegreesListFromDAO();
        response = getResponse("templates/admin_degrees.twig", getDegreesModel());
    }

    private void manageClassesSubpage(String[] uri, Map inputs){
        ClassroomDAO classroomDAO = factoryDAO.getClassroomDAO();
        if(uri.length==5){
            createClassroom(inputs, classroomDAO);
        }
        else if(uri.length==6){
            Classroom classroom = classroomDAO.get(Integer.parseInt(uri[5]));
            editClassroom(classroom,inputs, classroomDAO);
        }
        loadClassesListFromDAO();
        response = getResponse("templates/admin_classes.twig", getClassesModel());
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
        //AdminDAO adminDAO = factoryDAO.getAdminDAO;
        //Admin admin = adminDAO.get(id); //SKĄD TUTAJ WZAIC ID ZALOGOWANEGO ADMINA
        //model.with("admin_profile", admin);
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

    private JtwigModel getMentorById(int id){
        MentorDAO mentorDAO = factoryDAO.getMentorDAO();
        //model.with("mentor", mentorDAO.get(id));
        return model;
    }

    private JtwigModel getClassById(int id){
        ClassroomDAO classroomDAO= factoryDAO.getClassroomDAO();
        model.with("classroom", classroomDAO.get(id) );
        return model;
    }

    private JtwigModel getDegreeById(int id){
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
