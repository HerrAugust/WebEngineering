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
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

public class BE_UpdateProfile extends WebengineeringBaseController {

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        // Needed to support double language (ita/eng)
        String url = "backend/profile.ftl.html";
        String switchlang = "ITA";
        
        if(SecurityLayer.checkSession(request) == null) {
            request.setAttribute("message", "You must be logged in!");
            action_error(request, response);
            return;
        }
        
        TemplateResult res = new TemplateResult(getServletContext());
        // get info about the requester
        String email = (String)SecurityLayer.checkSession(request).getAttribute("username");
        Teacher curUser  = ((WebengineeringDataLayer)request.getAttribute("datalayer")).getTeacher(email);
        if(request.getParameter("lang") != null && request.getParameter("lang").equals("ITA")) {
            url = "backend/profile_ita.ftl.html";
            switchlang = "ENG";
        }
        else { 
            // if user hasn't forced the language of the page, check his language and use it
            if(curUser.getLanguage().toLowerCase().equals("ita")) {
                url = "backend/profile_ita.ftl.html";
                switchlang = "ENG"; 
            }
        }
        
        request.setAttribute("teacher", curUser);
        request.setAttribute("switchlang", switchlang);
        res.activate(url, request, response);
    }
    
    private void action_updateprofile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = SecurityLayer.checkSession(request);
        if(session != null) {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String lastname = request.getParameter("lastname");
            String language = request.getParameter("language");
            String password = request.getParameter("password");
            String password2 = request.getParameter("password2");
            String email = request.getParameter("email");
            String type = request.getParameter("type");
            
            //store photo
            String photoName = "", randomName = "";
            String filePath = getServletContext().getInitParameter("file-upload"); 
            if (ServletFileUpload.isMultipartContent(request)) {
            try {
                FileItemFactory fif = new DiskFileItemFactory();
                ServletFileUpload sfo = new ServletFileUpload(fif);
                List<FileItem> items = sfo.parseRequest((RequestContext) request);
                for (FileItem item : items) {
                    String fname = item.getFieldName();
                    if (item.isFormField() && fname.equals("n") && !item.getString().isEmpty()) {
                        request.setAttribute("n", item.getString());
                    } else if (!item.isFormField() && fname.equals("f1")) {
                        String namefile = item.getName();
                        photoName = namefile;
                        String contentType = item.getContentType();
                        long size = item.getSize();
                        if (size > 0 && !namefile.isEmpty()) {
                            randomName = SecurityLayer.generateRandomString7();
                            File target = new File(filePath + randomName);
                            item.write(target);
                        }
                    }
                }
            } catch (FileUploadException ex) {
                request.setAttribute("exception", ex);
                action_error(request, response);
            } catch (Exception ex) {
                request.setAttribute("exception", ex);
                action_error(request, response);
            }
            
            if(!password.equals("") && !password.equals(password2))  {
                request.setAttribute("message", "You did not confirm the new password well.");
                action_error(request, response);
                return;
            }
            
            Teacher t = new TeacherImpl(null);
            t.setId(id);
            t.setName(name);
            t.setLastname(lastname);
            t.setLanguage(language);
            t.setEmail(email);
            t.setPassword(password);
            
            boolean res = ((WebengineeringDataLayer)request.getAttribute("datalayer")).updateTeacher(t);
            String text = "Error while updating info of Teacher.";
            if(res)
                text = "ok";
                
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(text);
            return;
        }
        request.setAttribute("message", "You must be logged and be administrator!");
        action_error(request, response);
        return;
    }
    
    /*
    Allows the admin to modify the profile of the user with id userid
    */
    protected void editUser_Admin(HttpServletRequest request, HttpServletResponse response, int userid, ServletContext servletContext) throws TemplateManagerException {
        HttpSession session = SecurityLayer.checkSession(request);
        if(session != null) {
            // Check user is authenticated as admin
            String email = (String)SecurityLayer.checkSession(request).getAttribute("username");
            Teacher curUser  = ((WebengineeringDataLayer)request.getAttribute("datalayer")).getTeacher(email);
            if(curUser.getType().equals("admin") == false) {
                request.setAttribute("message", "You must be logged and be administrator!");
                action_error(request, response);
                return;
            }
            
            // Shows the page to update the profile of the other person
            // Needed to support double language (ita/eng)
            String url = "backend/profile.ftl.html";
            String switchlang = "ITA";

            TemplateResult res = new TemplateResult(servletContext);
            // get info about the user with id userid
            Teacher userToBeUpdated  = ((WebengineeringDataLayer)request.getAttribute("datalayer")).getTeacher(userid);
            if(request.getParameter("lang") != null && request.getParameter("lang").equals("ITA")) {
                url = "backend/profile_ita.ftl.html";
                switchlang = "ENG";
            }
            else { 
                // if user hasn't forced the language of the page, check his language and use it
                if(curUser.getLanguage().toLowerCase().equals("ita")) {
                    url = "backend/profile_ita.ftl.html";
                    switchlang = "ENG"; 
                }
            }

            request.setAttribute("teacher", userToBeUpdated);
            request.setAttribute("switchlang", switchlang);
            res.activate(url, request, response);
        }
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        request.setAttribute("context", "be_updateprofile");
        request.setAttribute("page_title", "Update Profile");

        try {
            if(request.getParameter("action") != null && request.getParameter("action").equals("updateprofile")) {
                action_updateprofile(request, response);
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
        return "Profile Backend servlet";
    }// </editor-fold>

}
