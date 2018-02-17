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

import java.util.logging.Level;
import java.util.logging.Logger;
import it.univaq.webengineering.data.impl.WebengineeringDataLayerMysqlImpl;
import java.io.File;

public class BE_ListUsers extends WebengineeringBaseController {

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
        String url = "backend/listusers.ftl.html";
        String switchlang = "ITA";
        
        HttpSession session = SecurityLayer.checkSession(request);
        if(session == null) {
            Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.INFO, SecurityLayer.getUser(request) + ": not logged in.");
            request.setAttribute("message", "Login necessary");
            this.action_error(request, response);
            return;
        }
        
        TemplateResult res = new TemplateResult(getServletContext());
        //add to the template a wrapper object that allows to call the stripslashes function
        //request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
        if(request.getParameter("lang") != null && request.getParameter("lang").equals("ITA")) {
            url = "backend/listusers_ita.ftl.html";
            switchlang = "ENG";
        }
        request.setAttribute("users", ((WebengineeringDataLayer)request.getAttribute("datalayer")).getTeachers());
        request.setAttribute("switchlang", switchlang);
        Teacher teacher = ((WebengineeringDataLayer)request.getAttribute("datalayer")).getTeacher((String)session.getAttribute("username"));
        request.setAttribute("isAdmin", teacher.isAdmin());
        if(request.getParameter("message") != null) request.setAttribute("message", request.getParameter("message"));
        res.activate(url, request, response);
    }
    
    private void action_deleteuser(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException, IOException, ServletException {
        Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.INFO, String.format("%s: %s.%s",SecurityLayer.getUser(request), Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName()));

        HttpSession session = SecurityLayer.checkSession(request);
        if(session != null) {
            int userid = Integer.parseInt(request.getParameter("userid"));
            Teacher teacher = ((WebengineeringDataLayer)request.getAttribute("datalayer")).getTeacher(userid);
            boolean res = ((WebengineeringDataLayer)request.getAttribute("datalayer")).deleteTeacher(userid);
            String text = "";
            
            // delete user's image
            if(teacher.getPhoto() != null) {
                ((WebengineeringDataLayer)request.getAttribute("datalayer")).deleteImage(teacher.getPhoto().getId());
                new File(teacher.getPhoto().getPath() + teacher.getPhoto().getName_on_disk()).delete();
            }
            
            if(res) {
                Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.INFO, String.format("%s: delete user %d", SecurityLayer.getUser(request), userid));
                text = "User " + teacher.getLastname() + " " + teacher.getName() + " deleted";
            }
            else
                text = "Error deleting user " + teacher.getLastname() + " " + teacher.getName();
            response.sendRedirect("be_listusers?message="+text);
            return;
        }
        Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.INFO, SecurityLayer.getUser(request) + ": not logged in.");
    }
    
    private void action_edituser(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException, IOException {
        Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.INFO, String.format("%s: %s.%s",SecurityLayer.getUser(request), Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName()));
        HttpSession session = SecurityLayer.checkSession(request);
        if(session != null) {
            int userid = Integer.parseInt(request.getParameter("userid"));
            Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.INFO, String.format("%s: edits user %d", SecurityLayer.getUser(request), userid));
            new BE_UpdateProfile().editUser_Admin(request, response, userid, getServletContext());
            
            request.setAttribute("message", "User updated");
            //response.sendRedirect("fe_courses?action=details_teacher&id=" + userid);
            
            return;
        }
        Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.INFO, SecurityLayer.getUser(request) + ": not logged in.");
        request.setAttribute("message", "Login necessary");
        this.action_error(request, response);
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        request.setAttribute("context", "be_listusers");
        request.setAttribute("page_title", "List users");

        try {
            if(request.getParameter("action") != null && request.getParameter("action").equals("deleteuser")) {
                action_deleteuser(request, response);
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
