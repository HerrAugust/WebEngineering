package it.univaq.webengineering.controller;

import it.univaq.webengineering.data.model.Course;
import it.univaq.webengineering.data.model.Image;
import it.univaq.webengineering.data.model.Teacher;
import it.univaq.webengineering.data.model.WebengineeringDataLayer;
import it.univaq.webengineering.framework.data.DataLayerException;
import it.univaq.webengineering.framework.result.FailureResult;
import it.univaq.webengineering.framework.result.TemplateResult;
import it.univaq.webengineering.framework.result.SplitSlashesFmkExt;
import it.univaq.webengineering.framework.result.TemplateManagerException;
import it.univaq.webengineering.framework.security.SecurityLayer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FE_Courses extends WebengineeringBaseController {

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }
    
    private void action_listCourses(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        // Needed to support double language (ita/eng)
        String url = "frontend/listcourses.ftl.html";
        String switchlang = "ITA";
        String title = "Courses";
        List<Course> courseslist = null;
        
        List<String> academic_years = new ArrayList<>();
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        for(int i = 1; i < 5; i++) {
            academic_years.add((year-1) + "/" + year);
            year--;
        }
       
        TemplateResult res = new TemplateResult(getServletContext());
        if(request.getParameter("lang") != null && request.getParameter("lang").equals("ITA")) {
            url = "frontend/listcourses_ita.ftl.html";
            switchlang = "ENG";
            title = "Corsi";
        }
        request.setAttribute("page_title", title);
        request.setAttribute("academic_years", academic_years);
        request.setAttribute("courses", title);
        // Check if user wants to filter courses
        if(request.getParameter("action") != null && request.getParameter("action").equals("filter")) {
            String name = request.getParameter("name");
            String language = request.getParameter("language");
            String semester = request.getParameter("semester");
            String academic_year = request.getParameter("academic_year");
            String SSD = request.getParameter("SSD");
            courseslist = ((WebengineeringDataLayer)request.getAttribute("datalayer")).getCoursesByFilters(name, language, semester, academic_year, SSD);
        }else { // user requires all courses, no filter
            courseslist = ((WebengineeringDataLayer)request.getAttribute("datalayer")).getCourses();
        }
        request.setAttribute("courseslist", courseslist);
        request.setAttribute("switchlang", switchlang);
        res.activate(url, request, response);
    }
    
    private void action_details_teacher(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        // Needed to support double language (ita/eng)
        String url = "frontend/professor_details.ftl.html";
        String switchlang = "ITA";
        String title = "Professor's details";
        int id = Integer.parseInt(request.getParameter("id"));
        
        TemplateResult res = new TemplateResult(getServletContext());
        //add to the template a wrapper object that allows to call the stripslashes function
        //request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
        if(request.getParameter("lang") != null && request.getParameter("lang").equals("ITA")) {
            url = "frontend/professor_details_ita.ftl.html";
            switchlang = "ENG";
            title = "Dettagli del professore";
        }
        Teacher teacher = ((WebengineeringDataLayer)request.getAttribute("datalayer")).getTeacher(id);
        Image image = ((WebengineeringDataLayer)request.getAttribute("datalayer")).getImageByTeacher(id);
        if(image != null)
            request.setAttribute("imagesrc", "uploads/" + image.getName_on_disk());
        request.setAttribute("page_title", title);
        request.setAttribute("teacher", teacher);
        request.setAttribute("switchlang", switchlang);
        request.setAttribute("context", "fe_courses?action=details_teacher&id="+id);
        res.activate(url, request, response);
    }
    
    private void action_details_course(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        // Needed to support double language (ita/eng)
        String url = "frontend/course_details.ftl.html";
        String switchlang = "ITA";
        String title = "Course details";
        String coursecode = request.getParameter("coursecode");
        String academic_year = request.getParameter("academic_year");
        
        TemplateResult res = new TemplateResult(getServletContext());
        //add to the template a wrapper object that allows to call the stripslashes function
        //request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
        if(request.getParameter("lang") != null && request.getParameter("lang").equals("ITA")) {
            url = "frontend/course_details_ita.ftl.html";
            switchlang = "ENG";
            title = "Dettagli del corso";
        }
        List<Course> courses = ((WebengineeringDataLayer)request.getAttribute("datalayer")).getCourses(coursecode);
        List<String> academic_years = new LinkedList<>();
        Course course = courses.get(0);
        for(Course cur : courses) {
            if(cur.getAcademic_year().equals(academic_year))
                course = cur;
            academic_years.add(cur.getAcademic_year());
        }
        course.setTeachers(((WebengineeringDataLayer)request.getAttribute("datalayer")).getTeachers(course));
        course.setBooks(((WebengineeringDataLayer)request.getAttribute("datalayer")).getTextbooks(course.getId()));
        course.setExternal_resources(((WebengineeringDataLayer)request.getAttribute("datalayer")).getExternalResources(course.getId()));
        course.setModule(((WebengineeringDataLayer)request.getAttribute("datalayer")).getModule(course));
        course.setSame_as(((WebengineeringDataLayer)request.getAttribute("datalayer")).getSame_as(course));
        course.setPreparatory(((WebengineeringDataLayer)request.getAttribute("datalayer")).getPreparatory(course));
        
        List<Image> imagessrc = ((WebengineeringDataLayer)request.getAttribute("datalayer")).getImagesByCourse(course.getId());
        
        request.setAttribute("imagessrc", imagessrc);
        request.setAttribute("page_title", title);
        request.setAttribute("course", course);
        request.setAttribute("switchlang", switchlang);
        request.setAttribute("academic_years", academic_years);
        request.setAttribute("current_academic_year", academic_year);
        request.setAttribute("context", "fe_courses?action=details_course&coursecode="+coursecode);
        res.activate(url, request, response);
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        try {
            if(request.getParameter("action") != null && request.getParameter("action").equals("details_course")) {
                action_details_course(request, response);
                return;
            }
            if(request.getParameter("action") != null && request.getParameter("action").equals("details_teacher")) {
                action_details_teacher(request, response);
                return;
            }
            action_listCourses(request, response);
        } catch (NumberFormatException ex) {
            request.setAttribute("message", "Invalid number submitted");
            action_error(request, response);
        } catch (IOException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        } catch (TemplateManagerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Courses Frontend servlet";
    }// </editor-fold>

}
