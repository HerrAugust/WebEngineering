package it.univaq.webengineering.controller;

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
public class FE_Login extends WebengineeringBaseController {

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        String url = "frontend/login.ftl.html";
        TemplateResult res = new TemplateResult(getServletContext());
        res.activate(url, request, response);
    }
    
    private void action_login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        if (request.getParameter("input_email") != null && request.getParameter("input_password") != null) {
            String email = request.getParameter("input_email");
            String password = request.getParameter("input_password");
            Teacher teacher = ((WebengineeringDataLayer)request.getAttribute("datalayer")).getTeacher(email, password);
            if (teacher != null) {
                // Set up session
                SecurityLayer.createSession(request, teacher.getEmail(), teacher.getId());
                request.setAttribute("switchlang", teacher.getLanguage());
            } else {
                response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
                response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
                response.getWriter().write("Wrong email or password");
            }
        } else {
                response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
                response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
                response.getWriter().write("No email or passoword");
        }
    }
    
    private void action_logout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        SecurityLayer.disposeSession(request);
        this.showHomepage(request, response);
    }
    
    private void showHomepage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        String url = "frontend/homepage_ita.ftl.html";
        TemplateResult res = new TemplateResult(getServletContext());
        res.activate(url, request, response);
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        HttpSession session = SecurityLayer.checkSession(request);
        request.setAttribute("page_title", "Login");
        request.setAttribute("logged", session != null);
        request.setAttribute("context", "fe_homepage");

        try {
            if(request.getRequestURI().endsWith("logout")) {
                request.setAttribute("logged", false);
                request.setAttribute("servlet", "");
                action_logout(request, response);
                return;
            }
            
            // if user has already done login
            if(session != null) {
                response.setContentType("text/plain"); 
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("You have already logged in");
                return;
            }
            
            if (request.getParameter("login") != null) {
                action_login(request, response);
                request.setAttribute("logged", true);
                
                String url = "backend/homepage.ftl.html";
                // if professor speaks italian
                if(request.getAttribute("switchlang") != null && request.getAttribute("switchlang").equals("eng")) {
                    url = "backend/homepage_ita.ftl.html";
                }
                TemplateResult res = new TemplateResult(getServletContext());
                res.activate(url, request, response);
                return;
            }
            else {
                action_default(request, response);
            }
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
