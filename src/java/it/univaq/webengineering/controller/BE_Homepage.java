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
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.logging.Level;
import java.util.logging.Logger;
import it.univaq.webengineering.data.impl.WebengineeringDataLayerMysqlImpl;

/**
 *
 * @author Giuseppe
 */
public class BE_Homepage extends WebengineeringBaseController {

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.INFO, String.format("%s: %s.%s",SecurityLayer.getUser(request), Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName()));
        
        // Needed to support double language (ita/eng)
        String url = "backend/homepage.ftl.html";
        String switchlang = "ITA";
        
        HttpSession session = SecurityLayer.checkSession(request);
        if(session != null) {
            TemplateResult res = new TemplateResult(getServletContext());
            
            //If user is teacher, forward to his hoomepage
            String email = (String)session.getAttribute("username");
            Teacher user = ((WebengineeringDataLayer)request.getAttribute("datalayer")).getTeacher(email);
            if(user.isAdmin()) {
                List<Course> courses = ((WebengineeringDataLayer)request.getAttribute("datalayer")).getCourses();
                request.setAttribute("courses", courses);
                request.setAttribute("teachers", ((WebengineeringDataLayer)request.getAttribute("datalayer")).getTeachers());
                request.setAttribute("switchlang", switchlang);
                
                if(user.isItalian() || (request.getParameter("lang") != null && request.getParameter("lang").equals("ITA"))) {
                    url = "backend/homepage_ita.ftl.html";
                    switchlang = "ENG";
                }
            }
            else {
                url = "backend/homepage_teacher.ftl.html";
                request.setAttribute("courses", ((WebengineeringDataLayer)request.getAttribute("datalayer")).getCoursesByTeacher(user));
                
                if(user.isItalian() || (request.getParameter("lang") != null && request.getParameter("lang").equals("ITA"))) {
                    url = "backend/homepage_teacher_ita.ftl.html";
                    switchlang = "ENG";
                }
            }
            request.setAttribute("current_academic_year", super.getCurrentAcademicYear());
            request.setAttribute("isAdmin", user.isAdmin());
            if(request.getParameter("message") != null) request.setAttribute("message", request.getParameter("message"));
            res.activate(url, request, response);
        }
        else { // user not logged in
            Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.INFO, SecurityLayer.getUser(request) + ": not logged in.");
            response.sendRedirect("fe_login");
        }
    }
    
    private void action_deletecourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = SecurityLayer.checkSession(request);
        if(session != null) {
            WebengineeringDataLayer datalayer = (WebengineeringDataLayer)request.getAttribute("datalayer");
            String coursecode = request.getParameter("coursecode");
            String email = (String)session.getAttribute("username");
            Teacher t = datalayer.getTeacher(email);
            if(t.getType() != null && t.getType().equals("admin") && coursecode != null) {
                Course course = datalayer.getCourseByCodeAndAcademic_year(coursecode, super.getCurrentAcademicYear());
                // delete images
                List<Image> imagesByCourse = datalayer.getImagesByCourse(course.getId());
                for(Image image : imagesByCourse) {
                    new File(image.getPath() + image.getName_on_disk()).delete();
                    datalayer.deleteImage(image.getId());
                }
                // delete teached courses (not teachers of course, only connection)
                for(Teacher teacher : datalayer.getTeachers(course))
                    datalayer.decouple_course(course.getId(), teacher.getId());

                ((WebengineeringDataLayer)request.getAttribute("datalayer")).deleteCourse(coursecode);
                Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.INFO, String.format("%s: deleted course %s", SecurityLayer.getUser(request), coursecode));
                String text = "Course deleted";
                if(t.isItalian())
                    text = "Corso eliminato";
                response.sendRedirect("be_listcourses?message="+text);
                return;
            }
        }
        Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.INFO, SecurityLayer.getUser(request) + ": not logged in.");
    }
    
    private void action_assigncourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = SecurityLayer.checkSession(request);
        if(session != null) {
            String coursecode = request.getParameter("coursecode");
            String academic_year = request.getParameter("academic_year");
            String teacherid = request.getParameter("teacherid");
            if(!SecurityLayer.isNumberic(teacherid)) {
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                String text = "Teacher's id must be a number (-1 if none)";
                if(SecurityLayer.getTeacher(request).isItalian())
                    text = "L\'ID del professore deve essere un numero (-1 se inesistente)";
                response.getWriter().write(text);
            }
            
            // Get course and teacher
            Teacher t = teacherid.equals("-1") ? null : ((WebengineeringDataLayer)request.getAttribute("datalayer")).getTeacher(Integer.parseInt(teacherid));
            Course  c = ((WebengineeringDataLayer)request.getAttribute("datalayer")).getCourseByCodeAndAcademic_year(coursecode, academic_year);
            boolean res = ((WebengineeringDataLayer)request.getAttribute("datalayer")).assignCourse(c, t);
            String text = "";
            if(res) {
                text = "ok";
                Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.INFO, String.format("%s: assign course %s to teacher %s", SecurityLayer.getUser(request), c.getName(), t.getName()));
            }
            else {
                text = "Error while assigning course.";
                if(SecurityLayer.getTeacher(request).isItalian())
                    text = "Errore durante l\'assegnamento del corso";
            }
            response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
            response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
            response.getWriter().write(text); 
            return;
        }
        Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.INFO, SecurityLayer.getUser(request) + ": not logged in.");
        response.sendRedirect("fe_login?message=You must be authorized and logged in for this.");
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        request.setAttribute("context", "be_homepage");
        request.setAttribute("page_title", "Homepage");

        try {
            if(request.getParameter("action") != null && request.getParameter("action").equals("deletecourse")) {
                action_deletecourse(request, response);
                return;
            }
            if(request.getParameter("action") != null && request.getParameter("action").equals("assigncourseteacher")) {
                action_assigncourse(request, response);
                return;
            }
            action_default(request, response);
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
        return "Homepage Backend servlet";
    }// </editor-fold>

}
