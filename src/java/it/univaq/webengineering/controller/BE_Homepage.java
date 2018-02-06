package it.univaq.webengineering.controller;

import it.univaq.webengineering.data.model.Course;
import it.univaq.webengineering.data.model.Teacher;
import it.univaq.webengineering.data.model.WebengineeringDataLayer;
import it.univaq.webengineering.framework.data.DataLayerException;
import it.univaq.webengineering.framework.result.FailureResult;
import it.univaq.webengineering.framework.result.TemplateResult;
import it.univaq.webengineering.framework.result.SplitSlashesFmkExt;
import it.univaq.webengineering.framework.result.TemplateManagerException;
import it.univaq.webengineering.framework.security.SecurityLayer;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
                request.setAttribute("courses", ((WebengineeringDataLayer)request.getAttribute("datalayer")).getCourses(null));
                request.setAttribute("teachers", ((WebengineeringDataLayer)request.getAttribute("datalayer")).getTeachers());
                request.setAttribute("switchlang", switchlang);
                
                if(request.getParameter("lang") != null && request.getParameter("lang").equals("ITA")) {
                    url = "backend/homepage_ita.ftl.html";
                    switchlang = "ENG";
                }
            }
            else {
                url = "backend/homepage_teacher.ftl.html";
                request.setAttribute("courses", ((WebengineeringDataLayer)request.getAttribute("datalayer")).getCourses(user));
                
                if(request.getParameter("lang") != null && request.getParameter("lang").equals("ITA")) {
                    url = "backend/homepage_teacher_ita.ftl.html";
                    switchlang = "ENG";
                }
            }
            res.activate(url, request, response);
        }
    }
    
    private void action_deletecourse(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = SecurityLayer.checkSession(request);
        if(session != null) {
            String coursecode = request.getParameter("coursecode");
            String email = (String)session.getAttribute("username");
            Teacher t = ((WebengineeringDataLayer)request.getAttribute("datalayer")).getTeacher(email);
            if(t.getType() != null && t.getType().equals("admin") && coursecode != null) {
                ((WebengineeringDataLayer)request.getAttribute("datalayer")).deleteCourse(coursecode);
            }
            else {
                
            }
        }
    }
    
    private void action_assigncourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = SecurityLayer.checkSession(request);
        if(session != null) {
            String coursecode = request.getParameter("coursecode");
            String teacherid = request.getParameter("teacherid");
            if(!SecurityLayer.isNumberic(teacherid)) {
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("Teacher id must be a number (-1 if none)");
            }
            
            // Get course and teacher
            Teacher t = teacherid.equals("-1") ? null : ((WebengineeringDataLayer)request.getAttribute("datalayer")).getTeacher(Integer.parseInt(teacherid));
            Course  c = ((WebengineeringDataLayer)request.getAttribute("datalayer")).getCourse(coursecode);
            boolean res = ((WebengineeringDataLayer)request.getAttribute("datalayer")).assignCourse(c, t);
            String text = "";
            if(res)
                text = "ok";
            else
                text = "Error while assigning course.";
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(text);
            return;
        }
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("You must be authorized and logged in for this.");
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
