package it.univaq.webengineering.data.model;

public interface Teacher {

    int getId();

    String getName();

    String getLastname();
    
    String getLanguage();
    
    String getType();
    
    String getEmail();
    
    String getPassword();
    
    void setId(int id);

    void setName(String name);

    void setLastname(String lastname);

    void setLanguage(String lang);
    
    void setType(String type);
    
    void setEmail(String email);
    
    void setPassword(String password);
    
    boolean isAdmin();
}
