package it.univaq.webengineering.data.model;

import java.util.List;

public interface Course {

    int getId();

    String getName();

    String getCode();
    
    String getSSD();
    
    String getLanguage();
    
    int getSemester();
    
    String getAcademic_year();
    
    String getPrerequisites();
    
    String getLearning_outcomes();
    
    String getAssessment_method();
    
    String getTeaching_method();
    
    String getSyllabus();
    
    String getHomepage();
    
    String getForum();
    
    String getNotes();
    
    List<Teacher> getTeachers();
    
    List<Book> getBooks();
    
    void setBooks(List<Book> b);
    
    void setTeachers(List<Teacher> l);
    
    void setId(int id);

    void setName(String name);

    void setCode(String code);

    void setLanguage(String lang);
    
    void setSSD(String ssd);
    
    void setSemester(int semester);
    
    void setAcademic_year(String ay);
    
    void setPrerequisites(String prerequisites);
    
    void setLearning_outcomes(String lo);
    
    void setAssessment_method(String am);
    
    void setTeaching_method(String tm);
    
    void setSyllabus(String syllabus);
    
    void setHomepage(String homepage);
    
    void setForum(String forum);
    
    void setNotes(String notes);

    public String getPrerequisites_ita();

    public String getLearning_outcomes_ita();

    public String getAssessment_method_ita();

    public String getTeaching_method_ita();
    
    public String getNotes_ita();
    
    public String getSyllabus_ita();
   
    public List<Course> getPreparatory();
    
    public List<Course> getSame_as();
    
    public Course getModule();
    
    public void setPrerequisites_ita(String s);

    public void setLearning_outcomes_ita(String s);

    public void setAssessment_method_ita(String s);

    public void setTeaching_method_ita(String s);
    
    public void setNotes_ita(String s);
    
    public void setSyllabus_ita(String s);
    
    public void setSame_as(List<Course> c);
    
    public void setPreparatory(List<Course> c);
    
    public void setModule(Course c);
}
