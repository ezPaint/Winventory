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

@WebServlet("/hardware/edit")
public class HardwareEditController extends BaseController {
    private static final long serialVersionUID = 1L;
    
    private Logger log = Logger.getLogger(HardwareEditController.class); 

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String key = request.getParameter("key");

        Hardware hardware = null;
        if (key != null) {
            try {
                Long long_key = Long.parseLong(key);
                hardware = HardwareBo.getInstance().read(long_key);
            } catch (BoException e) {
                e.printStackTrace();
            }
        }
        
        if (hardware == null) {
            request.setAttribute("error", "The key is not valid or there is no hardware with that key");
            log.error("The key is not valid or there is no hardware with that key");
        }
        
        ArrayList<RefCondition> conditions = null;
        try {
            conditions = new ArrayList<RefCondition>(RefConditionBo.getInstance().getAll());
        } catch (BoException e) {
            e.printStackTrace();
        }
        
        if (conditions != null) {
            request.setAttribute("conditions", conditions);
        }

        request.setAttribute("hardware", hardware);
        request.getRequestDispatcher("/WEB-INF/flows/hardware/edit.jsp").forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String key = request.getParameter("key");
        HardwareBo bo = HardwareBo.getInstance();
        Hardware hardware = null;
        if (key != null) {
            try {
                Long long_key = Long.parseLong(key);
                hardware = HardwareBo.getInstance().read(long_key);
            } catch (BoException e) {
                e.printStackTrace();
            }
        }
        
        hardware.setType(request.getParameter("type"));
        hardware.setCondition(request.getParameter("condition"));
        hardware.setCost(Double.parseDouble(request.getParameter("cost")));
        hardware.setDescription(request.getParameter("description"));
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            hardware.setPurchaseDate(format.parse(request.getParameter("date")));
        } catch (ParseException e) {
            System.out.println(e);
        }
        hardware.setSerialNo(request.getParameter("serialNo"));
        
        try {
            bo.update(hardware);
        } catch (BoException e) {
            e.printStackTrace();
        }

        request.setAttribute("key", key);
        
        this.sendRedirect(request, response, "view?key=" + key);
        
    }

}