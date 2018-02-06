package it.univaq.webengineering.data.impl;

import it.univaq.webengineering.data.model.Image;
import it.univaq.webengineering.data.model.Teacher;
import it.univaq.webengineering.framework.data.DataLayerException;
import it.univaq.webengineering.data.model.WebengineeringDataLayer;
import java.util.List;

public class TeacherImpl implements Teacher {

    private int id;
    private String name, lastname, language, type, email, password;
    protected WebengineeringDataLayer dl;

    public TeacherImpl(WebengineeringDataLayer dl) {
        this.dl = dl;
        this.name = "";
        this.lastname = "";
        this.language = "";
        this.type = "";
        this.email = "";
        this.password = "";
    }

    @Override
    public int getId() {
        return this.id;
    }
    
     public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public boolean isAdmin() {
        return this.getType().equals("admin");
    }
    
}
