package com.simoncomputing.app.winventory.controller.software;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import com.simoncomputing.app.winventory.bo.SoftwareBo;
import com.simoncomputing.app.winventory.controller.BaseController;
import com.simoncomputing.app.winventory.domain.Software;
import com.simoncomputing.app.winventory.util.BoException;

/**
 * Controller Servlet for searching for Software
 * @author Megan Rigsbee
 *
 */
@WebServlet("/software/search")
public class SimpleSearchController extends BaseController {
    private static final long serialVersionUID = 1L;
    private static Logger log = Logger.getLogger(SimpleSearchController.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	SoftwareBo softwareBo = SoftwareBo.getInstance();

		String searchText = (String) request.getParameter("searchText");
		//request.setAttribute("searchText", searchText);

		ArrayList<Software> softwares = new ArrayList<Software>();
			if (searchText == null) {
				try {
					softwares = (ArrayList<Software>) softwareBo.getDefaultResults(10);
				} catch (BoException e){
					e.printStackTrace();
				} 
			} else {
				try {
				searchText = searchText.toUpperCase();
				softwares = (ArrayList<Software>) softwareBo.search(searchText);
				} catch (BoException e) {
				    log.error(e.getMessage());
				}
			}

		if (softwares.size() > 0) {
			request.setAttribute("softwares", softwares);
			request.setAttribute("swResult", true);
		}
        forward(request, response, "/WEB-INF/flows/software/advanced-search.jsp");
    }
    
    /**
     * Currently goes to the software's default/results page after clicking the 'submit' button
     * while doing an advanced search.
     * The values from the search form are printed to the console to ensure that all the 
     * information was gathered. 
     * 
     * TODO: Create search results page and have the search page's results show there, instead.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = (String) request.getParameter("name");
        request.setAttribute("name", name);
        System.out.println("Name: " + name);
        
        String serialNo = (String) request.getParameter("serialNo");
        request.setAttribute("serialNo", serialNo);
        System.out.println("SerialNo: " + serialNo);

        String version = (String) request.getParameter("version");
        request.setAttribute("version", version);
        System.out.println("Version: " + version);
        
        String licenseKey = (String) request.getParameter("licenseKey");
        request.setAttribute("licenseKey", licenseKey);
        System.out.println("License Key: " + licenseKey);
        
        String cost = (String) request.getParameter("cost");
        request.setAttribute("cost", cost);
        System.out.println("Cost: " + cost);
        
        String purchaseDateStart = (String) request.getParameter("purchaseDateStart");
        request.setAttribute("purchaseDateStart", purchaseDateStart);
        String purchaseDateEnd = (String) request.getParameter("purchaseDateEnd");
        request.setAttribute("purchaseDateEnd", purchaseDateEnd);
        System.out.println("Purchased: " + purchaseDateStart + " to " + purchaseDateEnd);
        
        String expirationDateStart = (String) request.getParameter("expirationDateStart");
        request.setAttribute("expirationDateStart", expirationDateStart);
        String expirationDateEnd = (String) request.getParameter("expirationDateEnd");
        request.setAttribute("expirationDateEnd", expirationDateEnd);

        System.out.println("Expiration: " + expirationDateStart + " to " + expirationDateEnd);
        
        //TODO For now, the search results just returns to the default software results page.

        request.getRequestDispatcher("/WEB-INF/flows/software/swResults.jsp").forward(request, response);  

    }
}
