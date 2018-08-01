package com.codecool.model;

import java.util.ArrayList;
import java.util.List;

public class MentorDAO {
    
    public void add(Mentor codecooler) {

    }

    public void update(Mentor codecooler) {

    }

    public Mentor get(Integer id) {
        return null;
    }

    public List<Mentor> getList() {
        List<Mentor> mentors = new ArrayList<>();
        Mentor mentor = new Mentor("ak", "m", "name", "lastname", "email@k2.pl", "mentor");
        mentors.add(mentor);
        return mentors;
    }

}