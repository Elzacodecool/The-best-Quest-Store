package com.codecool.controllers;

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

    private List<Mentor> mentors= new ArrayList<Mentor>();
    private List<Degree> degrees = new ArrayList<Degree>();
    private List<Classroom> classrooms = new ArrayList<Classroom>();


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
            response = getResponse("templates/admin_profile.twig", getAdminData());
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
            response = getResponse("templates/admin_profile.twig", getAdminData());
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
            editAdminProfile();
            response = getResponse("templates/admin_profile.twig", getAdminData());
        }
        else if (mainSubpage.equals("mentors")) {
            if(uri.length==5 && uri[4].equals("admin_create_mentor")){
                createMentor(inputs);
            }
            else if(uri.length==6){
                editMentorProfile();
            }
            loadMentorsListFromDAO();
            response = getResponse("templates/admin_mentors.twig", getMentorsModel());
        }
        else if (mainSubpage.equals("classes")) {

            if(uri.length==5){
                createClassroom(inputs);
            }
            else if(uri.length==6){
                editClassroom();
            }
            loadClassesListFromDAO();
            response = getResponse("templates/admin_classes.twig", getClassesModel());
        }
        else if (mainSubpage.equals("degrees")) {
            if(uri.length==5){
                createDegree(inputs);
            }
            else if(uri.length==6){
                editDegree();
            }
            loadDegreesListFromDAO();
            response = getResponse("templates/admin_degrees.twig", getDegreesModel());
        }
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
            // We have to decode the value because it's urlencoded. see: https://en.wikipedia.org/wiki/POST_(HTTP)#Use_for_submitting_web_forms
            String value = new URLDecoder().decode(keyValue[1], "UTF-8");
            map.put(keyValue[0], value);
        }
        return map;
    }


    //METHOD GET
    private JtwigModel getAdminData(){
        AdminDAO adminDAO = new AdminDAO();
        Admin admin = adminDAO.get(1); //SKĄD TUTAJ WZAIC ID ZALOGOWANEGO ADMINA

        model.with("admin_profile", admin);
        return model;
    }

    private void loadMentorsListFromDAO(){
        MentorDAO mentorDAO = new MentorDAO();
        mentors = mentorDAO.getList();
    }

    private JtwigModel getMentorsModel(){
        model.with("mentors", mentors);
        return model;
    }

    private void loadClassesListFromDAO(){
        ClassroomDAO classroomDAO = new ClassroomDAO();
        classrooms = classroomDAO.getList();
    }

    private JtwigModel getClassesModel(){
        model.with("classrooms", classrooms);  //SKAD TUTAJ WZIAC ILOSC MENTORÓW I CODECOOLERÓW W DANEJ KLASIE
        return model;
    }

    private void loadDegreesListFromDAO(){
        DegreeDAO degreeDAO = new DegreeDAO();
        degrees = degreeDAO.getList();
    }

    private JtwigModel getDegreesModel(){
        model.with("degrees", degrees);
        return model;
    }

    private JtwigModel getMentorById(int id){
        model.with("mentor", mentors.get(id) );
        return model;
    }

    private JtwigModel getClassById(int id){
        model.with("classroom", classrooms.get(id) );
        return model;
    }

    private JtwigModel getDegreeById(int id){
        model.with("degree", degrees.get(id) );
        return model;
    }

    //METHOD POST
    private void editAdminProfile(){

    }

    private void createMentor(Map inputs){
        Mentor mentor = new Mentor(inputs.get("firstName"), inputs.get("lastName"), inputs.get("login"), inputs.get("password"), inputs.get("email"));
    }

    private void editMentorProfile(){

    }

    private void createClassroom(Map inputs){
        Classroom classroom = new Classroom(inputs.get("classroomName"));

    }

    private void editClassroom(){

    }

    private void createDegree(Map inputs){
        Degree degree = new Degree(inputs.get("degreeName"), inputs.get("minCoolcoins"));
    }

    private void editDegree(){

    }
}
