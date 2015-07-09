package com.simoncomputing.app.winventory.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.simoncomputing.app.winventory.bo.HardwareBo;
import com.simoncomputing.app.winventory.domain.Hardware;
import com.simoncomputing.app.winventory.util.BoException;

@WebServlet("/hardware/view")
public class ViewHardwareController extends BaseController {
    private static final long serialVersionUID = 1L;
    
    private Logger log = Logger.getLogger(ViewHardwareController.class); 

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String key = request.getParameter("key");

        Hardware hardware = null;
        if (key != null) {
            try {
                Long long_key = Long.parseLong(key);
                hardware = HardwareBo.getInstance().read(long_key);
            } catch (BoException e) {
                request.setAttribute("error", e.getMessage());
                log.error(e.getMessage());
            }
        }
        
        if (hardware == null) {
            request.setAttribute("error", "The key is not valid or there is no hardware with that key");
            log.error("The key is not valid or there is no hardware with that key");
        }

        request.setAttribute("hardware", hardware);
        request.getRequestDispatcher("/WEB-INF/flows/hardware/view.jsp").forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String key = request.getParameter("key");
        HardwareBo bo = HardwareBo.getInstance();
        Long long_key = null;
        if (key != null) {
            long_key = Long.parseLong(key);
        }
        
        try {
            bo.delete(long_key);
        } catch (BoException e) {
            e.printStackTrace();
        }
        
        this.sendRedirect(request, response, "results");
                
    }

}