package it.univaq.webengineering.controller;

import it.univaq.webengineering.data.impl.WebengineeringDataLayerMysqlImpl;
import it.univaq.webengineering.data.model.WebengineeringDataLayer;
import it.univaq.webengineering.framework.data.DataLayerException;
import it.univaq.webengineering.framework.result.FailureResult;
import java.io.IOException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author Giuseppe Della Penna
 */
public abstract class WebengineeringBaseController extends HttpServlet {

    @Resource(name = "jdbc/webengineering")
    private DataSource ds;

    protected abstract void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException;

    private void processBaseRequest(HttpServletRequest request, HttpServletResponse response) {
        //WARNING: never declare DB-related objects including references to Connection and Statement (as our data layer)
        //as class variables of a servlet. Since servlet instances are reused, concurrent requests may conflict on such
        //variables leading to unexpected results. To always have different connections and statements on a per-request
        //(i.e., per-thread) basis, declare them in the doGet, doPost etc. (or in methods called by them) and 
        //(possibly) pass such variables through the request.
        try (WebengineeringDataLayer datalayer = new WebengineeringDataLayerMysqlImpl(ds)) {
            datalayer.init();
            request.setAttribute("datalayer", datalayer);
            processRequest(request, response);
        } catch (Exception ex) {
            ex.printStackTrace(); //for debugging only
            (new FailureResult(getServletContext())).activate(
                    (ex.getMessage() != null || ex.getCause() == null) ? ex.getMessage() : ex.getCause().getMessage(), request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processBaseRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processBaseRequest(request, response);
    }

    protected String getCurrentAcademicYear() {
        String ay = "";
        int year_init = Calendar.getInstance().get(Calendar.YEAR)-1;
        int year_end = year_init+1;
        int month = Calendar.getInstance().get(Calendar.MONTH);
        
        if(month > Calendar.SEPTEMBER) {
            year_init++;
            year_end++;
        }
        
        ay = /*"A.Y. " + */year_init + "/" + year_end;
        return ay;
    }

}
