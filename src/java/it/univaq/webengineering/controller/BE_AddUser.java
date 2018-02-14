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

import java.util.logging.Level;
import java.util.logging.Logger;
import it.univaq.webengineering.data.impl.WebengineeringDataLayerMysqlImpl;

public class BE_AddUser extends WebengineeringBaseController {

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
        String url = "backend/adduser.ftl.html";
        String switchlang = "ITA";
        
        if(SecurityLayer.checkSession(request) == null) {
            Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.INFO, SecurityLayer.getUser(request) + ": not logged in.");
            request.setAttribute("message", "You must be logged in and be an admin!");
            action_error(request, response);
            return;
        }
        
        TemplateResult res = new TemplateResult(getServletContext());
        String email = (String)SecurityLayer.checkSession(request).getAttribute("username");
        Teacher user = ((WebengineeringDataLayer)request.getAttribute("datalayer")).getTeacher(email);
        if(request.getParameter("lang") != null && request.getParameter("lang").equals("ITA")) {
            url = "backend/adduser_ita.ftl.html";
            switchlang = "ENG";
        }
        else { 
            // if user hasn't forced the language of the page, check his language and use it
            if(user.getLanguage().toLowerCase().equals("ita")) {
                url = "backend/adduser_ita.ftl.html";
                switchlang = "ENG"; 
            }
        }
        request.setAttribute("switchlang", switchlang);
        request.setAttribute("isAdmin", user.isAdmin());
        res.activate(url, request, response);
    }
    
    private void action_adduser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.INFO, String.format("%s: %s.%s",SecurityLayer.getUser(request), Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName()));
        
        HttpSession session = SecurityLayer.checkSession(request);
        if(session != null) {
            String name = request.getParameter("name");
            String lastname = request.getParameter("lastname");
            String language = request.getParameter("language");
            String password = request.getParameter("password");
            String password2 = request.getParameter("password2");
            String email = request.getParameter("email");
            String type = request.getParameter("type") != null ? "admin" : "teacher";
                        
            if(password.equals(password2) == false) {
                request.setAttribute("message", "Password missmatch");
                response.sendRedirect("be_adduser");
                return;
            }
            
            Teacher t = new TeacherImpl(null);
            t.setName(name);
            t.setLastname(lastname);
            t.setLanguage(language);
            t.setEmail(email);
            t.setPassword(password);
            t.setType(type);
            
            String text = "";
            boolean res = ((WebengineeringDataLayer)request.getAttribute("datalayer")).insertTeacher(t);
            if(res) {
                Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.INFO, String.format("%s: added teacher %s %s", SecurityLayer.getUser(request), t.getName(), t.getLastname()));
                response.sendRedirect("be_listusers");
            }
            else {
                if(((WebengineeringDataLayer)request.getAttribute("datalayer")).existTeacherByEmail(t.getEmail())) {
                    text += " Teacher with that email already exists";
                    Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.INFO, SecurityLayer.getUser(request) + ": teacher already exists");
                }
            }
            
            request.setAttribute("message", text);
            response.sendRedirect("be_adduser");
            return;
        }
        Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.INFO, SecurityLayer.getUser(request) + ": not logged in.");
        request.setAttribute("message", "You must be logged and be administrator!");
        action_error(request, response);
        return;
    }
    
    private void action_edituser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.INFO, String.format("%s: %s.%s",SecurityLayer.getUser(request), Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName()));
        
        HttpSession session = SecurityLayer.checkSession(request);
        if(session != null) {
            String name = request.getParameter("name");
            String lastname = request.getParameter("lastname");
            String language = request.getParameter("language");
            String password = request.getParameter("password");
            String password2 = request.getParameter("password2");
            String email = request.getParameter("email");
            String type = request.getParameter("type") != null ? "admin" : "teacher";
            
            int id = Integer.parseInt(request.getParameter("id"));
            
            if(password.equals(password2) == false) {
                request.setAttribute("message", "Password missmatch");
                response.sendRedirect("be_adduser");
                return;
            }
            
            Teacher t = new TeacherImpl(null);
            t.setId(id);
            t.setName(name);
            t.setLastname(lastname);
            t.setLanguage(language);
            t.setEmail(email);
            t.setPassword(password);
            t.setType(type);
            
            String text = "";
            boolean res = ((WebengineeringDataLayer)request.getAttribute("datalayer")).updateTeacher(t);
            if(res) {
                Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.INFO, String.format("%s: added teacher %s %s", SecurityLayer.getUser(request), t.getName(), t.getLastname()));
                response.sendRedirect("be_listusers");
            }
            else {
                if(((WebengineeringDataLayer)request.getAttribute("datalayer")).existTeacherByEmail(t.getEmail())) {
                    text += " Teacher with that email already exists";
                    Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.INFO, SecurityLayer.getUser(request) + ": teacher already exists");
                }
            }
            
            request.setAttribute("message", text);
            response.sendRedirect("be_adduser");
            return;
        }
        Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.INFO, SecurityLayer.getUser(request) + ": not logged in.");
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
            if(request.getParameter("action") != null && request.getParameter("action").equals("edituser")) {
                action_edituser(request, response);
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
