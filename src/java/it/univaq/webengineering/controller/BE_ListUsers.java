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

public class BE_ListUsers extends WebengineeringBaseController {

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        // Needed to support double language (ita/eng)
        String url = "backend/listusers.ftl.html";
        String switchlang = "ITA";
        
        TemplateResult res = new TemplateResult(getServletContext());
        //add to the template a wrapper object that allows to call the stripslashes function
        //request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
        if(request.getParameter("lang") != null && request.getParameter("lang").equals("ITA")) {
            url = "backend/listusers_ita.ftl.html";
            switchlang = "ENG";
        }
        request.setAttribute("users", ((WebengineeringDataLayer)request.getAttribute("datalayer")).getTeachers());
        request.setAttribute("switchlang", switchlang);
        res.activate(url, request, response);
    }
    
    private void action_deleteuser(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException, IOException, ServletException {
        HttpSession session = SecurityLayer.checkSession(request);
        if(session != null) {
            int userid = Integer.parseInt(request.getParameter("userid"));
            boolean res = ((WebengineeringDataLayer)request.getAttribute("datalayer")).deleteTeacher(userid);
            if(res) {
                action_default(request, response);
            }
        }
    }
    
    private void action_edituser(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException {
        HttpSession session = SecurityLayer.checkSession(request);
        if(session != null) {
            int userid = Integer.parseInt(request.getParameter("userid"));
            new BE_UpdateProfile().editUser_Admin(request, response, userid, getServletContext());
        }
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
