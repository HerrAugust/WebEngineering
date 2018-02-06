package it.univaq.webengineering.data.impl;

import it.univaq.webengineering.data.model.Course;
import it.univaq.webengineering.data.model.Image;
import it.univaq.webengineering.data.model.Teacher;
import it.univaq.webengineering.framework.data.DataLayerException;
import it.univaq.webengineering.data.model.WebengineeringDataLayer;
import java.util.List;

public class CourseImpl implements Course {

    private int id, semester;
    private String code = "", name = "", SSD = "", language = "", academic_year = "", prerequisites = "", learning_outcomes = "", assessment_method = "", teaching_method = "", syllabus = "", homepage = "", forum = "", notes = "";

    protected WebengineeringDataLayer dl;
    private String prerequisites_ita = "";
    private String learning_outcomes_ita = "";
    private String assessment_method_ita = "";
    private String teaching_method_ita = "";
    private String notes_ita = "";
    private String syllabus_ita = "";
    
    private List<Teacher> teachers = null;

    public CourseImpl(WebengineeringDataLayer dl) {
        this.dl = dl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public List<Teacher> getTeachers() {
        return this.teachers;
    }
    
    public void setTeachers(List<Teacher> l) {
        this.teachers = l;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSSD() {
        return SSD;
    }

    public void setSSD(String SSD) {
        this.SSD = SSD;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getAcademic_year() {
        return academic_year;
    }

    public void setAcademic_year(String academic_year) {
        this.academic_year = academic_year;
    }

    public String getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(String prerequisites) {
        if(prerequisites == null) prerequisites = "";
        this.prerequisites = prerequisites;
    }

    public String getLearning_outcomes() {
        return learning_outcomes;
    }

    public void setLearning_outcomes(String learning_outcomes) {
        if(learning_outcomes == null) learning_outcomes = "";
        this.learning_outcomes = learning_outcomes;
    }

    public String getAssessment_method() {
        return assessment_method;
    }

    public void setAssessment_method(String am) {
        if(am == null) am = "";
        this.assessment_method = assessment_method;
    }

    public String getTeaching_method() {
        return teaching_method;
    }

    public void setTeaching_method(String teaching_method) {
        if(teaching_method == null) teaching_method = "";
        this.teaching_method = teaching_method;
    }

    public String getSyllabus() {
        return syllabus;
    }

    public void setSyllabus(String syllabus) {
        if(syllabus == null) syllabus = "";
        this.syllabus = syllabus;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        if(homepage == null) homepage = "";
        this.homepage = homepage;
    }

    public String getForum() {
        return forum;
    }

    public void setForum(String forum) {
        if(forum == null) forum = "";
        this.forum = forum;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        if(notes == null) notes = "";
        this.notes = notes;
    }

    @Override
    public String getPrerequisites_ita() {
        return prerequisites_ita;
    }

    @Override
    public String getLearning_outcomes_ita() {
        return this.learning_outcomes_ita;
    }

    @Override
    public String getAssessment_method_ita() {
        return this.assessment_method_ita;
    }

    @Override
    public String getTeaching_method_ita() {
        return this.teaching_method_ita;
    }

    @Override
    public String getNotes_ita() {
        return this.notes_ita;
    }

    @Override
    public String getSyllabus_ita() {
        return this.syllabus_ita;
    }

    @Override
    public void setPrerequisites_ita(String s) {
        if(s == null) s = "";
        this.prerequisites_ita = s;
    }

    @Override
    public void setLearning_outcomes_ita(String s) {
        if(s == null) s = "";
        learning_outcomes_ita = s;
    }

    @Override
    public void setAssessment_method_ita(String s) {
        if(s == null) s = "";
        this.assessment_method_ita = s;
    }

    @Override
    public void setTeaching_method_ita(String s) {
        if(s == null) s = "";
        this.teaching_method_ita = s;
    }

    @Override
    public void setNotes_ita(String s) {
        if(s == null) s = "";
        this.notes_ita = s;
    }

    @Override
    public void setSyllabus_ita(String s) {
        if(s == null) s = "";
        this.syllabus_ita = s;
    }
}
