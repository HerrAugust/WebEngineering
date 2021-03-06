package it.univaq.webengineering.controller;

import it.univaq.webengineering.data.impl.ImageImpl;
import it.univaq.webengineering.data.impl.TeacherImpl;
import it.univaq.webengineering.data.impl.WebengineeringDataLayerMysqlImpl;
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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

@MultipartConfig
public class BE_UpdateProfile extends WebengineeringBaseController {

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
        String url = "backend/profile.ftl.html";
        String switchlang = "ITA";

        if(SecurityLayer.checkSession(request) == null) {
            Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.INFO, SecurityLayer.getUser(request) + ": not logged in.");
            request.setAttribute("message", "You must be logged in!");
            action_error(request, response);
            return;
        }

        TemplateResult res = new TemplateResult(getServletContext());
        // get info about the requester
        String email = (String)SecurityLayer.checkSession(request).getAttribute("username");
        Teacher curUser  = ((WebengineeringDataLayer)request.getAttribute("datalayer")).getTeacher(email);
        if(curUser.isItalian() || request.getParameter("lang") != null && request.getParameter("lang").equals("ITA")) {
            url = "backend/profile_ita.ftl.html";
            switchlang = "ENG";
        }
        else {
            // if user hasn't forced the language of the page, check his language and use it
            if(curUser.isItalian()) {
                url = "backend/profile_ita.ftl.html";
                switchlang = "ENG";
            }
        }

        request.setAttribute("teacher", curUser);
        request.setAttribute("switchlang", switchlang);
        request.setAttribute("isAdmin", curUser.isAdmin());
        res.activate(url, request, response);
    }

    private void action_updateprofile(HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateManagerException {
        Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.INFO, String.format("%s: %s.%s",SecurityLayer.getUser(request), Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName()));
        
        String text = "";
        HttpSession session = SecurityLayer.checkSession(request);
        if(session != null) {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String lastname = request.getParameter("lastname");
            String language = request.getParameter("language");
            String password = request.getParameter("password");
            String password2 = request.getParameter("password2");
            String email = request.getParameter("email");

            //store photo
            String photoName = "", randomName = "";
            String filePath = getServletContext().getInitParameter("file-upload");
            boolean multipart = ServletFileUpload.isMultipartContent(request);
            boolean imageUploaded = false;
            if (multipart) {
                try {
                    Part item = request.getPart("image");
                    if(item != null && item.getSize() > 0) {
                        imageUploaded = true;
                        String namefile = item.getSubmittedFileName();

                        // check if width and height are the same
                        BufferedImage bimg = ImageIO.read(item.getInputStream());
                        int width          = bimg.getWidth();
                        int height         = bimg.getHeight();
                        if(width != height) {
                            String txt = "Error: image height and width are not the same";
                            if(SecurityLayer.getTeacher(request).isItalian())
                                txt = "Errore: dimensioni immagine non uguali";
                            request.setAttribute("message", txt);
                            this.action_default(request, response);
                            return;
                        }

                        photoName = namefile;
                        long size = item.getSize();
                        String imagetype = item.getContentType().split("/")[1];
                        if (size > 0 && !namefile.isEmpty()) {
                            randomName = SecurityLayer.generateRandomString(7) + "." + imagetype;
                            //File target = new File(filePath + randomName);
                            item.write(filePath + randomName);
                        }
                    }
                } catch (ServletException ex) {
                    request.setAttribute("exception", ex);
                    action_error(request, response);
                }
            }

            if(!password.equals("") && !password.equals(password2))  {
                String txt = "You did not repeat the new password well.";
                if(SecurityLayer.getTeacher(request).isItalian())
                    txt = "Non ha ripetuto bene la password";
                request.setAttribute("message", txt);
                action_error(request, response);
                return;
            }

            // Get previous profile photo (must be deleted from DB)
            Teacher teacher = ((WebengineeringDataLayer)request.getAttribute("datalayer")).getTeacher(id);
            Image oldImage = ((WebengineeringDataLayer)request.getAttribute("datalayer")).getImageByTeacher(teacher.getId());

            // Save photo to DB
            Image image = teacher.getPhoto();
            if(! photoName.equals("")) { // if a photo is uploaded
                image = new ImageImpl(null);
                image.setName_on_disk(randomName);
                image.setOriginal_name(photoName);
                image.setPath(filePath);
                int photoid = ((WebengineeringDataLayer)request.getAttribute("datalayer")).insertImage(image);
                image.setId(photoid);
            }

            // save teacher to DB
            Teacher t = new TeacherImpl(null);
            t.setId(id);
            t.setName(name);
            t.setLastname(lastname);
            t.setLanguage(language);
            t.setEmail(email);
            t.setPassword(password);
            t.setPhoto(image);
            t.setType(teacher.getType());

            boolean res = ((WebengineeringDataLayer)request.getAttribute("datalayer")).updateTeacher(t);
            text = "Error while updating info of Teacher.";
            if(SecurityLayer.getTeacher(request).isItalian())
                text = "Errore aggiornamento professore";

            // Delete old image from DB and disk
            if(oldImage != null && imageUploaded) {
                ((WebengineeringDataLayer)request.getAttribute("datalayer")).deleteImage(oldImage.getId());
                new File(oldImage.getPath() + oldImage.getName_on_disk()).delete();
            }

            if(res) {
                Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.INFO, String.format("%s: updates his/her own profile", SecurityLayer.getUser(request)));
                response.sendRedirect("fe_courses?action=details_teacher&id="+t.getId());
                return;
            }
        }
        else {
            Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.INFO, SecurityLayer.getUser(request) + ": not logged in.");
            text = "You must be logged and be administrator!";
        }
        request.setAttribute("error", text);
        action_error(request, response);
        return;
    }

    /*
    * Allows the admin to modify the profile of the user with id userid.
    * This method only shows the GUI for editing his info
    */
    protected void editUser_Admin(HttpServletRequest request, HttpServletResponse response, int userid, ServletContext servletContext) throws TemplateManagerException {
        Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.INFO, String.format("%s: %s.%s",SecurityLayer.getUser(request), Thread.currentThread().getStackTrace()[1].getClassName(), Thread.currentThread().getStackTrace()[1].getMethodName()));
        
        HttpSession session = SecurityLayer.checkSession(request);
        if(session != null) {
            // Check user is authenticated as admin
            String email = (String)session.getAttribute("username");
            Teacher curUser  = ((WebengineeringDataLayer)request.getAttribute("datalayer")).getTeacher(email);
            if(curUser.getType().equals("admin") == false) {
                request.setAttribute("message", "You must be logged and be administrator!");
                action_error(request, response);
                return;
            }

            // Shows the page to update the profile of the other person
            // Needed to support double language (ita/eng)
            String url = "backend/adduser.ftl.html";
            String switchlang = "ITA";

            TemplateResult res = new TemplateResult(servletContext);
            // get info about the user with id userid
            Teacher userToBeUpdated  = ((WebengineeringDataLayer)request.getAttribute("datalayer")).getTeacher(userid);
            if(request.getParameter("lang") != null && request.getParameter("lang").equals("ITA")) {
                url = "backend/adduser_ita.ftl.html";
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
            request.setAttribute("isAdmin", true);
            request.setAttribute("edit_user", true);
            res.activate(url, request, response);
            return;
        }
        Logger.getLogger(WebengineeringDataLayerMysqlImpl.class.getName()).log(Level.INFO, SecurityLayer.getUser(request) + ": not logged in.");   
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
