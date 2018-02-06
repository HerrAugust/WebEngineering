package it.univaq.webengineering.controller;

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
public class FE_Homepage extends WebengineeringBaseController {

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        // Needed to support double language (ita/eng)
        String url = "frontend/homepage.ftl.html";
        String switchlang = "ITA";
        String courses = "Courses"; // menu entry
        
        TemplateResult res = new TemplateResult(getServletContext());
        //add to the template a wrapper object that allows to call the stripslashes function
        //request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
        if(request.getParameter("lang") != null && request.getParameter("lang").equals("ITA")) {
            url = "frontend/homepage_ita.ftl.html";
            switchlang = "ENG";
            courses = "Corsi";
        }
        request.setAttribute("courses", courses);
        request.setAttribute("switchlang", switchlang);
        HttpSession checkSession = SecurityLayer.checkSession(request);
        request.setAttribute("logged", SecurityLayer.checkSession(request) != null);
        res.activate(url, request, response);
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        request.setAttribute("context", "fe_homepage");
        request.setAttribute("page_title", "Homepage");

        try {
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
        return "Homepage Frontend servlet";
    }// </editor-fold>

}
