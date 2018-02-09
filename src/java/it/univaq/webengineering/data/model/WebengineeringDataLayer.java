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
    public Course getCourse(String code);
    public Course getCourse(int id);
    public List<Course> getCourses(Teacher teacher);
    public boolean insertCourse(Course t);
    public boolean existCourse(Course c);
    public boolean updateCourseBaseInfo(Course c);
    public boolean updateCourseDescription(Course c);
    public List<Course> getCoursesByFilters(String name, String language, String semester, String academic_year, String SSD);
    public boolean decouple_course(int course_id, int teacher_id);
    
    public List<Book> getBooks(Course c);
    public List<Course> getSame_as(Course course);
    public List<Course> getPreparatory(Course course);
    public Course getModule(Course course);

    public Image getImageByTeacher(int teacher_id);
    public void deleteImage(int id);
    public void deleteImageByTeacher(int teacher_id);
    public int insertImage(Image image);
    public List<Image> getImagesByCourse(int id);
}
