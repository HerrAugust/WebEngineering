package it.univaq.webengineering.data.model;

import it.univaq.webengineering.data.impl.ImageImpl;
import it.univaq.webengineering.framework.data.DataLayer;
import it.univaq.webengineering.framework.data.DataLayerException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author Giuseppe Della Penna
 */
public interface WebengineeringDataLayer extends DataLayer {

    public Teacher createTeacher(ResultSet rs);
    public boolean deleteTeacher(int teacherid);
    public Teacher getTeacher(String email, String password);
    public Teacher getTeacher(String email);
    public Teacher getTeacher(int teacherid);
    public List<Teacher> getTeachers(Course c); 
    public List<Teacher> getTeachers();
    public boolean insertTeacher(Teacher t);
    public boolean existTeacherByEmail(String email);
    public boolean updateTeacher(Teacher t);

    public boolean assignCourse(Course course, Teacher teacher);
    public void deleteCourse(String coursecode);
    public List<Course> getCourses(String code);
    public Course getCourse(int id);    
    public List<Course> getCourses();
    public Course getCourseByCodeAndAcademic_year(String code, String academic_year);
    public List<Course> getCoursesByTeacher(Teacher teacher);
    public boolean insertCourse(Course t);
    public boolean existCourse(Course c);
    public boolean updateCourseBaseInfo(Course c);
    public boolean updateCourseDescription(Course c);
    public List<Course> getCoursesByFilters(String name, String language, String semester, String academic_year, String SSD);
    public boolean decouple_course(int course_id, int teacher_id);
    
    public List<Book> getBooks(Course c);
    public List<Course> getSame_as(Course course);
    public List<Course> getPreparatory(Course course);
    public List<Course> getModule(Course course);

    public Image getImageByTeacher(int teacher_id);
    public void deleteImage(int id);
    public void deleteImageByTeacher(int teacher_id);
    public int insertImage(Image image);
    public List<Image> getImagesByCourse(int id);

    public void deletePreparatory(Course t);

    public void deleteSame_as(Course t);

    public void deleteModule(Course t);

    public void insertModule(int courseid, int parseInt);

    public void insertSame_as(int courseid, int parseInt);

    public void insertPreparatory(int courseid, int parseInt);

    public boolean insertTextbook(int courseid, String author, String title, String volume, String publisher, String weblink, int year);

    public boolean insertExternalResource(int courseid, String name, String description, String weblink);

    public List<Book> getTextbooks(int courseid);

    public List<ExternalResource> getExternalResources(int courseid);

    public boolean deleteTextbook(int courseid, int id);

    public boolean deleteExternalResource(int courseid, int id);
}
