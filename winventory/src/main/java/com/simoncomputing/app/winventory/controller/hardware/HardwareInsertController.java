package com.simoncomputing.app.winventory.controller.hardware;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.simoncomputing.app.winventory.bo.HardwareBo;
import com.simoncomputing.app.winventory.bo.RefConditionBo;
import com.simoncomputing.app.winventory.controller.BaseController;
import com.simoncomputing.app.winventory.domain.Hardware;
import com.simoncomputing.app.winventory.domain.RefCondition;
import com.simoncomputing.app.winventory.util.BoException;

@WebServlet("/hardware/insert")
public class HardwareInsertController extends BaseController {
    private static final long serialVersionUID = 1L;
    
    private Logger log = Logger.getLogger(HardwareInsertController.class); 

    protected void doGet( HttpServletRequest request, HttpServletResponse response ) 
        throws ServletException, IOException {
        ArrayList<RefCondition> conditions = null;
        try {
            conditions = new ArrayList<RefCondition>(RefConditionBo.getInstance().getAll());
        } catch (BoException e) {
            request.setAttribute("error", e.getMessage());
            log.error(e.getMessage());
        }
        
        if (conditions != null) {
            request.setAttribute("conditions", conditions);
        }
        request.getRequestDispatcher("/WEB-INF/flows/hardware/insert.jsp").forward(request, response);  
    }


    protected void doPost( HttpServletRequest request, HttpServletResponse response ) 
        throws ServletException, IOException {

        Hardware h = new Hardware();
        
        h.setType(request.getParameter("type"));
        h.setCondition(request.getParameter("condition"));
        h.setCost(Double.parseDouble(request.getParameter("cost")));
        h.setDescription(request.getParameter("description"));
        h.setLocationId(null);//Integer.parseInt(request.getParameter("locationId")));
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            h.setPurchaseDate(format.parse(request.getParameter("date")));
        } catch (ParseException e) {
            request.setAttribute("error", e.getMessage());
            log.error(e.getMessage());
        }
        h.setSerialNo(request.getParameter("serialNo"));
        h.setUserId(null);//Integer.parseInt(request.getParameter("userId")));
        
        HardwareBo bo = HardwareBo.getInstance();
        try {
            bo.create(h);
        } catch (BoException e) { 
            request.setAttribute("error", e.getMessage());
            log.error(e.getMessage());
        }
        
        ArrayList<RefCondition> conditions = null;
        try {
            conditions = new ArrayList<RefCondition>(RefConditionBo.getInstance().getAll());
        } catch (BoException e) {
            request.setAttribute("error", e.getMessage());
            log.error(e.getMessage());
        }
        
        if (conditions != null) {
            request.setAttribute("conditions", conditions);
        }
        
        request.getRequestDispatcher("/WEB-INF/flows/hardware/insert.jsp").forward(request, response);
    }

}