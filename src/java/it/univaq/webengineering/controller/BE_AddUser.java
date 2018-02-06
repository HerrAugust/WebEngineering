package it.univaq.webengineering.controller;

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

public class BE_AddUser extends WebengineeringBaseController {

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        // Needed to support double language (ita/eng)
        String url = "backend/adduser.ftl.html";
        String switchlang = "ITA";
        
        if(SecurityLayer.checkSession(request) == null) {
            request.setAttribute("message", "You must be logged in and be an admin!");
            action_error(request, response);
            return;
        }
        
        TemplateResult res = new TemplateResult(getServletContext());
        if(request.getParameter("lang") != null && request.getParameter("lang").equals("ITA")) {
            url = "backend/adduser_ita.ftl.html";
            switchlang = "ENG";
        }
        else { 
            // if user hasn't forced the language of the page, check his language and use it
            String email = (String)SecurityLayer.checkSession(request).getAttribute("username");
            if(((WebengineeringDataLayer)request.getAttribute("datalayer")).getTeacher(email).getLanguage().toLowerCase().equals("ita")) {
                url = "backend/adduser_ita.ftl.html";
                switchlang = "ENG"; 
            }
        }
        request.setAttribute("switchlang", switchlang);
        res.activate(url, request, response);
    }
    
    private void action_adduser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = SecurityLayer.checkSession(request);
        if(session != null) {
            String name = request.getParameter("name");
            String lastname = request.getParameter("lastname");
            String language = request.getParameter("language");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            String type = request.getParameter("type");
            
            Teacher t = new TeacherImpl(null);
            t.setName(name);
            t.setLastname(lastname);
            t.setLanguage(language);
            t.setEmail(email);
            t.setPassword(password);
            t.setType(type.equals("on") ? "admin" : "teacher");
            
            boolean res = ((WebengineeringDataLayer)request.getAttribute("datalayer")).insertTeacher(t);
            String text = "Error while creating new Teacher.";
            if(res)
                text = "ok";
            else {
                if(((WebengineeringDataLayer)request.getAttribute("datalayer")).existTeacherByEmail(t.getEmail()))
                    text += " Teacher with that email already exists";
            }
                
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(text);
            return;
        }
        request.setAttribute("message", "You must be logged and be administrator!");
        action_error(request, response);
        return;
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        request.setAttribute("context", "be_adduser");
        request.setAttribute("page_title", "Add new user");

        try {
            if(request.getParameter("action") != null && request.getParameter("action").equals("adduser")) {
                action_adduser(request, response);
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
