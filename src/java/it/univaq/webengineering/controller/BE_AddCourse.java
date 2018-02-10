package it.univaq.webengineering.controller;

import it.univaq.webengineering.data.impl.CourseImpl;
import it.univaq.webengineering.data.impl.TeacherImpl;
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


public class BE_AddCourse extends WebengineeringBaseController {

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        // Needed to support double language (ita/eng)
        String url = "backend/addcourse.ftl.html";
        String switchlang = "ITA";
        List<Teacher> teachers = ((WebengineeringDataLayer)request.getAttribute("datalayer")).getTeachers();
        
        if(SecurityLayer.checkSession(request) == null) {
            request.setAttribute("message", "You must be logged in and be an admin!");
            action_error(request, response);
            return;
        }
        
        TemplateResult res = new TemplateResult(getServletContext());
        String email = (String)SecurityLayer.checkSession(request).getAttribute("username");
        Teacher user = ((WebengineeringDataLayer)request.getAttribute("datalayer")).getTeacher(email);
        if(request.getParameter("lang") != null && request.getParameter("lang").equals("ITA")) {
            url = "backend/addcourse_ita.ftl.html";
            switchlang = "ENG";
        }
        else { 
            // if user hasn't forced the language of the page, check his language and use it
            if(user.getLanguage().toLowerCase().equals("ita")) {
                url = "backend/addcourse_ita.ftl.html";
                switchlang = "ENG"; 
            }
        }
        request.setAttribute("teachers", teachers);
        request.setAttribute("switchlang", switchlang);
        request.setAttribute("isAdmin", user.isAdmin());
        res.activate(url, request, response);
    }
    
    private void action_addcourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = SecurityLayer.checkSession(request);
        if(session != null) {
            String name = request.getParameter("name");
            String code = request.getParameter("code");
            int teacherid = Integer.parseInt(request.getParameter("teacherid"));
            
            Course t = new CourseImpl(null);
            t.setName(name);
            t.setCode(code);
            t.setAcademic_year(super.getCurrentAcademicYear());
            
            String text = "Error while creating new course.";
            if(((WebengineeringDataLayer)request.getAttribute("datalayer")).existCourse(t) == false) {
                boolean res = ((WebengineeringDataLayer)request.getAttribute("datalayer")).insertCourse(t);
                t = ((WebengineeringDataLayer)request.getAttribute("datalayer")).getCourse(code);
                
                // Assign course to teacher
                if(teacherid != -1) {
                    Teacher teacher = ((WebengineeringDataLayer)request.getAttribute("datalayer")).getTeacher(teacherid);
                    res = ((WebengineeringDataLayer)request.getAttribute("datalayer")).assignCourse(t, teacher);
                }
                if(res) {
                    text = "ok";
                }
            }
            else {
                text += " Course with that code and name already exists. Change one of them.";
            }
                
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(text);
            return;
        }
        request.setAttribute("message", "You must be logged and be administrator!");
        action_error(request, response);
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        request.setAttribute("context", "be_addcourse");
        request.setAttribute("page_title", "Add new course");

        try {
            if(request.getParameter("action") != null && request.getParameter("action").equals("addcourse")) {
                action_addcourse(request, response);
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
