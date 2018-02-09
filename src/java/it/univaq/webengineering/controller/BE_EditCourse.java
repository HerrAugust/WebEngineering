package it.univaq.webengineering.controller;

import it.univaq.webengineering.data.impl.CourseImpl;
import it.univaq.webengineering.data.impl.ImageImpl;
import it.univaq.webengineering.data.impl.TeacherImpl;
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
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@MultipartConfig
public class BE_EditCourse extends WebengineeringBaseController {

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    // If you pass the course, it will show the edit page of course with its information. pagenumber = 1 shows the course base info page, = 2 shows the course description page
    private void action_default(HttpServletRequest request, HttpServletResponse response, int pagenumber) throws IOException, ServletException, TemplateManagerException {
        // Needed to support double language (ita/eng)
        String url = "backend/be_editcoursebase_teacher.ftl.html";
        if(pagenumber == 2) url = "backend/be_editcoursedescription_teacher.ftl.html";
        String switchlang = "ITA";
        String coursecode = request.getParameter("coursecode");
        
        if(SecurityLayer.checkSession(request) == null) {
            request.setAttribute("message", "You must be logged in!");
            action_error(request, response);
            return;
        }
        if(coursecode == null || coursecode.equals("")) {
            request.setAttribute("message", "Course code is null!");
            action_error(request, response);
            return;
        }
        
        TemplateResult res = new TemplateResult(getServletContext());
        if(request.getParameter("lang") != null && request.getParameter("lang").equals("ITA")) {
            url = "backend/be_editcoursebase_teacher_ita.ftl.html";
            if(pagenumber == 2) url = "backend/be_editcoursedescription_teacher.ftl.html";
            switchlang = "ENG";
        }
        else { 
            // if user hasn't forced the language of the page, check his language and use it
            String email = (String)SecurityLayer.checkSession(request).getAttribute("username");
            if(((WebengineeringDataLayer)request.getAttribute("datalayer")).getTeacher(email).getLanguage().toLowerCase().equals("ita")) {
                url = "backend/be_editcoursebase_teacher_ita.ftl.html";
                if(pagenumber == 2) url = "backend/be_editcoursedescription_teacher_ita.ftl.html";
                switchlang = "ENG"; 
            }
        }
        
        Course course = ((WebengineeringDataLayer)request.getAttribute("datalayer")).getCourse(coursecode);
        request.setAttribute("course", course);
        String i = super.getCurrentAcademicYear();
        request.setAttribute("academic_year", super.getCurrentAcademicYear());
        request.setAttribute("switchlang", switchlang);
        res.activate(url, request, response);
    }
    
    private void action_savebaseinfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = SecurityLayer.checkSession(request);
        if(session != null) {
            String coursecode = request.getParameter("coursecode");
            String ssd = request.getParameter("ssd");
            String language = request.getParameter("language");
            int semester = Integer.parseInt(request.getParameter("semester"));
            int courseid = ((WebengineeringDataLayer)request.getAttribute("datalayer")).getCourse(coursecode).getId();
            
            Course t = new CourseImpl(null);
            t.setId(courseid);
            t.setCode(coursecode);
            t.setSSD(ssd);
            t.setSemester(semester);
            t.setLanguage(language);
            
            boolean res = ((WebengineeringDataLayer)request.getAttribute("datalayer")).updateCourseBaseInfo(t);
            String text = "Error while saving basic info.";
            if(res)
                text = "ok";
                
            response.sendRedirect("be_editcourse?action=showBe_EditCourseDescription_teacher&coursecode="+coursecode);
            return;
        }
        request.setAttribute("message", "You must be logged!");
        action_error(request, response);
        return;
    }
    
    private void action_savedescription(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = SecurityLayer.checkSession(request);
        if(session != null) {
            String coursecode = request.getParameter("coursecode");
            String prerequisites = request.getParameter("prerequisites");
            String learning_outcomes = request.getParameter("language");
            String assessment_method = request.getParameter("assessment_method");
            String teaching_method = request.getParameter("teaching_method");
            String notes = request.getParameter("notes");
            String prerequisites_ita = request.getParameter("prerequisites_ita");
            String learning_outcomes_ita = request.getParameter("language_ita");
            String assessment_method_ita = request.getParameter("assessment_method_ita");
            String teaching_method_ita = request.getParameter("teaching_method_ita");
            String notes_ita = request.getParameter("notes_ita");
            int courseid = ((WebengineeringDataLayer)request.getAttribute("datalayer")).getCourse(coursecode).getId();
            
            List<String> photoNames = new LinkedList<>(), randomNames = new LinkedList<>();
            String filePath = getServletContext().getInitParameter("file-upload"); // see web.conf file
            try {
                int i = 0;
                while(true) {
                    Part item = request.getPart("file-" + i++);
                    if(item == null) break;
                    String namefile = item.getSubmittedFileName();
                    photoNames.add(namefile);
                    long size = item.getSize();
                    String imagetype = item.getContentType().split("/")[1];
                    if (size > 0 && !namefile.isEmpty()) {
                        randomNames.add(SecurityLayer.generateRandomString(7) + "." + imagetype);
                        //File target = new File(filePath + randomName);
                        item.write(filePath + randomNames.get(randomNames.size()-1));
                    }
                }
            } catch (ServletException ex) {
                request.setAttribute("exception", ex);
                action_error(request, response);
            }
            
            // Get previous profile photo (must be deleted from DB)
            Course course = ((WebengineeringDataLayer)request.getAttribute("datalayer")).getCourse(courseid);
            List<Image> oldImages = ((WebengineeringDataLayer)request.getAttribute("datalayer")).getImagesByCourse(course.getId());
            
            // Save photos to DB
            List<Image> savedImages = new LinkedList<>();
            for(int i = 0; i < randomNames.size(); i++) {
                String randomName = randomNames.get(i);
                String photoName = photoNames.get(i);
                
                ImageImpl image = new ImageImpl(null);
                image.setName_on_disk(randomName);
                image.setOriginal_name(photoName);
                image.setPath(filePath);
                image.setCourse_id(courseid);
                int photoid = ((WebengineeringDataLayer)request.getAttribute("datalayer")).insertImage(image);
                image.setId(photoid);
                
                savedImages.add(image);
            }
            
            // save course to DB
            Course t = new CourseImpl(null);
            t.setId(courseid);
            t.setCode(coursecode);
            t.setPrerequisites(prerequisites);
            t.setLearning_outcomes(learning_outcomes);
            t.setAssessment_method(assessment_method);
            t.setTeaching_method(teaching_method);
            t.setNotes(notes);
            t.setPrerequisites_ita(prerequisites_ita);
            t.setLearning_outcomes_ita(learning_outcomes_ita);
            t.setAssessment_method_ita(assessment_method_ita);
            t.setTeaching_method_ita(teaching_method_ita);
            t.setNotes_ita(notes_ita);
            
            boolean res = ((WebengineeringDataLayer)request.getAttribute("datalayer")).updateCourseDescription(t);
            String text = "ok";
            if(!res)
                text = "Error while saving the description of the course.";
            
            // delete old images of course
            if(oldImages.isEmpty() == false) {
                for(Image oldImage : oldImages) {
                    ((WebengineeringDataLayer)request.getAttribute("datalayer")).deleteImage(oldImage.getId());
                    new File(oldImage.getPath() + oldImage.getName_on_disk()).delete();
                }
            }
            
            if(text.equals("ok")) {
                request.setAttribute("message", "ok");
                response.sendRedirect("fe_courses?action=coursedetails&id=" + courseid);
            }
            else {
                request.setAttribute("message", text);
                action_error(request, response);
            }
            return;
        }
        request.setAttribute("message", "You must be logged!");
        action_error(request, response);
    }
    
    private void action_decouple_teacher(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = SecurityLayer.checkSession(request);
        if(session != null) {
            int teacher_id = Integer.parseInt(request.getParameter("teacher_id"));
            int course_id = Integer.parseInt(request.getParameter("course_id"));
            boolean success = ((WebengineeringDataLayer)request.getAttribute("datalayer")).decouple_course(course_id, teacher_id);
            
            if(!success) {
                request.setAttribute("message", "Error while decoupling teacher and course");
                action_error(request,response);
                return;
            }
            response.sendRedirect("be_homepage");
            return;
        }
        request.setAttribute("message", "You must be logged!");
        action_error(request, response);
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        request.setAttribute("context", "be_editcourse");
        request.setAttribute("page_title", "Edit course");

        try {
            if(request.getParameter("action") == null) {
                action_default(request, response,1);
                return; 
            }
            
            switch(request.getParameter("action")) {
                case "showBe_EditCourseDescription_teacher":
                    action_default(request, response, 2);
                    break;
                case "coursedescription":
                    action_savedescription(request, response);
                    break;
                case "coursebase":
                    action_savebaseinfo(request, response);
                    break;
                case "decouple_teacher":
                    action_decouple_teacher(request,response);
                    break;
                default:
                    action_default(request, response,1);
                    break;
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
        return "Homepage Backend servlet";
    }// </editor-fold>

}
