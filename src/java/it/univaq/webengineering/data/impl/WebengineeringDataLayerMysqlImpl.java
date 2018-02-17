package it.univaq.webengineering.data.impl;

import it.univaq.webengineering.data.model.Book;
import it.univaq.webengineering.data.model.Teacher;
import it.univaq.webengineering.framework.data.DataLayerException;
import it.univaq.webengineering.data.model.Course;
import it.univaq.webengineering.data.model.ExternalResource;
import it.univaq.webengineering.data.model.Image;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import javax.sql.DataSource;
import it.univaq.webengineering.data.model.WebengineeringDataLayer;
import it.univaq.webengineering.framework.data.DataLayerMysqlImpl;
import it.univaq.webengineering.framework.security.SecurityLayer;
import java.sql.Connection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HerrAugust on work by Giuseppe Della Penna
 */
public class WebengineeringDataLayerMysqlImpl extends DataLayerMysqlImpl implements WebengineeringDataLayer {

    private PreparedStatement sTeacherByEmailPassword;
    private PreparedStatement sTeacherByEmail;
    private PreparedStatement sCoursesByCode;
    private PreparedStatement sCourses;
    
    private PreparedStatement dCourse;
    
    private PreparedStatement sTeachers;
    private PreparedStatement iCourseTeacher;
    private PreparedStatement dCourseTeacher;
    private PreparedStatement sTeacherByID;
    private PreparedStatement sCourseByID;
    private PreparedStatement iTeacher;
    private PreparedStatement dTeacher;
    private PreparedStatement iCourse;
    private PreparedStatement sCourseByCodeAndName;
    private PreparedStatement uTeacher_withPassword;
    private PreparedStatement uTeacher;
    private PreparedStatement sCoursesOfTeacher;
    private PreparedStatement uCourseDescription;
    private PreparedStatement uCourseBasicInfo;
    private PreparedStatement sTeacherByCourse;
    private PreparedStatement sTeachersByCourse;
    private PreparedStatement sBooksByCourse;
    private PreparedStatement sCoursesPreparatory;
    private PreparedStatement sCoursesSame_as;
    private PreparedStatement sCourseModule;
    private PreparedStatement dTeach;
    private PreparedStatement dImage;
    private PreparedStatement iImage;
    private PreparedStatement sImageByTeacher;
    private PreparedStatement sImage;
    private PreparedStatement sImagesByCourse;
    private PreparedStatement sCourseByCodeAndAcademic_year;
    private PreparedStatement dModule;
    private PreparedStatement dSame_as;
    private PreparedStatement dPreparatory;
    private PreparedStatement iSame_as;
    private PreparedStatement iModule;
    private PreparedStatement iPreparatory;
    private PreparedStatement iExternalResource;
    private PreparedStatement iSupport;
    private PreparedStatement iUses;
    private PreparedStatement iTextbook;
    private PreparedStatement sExternalResourcesByCourse;
    private PreparedStatement dExternalResource;
    private PreparedStatement dTextbook;
    private PreparedStatement dUses;
    private PreparedStatement dSupport;
    private PreparedStatement sCloserCourseProperty;

    public WebengineeringDataLayerMysqlImpl(DataSource datasource) throws SQLException, NamingException {
        super(datasource);
    }

    @Override
    public void init() throws DataLayerException {
        try {
            super.init();

            //precompile all the queries uses in this class
            sTeacherByEmailPassword  = connection.prepareStatement("SELECT * FROM teacher WHERE email=? and password=?");
            sTeacherByEmail          = connection.prepareStatement("SELECT id, name, lastname, language, type, photo, email FROM teacher WHERE email=?");
            sTeacherByCourse         = connection.prepareStatement("SELECT teacher.id, teacher.name, teacher.lastname, teacher.language, teacher.type, teacher.photo, teacher.email from teacher join teach on teacher.id = teach.teacher_id where teach.course_id = ?");
            sTeachers                = connection.prepareStatement("SELECT id FROM teacher");
            sTeacherByID             = connection.prepareStatement("SELECT * FROM teacher WHERE id=?");
            sTeachersByCourse        = connection.prepareStatement("SELECT * FROM teacher JOIN teach ON teach.teacher_id = teacher.id WHERE teach.course_id = ?");
            sCoursesByCode = connection.prepareStatement("SELECT * FROM course WHERE code=?");
            sCourseByID = connection.prepareStatement("SELECT * FROM course WHERE id=?");
            sCourseByCodeAndAcademic_year = connection.prepareStatement("SELECT * FROM course WHERE code=? AND academic_year = ?");
            sCourses  = connection.prepareStatement("SELECT id FROM course");
            sCourseByCodeAndName = connection.prepareStatement("SELECT * FROM course WHERE code=? AND name=?");
            sCoursesOfTeacher = connection.prepareStatement("SELECT * FROM course JOIN teach ON course.id=teach.course_id JOIN teacher ON teacher.id=teach.teacher_id WHERE teacher.id=?");
            sBooksByCourse = connection.prepareStatement("SELECT * FROM book JOIN uses ON uses.book_id = book.id WHERE uses.course_id = ?");
            sCoursesPreparatory = connection.prepareStatement("SELECT course.* FROM preparatory JOIN course ON preparatory.requires = course.id WHERE preparatory.course_id = ?");
            sCoursesSame_as = connection.prepareStatement("select course.* from same_as join course on same_as.same_as_course_id = course.id WHERE same_as.course_id = ? UNION select course.* from same_as join course on same_as.course_id = course.id WHERE same_as.same_as_course_id = ?");
            sCourseModule = connection.prepareStatement(" select course.* from module join course on module.module_course_id = course.id WHERE module.course_id = ? UNION select course.* from module join course on module.course_id = course.id WHERE module.module_course_id = ?");
            sImage = connection.prepareStatement("SELECT * FROM image WHERE id = ?");
            sImageByTeacher = connection.prepareStatement("SELECT image.* FROM teacher JOIN image ON teacher.photo = image.id WHERE teacher.id = ?");
            sImagesByCourse = connection.prepareStatement("SELECT image.* FROM image JOIN course ON image.course_id = course.id WHERE course.id = ?");
            sBooksByCourse = connection.prepareStatement("SELECT book.* FROM uses JOIN book ON book.id = uses.book_id WHERE uses.course_id = ?");
            sExternalResourcesByCourse = connection.prepareStatement("SELECT external_resource.* FROM support JOIN external_resource ON external_resource.id = support.external_resource_id WHERE support.course_id = ?");
            
            iTeacher = connection.prepareStatement("INSERT INTO teacher(name, lastname, language, type, email, password) VALUES(?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            iCourse = connection.prepareStatement("INSERT INTO course(code, name, academic_year) VALUES(?,?,?)", Statement.RETURN_GENERATED_KEYS);
            iCourseTeacher = connection.prepareStatement("INSERT INTO teach(course_id, teacher_id) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
            iImage = connection.prepareStatement("INSERT INTO image(original_name, name_on_disk, path, course_id) VALUE(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            iModule = connection.prepareStatement("INSERT INTO module(course_id, module_course_id) VALUE(?,?)");
            iSame_as = connection.prepareStatement("INSERT INTO same_as(course_id, same_as_course_id) VALUE(?,?)");
            iPreparatory = connection.prepareStatement("INSERT INTO preparatory(course_id, requires) VALUE(?,?)");
            iExternalResource = connection.prepareStatement("INSERT INTO external_resource(name, description, weblink) VALUE(?,?,?)", Statement.RETURN_GENERATED_KEYS);
            iSupport = connection.prepareStatement("INSERT INTO support(course_id, external_resource_id) VALUE(?,?)");
            iUses = connection.prepareStatement("INSERT INTO uses(course_id, book_id) VALUE(?,?)");
            iTextbook = connection.prepareStatement("INSERT INTO book(author, title, volume, year, publisher, weblink) VALUE(?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                   
            dTeacher = connection.prepareStatement("DELETE FROM teacher WHERE id=?");
            dCourse = connection.prepareStatement("DELETE FROM course WHERE code=?");
            dCourseTeacher = connection.prepareStatement("DELETE FROM teach WHERE course_id=?");
            dTeach = connection.prepareStatement("DELETE FROM teach WHERE course_id = ? and teacher_id = ?");
            dImage = connection.prepareStatement("DELETE FROM image WHERE id = ?");
            dModule = connection.prepareStatement("DELETE FROM module WHERE course_id = ? OR module_course_id = ?");
            dPreparatory = connection.prepareStatement("DELETE FROM preparatory WHERE course_id = ? OR requires = ?");
            dSame_as = connection.prepareStatement("DELETE FROM same_as WHERE course_id = ? OR same_as_course_id = ?");
            dExternalResource = connection.prepareStatement("DELETE FROM external_resource WHERE id=?");
            dTextbook = connection.prepareStatement("DELETE FROM book WHERE id=?");
            dUses = connection.prepareStatement("DELETE FROM uses WHERE course_id=? AND book_id =?");
            dSupport = connection.prepareStatement("DELETE FROM support WHERE course_id=? AND external_resource_id =?");
            
            uTeacher = connection.prepareStatement("UPDATE teacher SET name=?,lastname=?,language=?,email=?, photo=?, type=? WHERE id=?");
            uTeacher_withPassword = connection.prepareStatement("UPDATE teacher SET name=?,lastname=?,language=?,email=?, password=?,photo=?,type=? WHERE id=?");
            uCourseBasicInfo = connection.prepareStatement("UPDATE course SET ssd=?,language=?,semester=? WHERE id=?");
            uCourseDescription = connection.prepareStatement("UPDATE course SET prerequisites=?,learning_outcomes=?,assessment_method=?,teaching_method=?,notes=?,prerequisites_ita=?,learning_outcomes_ita=?,assessment_method_ita=?,teaching_method_ita=?,notes_ita=?,syllabus=?,syllabus_ita=?,homepage=?,forum=? WHERE id=?");

            //iArticle = connection.prepareStatement("INSERT INTO article (title,text,authorID,issueID,page) VALUES(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

        } catch (SQLException ex) {
            throw new DataLayerException("Error initializing the data layer", ex);
        }
    }
    
    public boolean existTeacherByEmail(String email) {
        try {
            sTeacherByEmail.setString(1, email);
            if(!sTeacherByEmail.executeQuery().next())
                return false;
        }catch (SQLException ex) {
            Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    public boolean insertTeacher(Teacher t) {
        try {
            iTeacher.setString(1, t.getName());
            iTeacher.setString(2, t.getLastname());
            iTeacher.setString(3, t.getLanguage());
            iTeacher.setString(4, t.getType());
            iTeacher.setString(5, t.getEmail());
            iTeacher.setString(6, t.getPassword());
            iTeacher.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        return true;
    }
    
    public boolean deleteTeacher(int id) {
        try {
            dTeacher.setInt(1, id);
            dTeacher.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        return true;
    }
    
    public Teacher createTeacher(ResultSet rs) {
        TeacherImpl r = new TeacherImpl(this);;
        try {
            r.setId(rs.getInt("id"));
            r.setEmail(rs.getString("email"));
            r.setLanguage(rs.getString("language"));
            r.setName(rs.getString("name"));
            r.setLastname(rs.getString("lastname"));
            r.setType(rs.getString("type"));
            r.setPhoto(this.getImage(rs.getInt("photo")));
        } catch (SQLException ex) {
            Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }

    public Teacher getTeacher(String email, String password) {
        try {
            sTeacherByEmailPassword.setString(1, email);
            sTeacherByEmailPassword.setString(2, password);
            try (ResultSet rs = sTeacherByEmailPassword.executeQuery()) {
                if (rs.next()) {
                    return createTeacher(rs);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
    
    public Teacher getTeacher(String email) {
        try {
            sTeacherByEmail.setString(1, email);
            try (ResultSet rs = sTeacherByEmail.executeQuery()) {
                if (rs.next()) {
                    return createTeacher(rs);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
    
    public Teacher getTeacher(int teacherid) {
        try {
            sTeacherByID.setInt(1, teacherid);
            try (ResultSet rs = sTeacherByID.executeQuery()) {
                if (rs.next()) {
                    return createTeacher(rs);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
    
    public List<Teacher> getTeachers() {
        List<Teacher> teachers = new LinkedList<>();
        try {
            try (ResultSet rs = sTeachers.executeQuery()) {
                while (rs.next()) {
                     teachers.add(getTeacher(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return teachers;
    }
    
    public List<Teacher> getTeachers(Course course) {
        List<Teacher> teachers = new LinkedList<>();
        try {
            sTeachersByCourse.setInt(1,course.getId());
            try (ResultSet rs = sTeachersByCourse.executeQuery()) {
                while (rs.next()) {
                     teachers.add(getTeacher(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return teachers;
    }
    
    public boolean updateTeacher(Teacher t) {
        try {
            if(t.getPassword().equals("")) {
                uTeacher.setString(1, t.getName());
                uTeacher.setString(2, t.getLastname());
                uTeacher.setString(3, t.getLanguage());
                uTeacher.setString(4, t.getEmail());
                if(t.getPhoto().getId() != 0)
                    uTeacher.setInt(5, t.getPhoto().getId());
                else
                    uTeacher.setNull(5, java.sql.Types.INTEGER);
                uTeacher.setString(6, t.getType());
                uTeacher.setInt(7, t.getId());
                uTeacher.executeUpdate();
            }
            else {
                uTeacher_withPassword.setString(1, t.getName());
                uTeacher_withPassword.setString(2, t.getLastname());
                uTeacher_withPassword.setString(3, t.getLanguage());
                uTeacher_withPassword.setString(4, t.getEmail());
                uTeacher_withPassword.setString(5, t.getPassword());
                if(t.getPhoto()!= null)
                    uTeacher_withPassword.setInt(6, t.getPhoto().getId());
                else
                    uTeacher_withPassword.setNull(6, java.sql.Types.INTEGER);
                uTeacher_withPassword.setString(7, t.getType());
                uTeacher_withPassword.setInt(8, t.getId());
                uTeacher_withPassword.executeUpdate();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        return true;
    }
    
    public boolean assignCourse(Course course, Teacher teacher) {
        try {
            dCourseTeacher.setInt(1, course.getId());
            iCourseTeacher.setInt(1, course.getId());
            iCourseTeacher.setInt(2, teacher.getId());
            dCourseTeacher.executeUpdate();
            iCourseTeacher.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    public Course createCourse(ResultSet rs) {
        CourseImpl r = new CourseImpl(this);;
        try {
            r.setId(rs.getInt("id"));
            r.setSemester(rs.getInt("semester"));
            r.setCode(rs.getString("code"));
            r.setAcademic_year(rs.getString("academic_year"));
            r.setName(rs.getString("name"));
            r.setForum(rs.getString("forum"));
            r.setLanguage(rs.getString("language"));
            r.setSSD(rs.getString("ssd"));
            r.setPrerequisites(rs.getString("prerequisites"));
            r.setLearning_outcomes(rs.getString("learning_outcomes"));
            r.setAssessment_method(rs.getString("assessment_method"));
            r.setTeaching_method(rs.getString("teaching_method"));
            r.setSyllabus(rs.getString("syllabus"));
            r.setHomepage(rs.getString("homepage"));
            r.setForum(rs.getString("forum"));
            r.setNotes(rs.getString("notes"));
            
            r.setPrerequisites_ita(rs.getString("prerequisites_ita"));
            r.setAssessment_method_ita(rs.getString("assessment_method_ita"));
            r.setTeaching_method_ita(rs.getString("teaching_method_ita"));
            r.setLearning_outcomes_ita(rs.getString("learning_outcomes_ita"));
            r.setNotes_ita(rs.getString("notes_ita"));
            r.setSyllabus_ita(rs.getString("syllabus_ita"));
            
        } catch (SQLException ex) {
            Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }
    
    public void deleteCourse(String code) {
        try {
            dCourse.setString(1, code);
            dCourse.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // According to both code and name
    public boolean existCourse(Course c) {
        try {
            sCourseByCodeAndName.setString(1, c.getCode());
            sCourseByCodeAndName.setString(2, c.getName());
            if(!sCourseByCodeAndName.executeQuery().next())
                return false;
        }catch (SQLException ex) {
            Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    public List<Course> getCourses(String code) {
        List<Course> courses = new LinkedList<>();
        try {
            sCoursesByCode.setString(1, code);
            try (ResultSet rs = sCoursesByCode.executeQuery()) {
                while (rs.next()) {
                    courses.add(createCourse(rs));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return courses;
    }
    
    public Course getCourse(int courseid) {
        try {
            sCourseByID.setInt(1, courseid);
            try (ResultSet rs = sCourseByID.executeQuery()) {
                if (rs.next()) {
                    return createCourse(rs);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
    
    public List<Course> getCoursesByTeacher(Teacher teacher) {
        List<Course> courses = new LinkedList<>();
        try {
            if(teacher == null ) { // you are an admin and you want all the courses
                try (ResultSet rs = sCourses.executeQuery()) {
                    while(rs.next()) {
                        courses.add(getCourse(rs.getInt("id")));
                    }
                }
                
                // Order courses alfabetically
                courses.sort(Comparator.comparing(Course::getName));
                
                // Assign teachers to courses and books
                for(Course c : courses) {
                    sTeacherByCourse.setInt(1, c.getId());
                    try (ResultSet rs = sTeacherByCourse.executeQuery()) {
                        List<Teacher> teachers = new LinkedList<>();
                        while (rs.next()) {
                            teachers.add(createTeacher(rs));
                        }
                        c.setTeachers(teachers);
                    }
                }
                
            }
            else {
                sCoursesOfTeacher.setInt(1, teacher.getId());
                try (ResultSet rs = sCoursesOfTeacher.executeQuery()) {
                    while (rs.next()) {
                        courses.add(getCourse(rs.getInt("id")));
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return courses;
    }
    
    public List<Course> getCoursesByFilters(String name, String language, String semester, String academic_year, String SSD) {
        List<Course> temp = this.getCourses();
        List<Course> courses = new LinkedList<>();
        
                // initially all courses are admitted. Then, filter out the ones that don't satisfy criteria
        for(Course cur : temp) {
            if(name != null && !name.equals("")) {
                // course admitted at first. Then, check if it's filtered out if both its names and teachers'names don't match name
                if(cur.getName().toLowerCase().contains(name)) {
                    courses.add(cur);
                    continue;
                }
                boolean admitted = false;
                for(Teacher t : cur.getTeachers())
                    if(t.getName().toLowerCase().contains(name) || t.getLastname().toLowerCase().contains(name)) {
                        admitted = true;
                        break;
                    }
                if(!admitted)
                    continue;
            }
            
            if(language != null && !language.equals("any")) {
                if(!cur.getLanguage().equals(language))
                    continue;
            }
            
            if(semester != null && !semester.equals("any")) {
                if(cur.getSemester() != Integer.parseInt(semester))
                    continue;
            }
            
            if(academic_year != null && !academic_year.equals("any")) {
                if(!cur.getAcademic_year().equals(academic_year))
                    continue;
            }
            
            if(SSD != null && !SSD.equals("any")) {
                if(!cur.getSSD().equals(SSD.toUpperCase()))
                    continue;
            }
            
            courses.add(cur);
        }
        
        return courses;
    }
    
    public boolean insertCourse(Course c) {
        try {
            iCourse.setString(1, c.getCode());
            iCourse.setString(2, c.getName());
            iCourse.setString(3, c.getAcademic_year());
            iCourse.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        return true;
    }
    
    public boolean updateCourseBaseInfo(Course c) {
        // uCourseBasicInfo = connection.prepareStatement("UPDATE course SET ssd=?,language=?,semester=? WHERE id=?");
        try {
            uCourseBasicInfo.setString(1, c.getSSD());
            uCourseBasicInfo.setString(2, c.getLanguage());
            uCourseBasicInfo.setInt(3, c.getSemester());
            uCourseBasicInfo.setInt(4, c.getId());
            uCourseBasicInfo.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        return true;
    }
    
    public boolean updateCourseDescription(Course c) {
        // uCourseDescription = connection.prepareStatement("UPDATE course SET requirements=?,learning_outcomes=?,assessment_method=?,teaching_method=?,notes=?,requirements_ita=?,learning_outcomes_ita=?,assessment_method_ita=?,teaching_method_ita=?,notes_ita=? WHERE id=?");
        try {
            uCourseDescription.setString(1, c.getPrerequisites());
            uCourseDescription.setString(2, c.getLearning_outcomes());
            uCourseDescription.setString(3, c.getAssessment_method());
            uCourseDescription.setString(4, c.getTeaching_method());
            uCourseDescription.setString(5, c.getNotes());
            uCourseDescription.setString(6, c.getPrerequisites_ita());
            uCourseDescription.setString(7, c.getLearning_outcomes_ita());
            uCourseDescription.setString(8, c.getAssessment_method_ita());
            uCourseDescription.setString(9, c.getTeaching_method_ita());
            uCourseDescription.setString(10, c.getNotes_ita());
            uCourseDescription.setString(11, c.getSyllabus());
            uCourseDescription.setString(12, c.getSyllabus_ita());
            uCourseDescription.setString(13, c.getHomepage());
            uCourseDescription.setString(14, c.getForum());
            uCourseDescription.setInt(15, c.getId());
            uCourseDescription.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        return true;
    }
    
    @Override
    public void destroy() {
        //also closing PreparedStamenents is a good practice...
        try {
            sTeacherByEmailPassword.close();
            sTeacherByEmail.close();
            sCoursesByCode.close();
            sCourses.close();

            dCourse.close();

            sTeachers.close();
            iCourseTeacher.close();
            dCourseTeacher.close();
            sTeacherByID.close();
            sCourseByID.close();
            iTeacher.close();
            dTeacher.close();
            iCourse.close();
            sCourseByCodeAndName.close();
            uTeacher_withPassword.close();
            uTeacher.close();
            sCoursesOfTeacher.close();
            uCourseDescription.close();
            uCourseBasicInfo.close();
            sTeacherByCourse.close();
            sTeachersByCourse.close();
            sBooksByCourse.close();
            sCoursesPreparatory.close();
            sCoursesSame_as.close();
            sCourseModule.close();
            dTeach.close();
            dImage.close();
            iImage.close();
            sImageByTeacher.close();
            sImage.close();
            sImagesByCourse.close();
        } catch (SQLException ex) {
            //
        }
        super.destroy();
    }

    public Book createBook(ResultSet rs) {
        BookImpl r = new BookImpl(this);;
        try {
            r.setId(rs.getInt("id"));
            r.setYear(rs.getInt("year"));
            r.setAuthor(rs.getString("author"));
            r.setPublisher(rs.getString("publisher"));
            r.setTitle(rs.getString("title"));
            r.setVolume(rs.getString("volume"));
            r.setWeblink(rs.getString("weblink"));
        } catch (SQLException ex) {
            Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }
    
    public ExternalResource createExternalResource(ResultSet rs) {
        ExternalResourceImpl r = new ExternalResourceImpl(this);;
        try {
            r.setId(rs.getInt("id"));
            r.setDescription(rs.getString("description"));
            r.setName(rs.getString("name"));
            r.setWeblink(rs.getString("weblink"));
        } catch (SQLException ex) {
            Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }

    @Override
    public List<Course> getSame_as(Course course) {
        List<Course> same_as = new LinkedList<>();
        try {
            sCoursesSame_as.setInt(1, course.getId());
            sCoursesSame_as.setInt(2, course.getId());
            try (ResultSet rs = sCoursesSame_as.executeQuery()) {
                while (rs.next()) {
                    same_as.add(createCourse(rs));
                }
            }
        }
        catch(SQLException ex) {
            Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return same_as;
    }

    @Override
    public List<Course> getPreparatory(Course course) {
        List<Course> courses = new LinkedList<>();
        try {
            sCoursesPreparatory.setInt(1, course.getId());
            try (ResultSet rs = sCoursesPreparatory.executeQuery()) {
                while (rs.next()) {
                    courses.add(createCourse(rs));
                }
            }
        }
        catch(SQLException ex) {
            Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return courses;
    }

    @Override
    public List<Course> getModule(Course course) {
        List<Course> module = new LinkedList<>();
        try {
            sCourseModule.setInt(1, course.getId());
            sCourseModule.setInt(2, course.getId());
            try (ResultSet rs = sCourseModule.executeQuery()) {
                while (rs.next()) {
                    module.add(createCourse(rs));
                }
            }
        }
        catch(SQLException ex) {
            Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return module;
    }

    @Override
    public boolean decouple_course(int course_id, int teacher_id) {
        try {
            dTeach.setInt(1, course_id);
            dTeach.setInt(2, teacher_id);
            dTeach.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    @Override
    public int insertImage(Image image) {
        int id = 0;
        try {
            iImage.setString(1, image.getOriginal_name());
            iImage.setString(2, image.getName_on_disk());
            iImage.setString(3, image.getPath());
            if(image.getCourse_id() != 0)
                iImage.setInt(4, image.getCourse_id());
            else
                iImage.setNull(4, java.sql.Types.INTEGER);
            iImage.executeUpdate();
            
            // get id
            ResultSet rs = iImage.getGeneratedKeys();
            if (rs.next()) {
                id = (int) rs.getLong(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return id;
    }
    
    private Image createImage(ResultSet rs) {
        ImageImpl r = new ImageImpl(this);
        try {
            r.setId(rs.getInt("id"));
            r.setOriginal_name(rs.getString("original_name"));
            r.setName_on_disk(rs.getString("name_on_disk"));
            r.setPath(rs.getString("path"));
            if(rs.getInt("course_id") != 0)
                r.setCourse_id(rs.getInt("course_id"));
        } catch (SQLException ex) {
            Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }

    @Override
    public Image getImageByTeacher(int teacher_id) {
        try {
            sImageByTeacher.setInt(1, teacher_id);
            try (ResultSet rs = sImageByTeacher.executeQuery()) {
                if (rs.next()) {
                    return createImage(rs);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public void deleteImage(int id) {
        try {
            dImage.setInt(1, id);
            dImage.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void deleteImageByTeacher(int teacher_id) {
        Teacher teacher = this.getTeacher(teacher_id);
        this.deleteImage(teacher.getPhoto().getId());
    }

    private Image getImage(int id) {
        try{
            sImage.setInt(1,id);
            try (ResultSet rs = sImage.executeQuery();) {
                if (rs.next()) {
                    return createImage(rs);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Image> getImagesByCourse(int courseid) {
        List<Image> images = new LinkedList<>();
        try {
            sImagesByCourse.setInt(1, courseid);
            try (ResultSet rs = sImagesByCourse.executeQuery()) {
                while (rs.next()) {
                    images.add(createImage(rs));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return images;
    }

    @Override
    public Course getCourseByCodeAndAcademic_year(String code, String academic_year) {
        try {
            sCourseByCodeAndAcademic_year.setString(1, code);
            sCourseByCodeAndAcademic_year.setString(2, academic_year);
            try (ResultSet rs = sCourseByCodeAndAcademic_year.executeQuery()) {
                if (rs.next()) {
                    return createCourse(rs);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public List<Course> getCourses() {
        return this.getCoursesByTeacher(null);
    }

    @Override
    public void deletePreparatory(Course t) {
        try {
            dPreparatory.setInt(1, t.getId());
            dPreparatory.setInt(2, t.getId());
            dPreparatory.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deleteSame_as(Course t) {
        try {
            dSame_as.setInt(1, t.getId());
            dSame_as.setInt(2, t.getId());
            dSame_as.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deleteModule(Course t) {
        try {
            dModule.setInt(1, t.getId());
            dModule.setInt(2, t.getId());
            dModule.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void insertModule(int courseid, int other) {
        try {
            iModule.setInt(1, courseid);
            iModule.setInt(2, other);
            iModule.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void insertSame_as(int courseid, int other) {
        try {
            iSame_as.setInt(1, courseid);
            iSame_as.setInt(2, other);
            iSame_as.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void insertPreparatory(int courseid, int other) {
        try {
            iPreparatory.setInt(1, courseid);
            iPreparatory.setInt(2, other);
            iPreparatory.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean insertTextbook(int courseid, String author, String title, String volume, String publisher, String weblink, int year) {
        boolean res = false;
        try {
            iTextbook.setString(1, author);
            iTextbook.setString(2, title);
            iTextbook.setString(3, volume);
            iTextbook.setInt(4, year);
            iTextbook.setString(5, publisher);
            iTextbook.setString(6, weblink);
            iTextbook.executeUpdate();
            
            // get id
            ResultSet rs = iTextbook.getGeneratedKeys();
            int bookid = 0;
            if (rs.next()) {
                bookid = (int) rs.getLong(1);
            }
            
            iUses.setInt(1, courseid);
            iUses.setInt(2, bookid);
            iUses.executeUpdate();
            res = true;
        } catch (SQLException ex) {
            Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    @Override
    public boolean insertExternalResource(int courseid, String name, String description, String weblink) {
        boolean res = false;
        try {
            iExternalResource.setString(1, name);
            iExternalResource.setString(2, description);
            iExternalResource.setString(3, weblink);
            iExternalResource.executeUpdate();
            
            // get id
            ResultSet rs = iExternalResource.getGeneratedKeys();
            int erid = 0;
            if (rs.next()) {
                erid = (int) rs.getLong(1);
            }
            
            iSupport.setInt(1, courseid);
            iSupport.setInt(2, erid);
            iSupport.executeUpdate();
            res = true;
        } catch (SQLException ex) {
            Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    @Override
    public List<Book> getTextbooks(int courseid) {
        List<Book> books = new LinkedList<>();
        try {
            sBooksByCourse.setInt(1,courseid);
            try (ResultSet rs = sBooksByCourse.executeQuery()) {
                while (rs.next()) {
                     books.add(createBook(rs));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return books;
    }

    @Override
    public List<ExternalResource> getExternalResources(int courseid) {
        List<ExternalResource> ers = new LinkedList<>();
        try {
            sExternalResourcesByCourse.setInt(1,courseid);
            try (ResultSet rs = sExternalResourcesByCourse.executeQuery()) {
                while (rs.next()) {
                     ers.add(createExternalResource(rs));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ers;
    }

    @Override
    public boolean deleteTextbook(int courseid, int id) {
        boolean res = false;
        try {
            dUses.setInt(1,courseid);
            dUses.setInt(2,id);
            dUses.executeUpdate();
            
            dTextbook.setInt(1, id);
            dTextbook.executeUpdate();
            res = true;
        } catch (SQLException ex) {
            Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    @Override
    public boolean deleteExternalResource(int courseid, int id) {
        boolean res = false;
        try {
            dSupport.setInt(1,courseid);
            dSupport.setInt(2,id);
            dSupport.executeUpdate();
            
            dExternalResource.setInt(1, id);
            dExternalResource.executeUpdate();
            res = true;
        } catch (SQLException ex) {
            Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    @Override
    public Course getCloserCourseProperty(String code, String property) {
        List<Course> courses = new LinkedList<>();
        try {
            switch(property) {
                case "notes_ita":
                case "name":
                case "SSD":
                case "language": 
                case "semester": 
                case "academic_year": 
                case "prerequisites":
                case "learning_outcomes":
                case "learning_outcomes_ita":
                case "prerequisites_ita":
                case "assessment_method_ita":
                case "teaching_method_ita":
                case "assessment_method":
                case "teaching_method":
                case "syllabus":
                case "syllabus_ita":
                case "homepage":
                case "forum":
                case "notes": 
                    break;
                default:
                    throw new SQLException("Somebody tried a strange property in getCloserCourseProperty()");
            }
            sCloserCourseProperty = connection.prepareStatement("select * from course where code = ? and " + property + " != '' order by academic_year desc");
            sCloserCourseProperty.setString(1, code);
            try (ResultSet rs = sCloserCourseProperty.executeQuery()) {
                while (rs.next()) {
                    courses.add(createCourse(rs));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        if(courses.isEmpty())
            return null;
        return courses.get(0);
    }

    @Override
    public List<ExternalResource> getCloserExternal_resources(String code) {
        List<Course> courses = this.getCourses(code);
        // Sort by academic year
        Collections.sort(courses, (Course p1, Course p2) -> p2.getAcademic_year().compareTo(p1.getAcademic_year()));
        
        for(Course c : courses) {
            List<ExternalResource> t = this.getExternalResources(c.getId());
            if(t.isEmpty() == false)
                return t;
        }
        return new LinkedList<>();
    }

    @Override
    public List<Book> getCloserTextbooks(String code) {
        List<Course> courses = this.getCourses(code);
        // Sort by academic year
        Collections.sort(courses, (Course p1, Course p2) -> p2.getAcademic_year().compareTo(p1.getAcademic_year()));
        
        for(Course c : courses) {
            List<Book> t = this.getTextbooks(c.getId());
            if(t.isEmpty() == false)
                return t;
        }
        return new LinkedList<>();
    }

    @Override
    public List<Course> getCloserModule(String code) {
        List<Course> courses = this.getCourses(code);
        // Sort by academic year
        Collections.sort(courses, (Course p1, Course p2) -> p2.getAcademic_year().compareTo(p1.getAcademic_year()));
        
        for(Course c : courses) {
            List<Course> t = this.getModule(c);
            if(t.isEmpty() == false)
                return t;
        }
        return new LinkedList<>();
    }

    @Override
    public List<Course> getCloserPreparatory(String code) {
        List<Course> courses = this.getCourses(code);
        // Sort by academic year
        Collections.sort(courses, (Course p1, Course p2) -> p2.getAcademic_year().compareTo(p1.getAcademic_year()));
        
        for(Course c : courses) {
            List<Course> t = this.getPreparatory(c);
            if(t.isEmpty() == false)
                return t;
        }
        return new LinkedList<>();
    }

    @Override
    public List<Course> getCloserSame_as(String code) {
        List<Course> courses = this.getCourses(code);
        // Sort by academic year
        Collections.sort(courses, (Course p1, Course p2) -> p2.getAcademic_year().compareTo(p1.getAcademic_year()));
        
        for(Course c : courses) {
            List<Course> t = this.getSame_as(c);
            if(t.isEmpty() == false)
                return t;
        }
        return new LinkedList<>();
    }

    @Override
    public List<Image> getCloserImages(String code) {
        List<Course> courses = this.getCourses(code);
        // Sort by academic year
        Collections.sort(courses, (Course p1, Course p2) -> p2.getAcademic_year().compareTo(p1.getAcademic_year()));
        
        for(Course c : courses) {
            List<Image> t = this.getImagesByCourse(c.getId());
            if(t.isEmpty() == false)
                return t;
        }
        return new LinkedList<>();
    }
}
