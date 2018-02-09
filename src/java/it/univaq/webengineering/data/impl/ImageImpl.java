package it.univaq.webengineering.data.impl;

import it.univaq.webengineering.data.model.Book;
import it.univaq.webengineering.data.model.Course;
import it.univaq.webengineering.data.model.Image;
import it.univaq.webengineering.data.model.Teacher;
import it.univaq.webengineering.framework.data.DataLayerException;
import it.univaq.webengineering.data.model.WebengineeringDataLayer;
import java.util.List;

public class ImageImpl implements Image {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }

    public String getName_on_disk() {
        return name_on_disk;
    }

    public void setName_on_disk(String name_on_disk) {
        this.name_on_disk = name_on_disk;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public WebengineeringDataLayer getDl() {
        return dl;
    }

    public void setDl(WebengineeringDataLayer dl) {
        this.dl = dl;
    }

    private int id, course_id;
    private String original_name, name_on_disk, path;
    private Course course;
    protected WebengineeringDataLayer dl;

    public ImageImpl(WebengineeringDataLayer dl) {
        this.dl = dl;
        this.course_id = 0;
        this.original_name = "";
        this.name_on_disk = "";
        this.path = "";
        this.course = null;
    }

    

}
