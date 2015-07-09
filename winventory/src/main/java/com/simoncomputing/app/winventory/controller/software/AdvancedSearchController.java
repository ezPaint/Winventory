package com.simoncomputing.app.winventory.controller.software;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.simoncomputing.app.winventory.bo.SoftwareBo;
import com.simoncomputing.app.winventory.domain.Software;
import com.simoncomputing.app.winventory.util.BoException;
import org.apache.log4j.Logger;


/**
 * Controller for Advanced Search functionality
 * 
 * @author jessica.ya
 *
 */
@WebServlet("/software/advancedsearch")
public class AdvancedSearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(AdvancedSearchController.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		SoftwareBo softwareBo = SoftwareBo.getInstance();
//
//		String name = (String) request.getParameter("name");
//		String serialNo = (String) request.getParameter("serialNo");
//		String version = (String) request.getParameter("version");
//		String licenseKey = (String) request.getParameter("licenseKey");
//		String cost = (String) request.getParameter("cost");
////		String purchaseDateStart = (String) request.getParameter("purchaseDateStart");
////		String purchaseDateEnd = (String) request.getParameter("purchaseDateEnd");
////		String expirationDateStart = (String) request.getParameter("expirationDateStart");
////		String expirationDateEnd = (String) request.getParameter("expirationDateEnd");
////		
////		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
////	      java.sql.Date sqlPStartDate=null;
////	      java.sql.Date sqlPEndDate=null;
////	      java.sql.Date sqlEStartDate = null;
////	      java.sql.Date sqlEEndDate = null;
////	      
////	      try{
////	      sqlPStartDate= new java.sql.Date(dateFormat.parse(purchaseDateStart).getTime());
////	      //System.out.println("Date as String; "+response.getReturnedStartDate()+" SqlDate: "+sqlStartDate );
////	      sqlPEndDate= new java.sql.Date(dateFormat.parse(purchaseDateEnd).getTime());
////	      //System.out.println("Date as String; "+response.getNextPaymentDate()+" SqlDate: "+sqlNextDate );
////	      sqlEStartDate = new java.sql.Date(dateFormat.parse(expirationDateStart).getTime());
////	      sqlEEndDate = new java.sql.Date(dateFormat.parse(expirationDateEnd).getTime());
////	      } catch (ParseException e){
////	          log.error(e.getMessage());
////	      }
//
//      request.setAttribute("name", name);      
//      request.setAttribute("serialNo", serialNo);
//      request.setAttribute("version", version);
//      request.setAttribute("licenseKey", licenseKey);
//      request.setAttribute("cost", cost);
//      
////      request.setAttribute("purchaseDateStart", purchaseDateStart);
////      request.setAttribute("purchaseDateEnd", purchaseDateEnd);
////      
////      request.setAttribute("expirationDateStart", expirationDateStart);
////      request.setAttribute("expirationDateEnd", expirationDateEnd);
//		
//		
//		ArrayList<Software> softwares = new ArrayList<Software>();
//		if (name == null && serialNo == null && version == null && licenseKey == null && cost == null) {
//			try {
//				softwares = (ArrayList<Software>) softwareBo.getDefaultResults(10);
//			} catch (BoException e) {
//				log.error(e.getMessage());
//			}
//		} else {
//			try {
//				name = name.toUpperCase();
//				serialNo = serialNo.toUpperCase();
//				version = version.toUpperCase();
//				licenseKey = licenseKey.toUpperCase();
//				softwares = (ArrayList<Software>) softwareBo.advancedSearch(name, serialNo,
//						version, licenseKey, cost);//, sqlPStartDate, sqlPEndDate, sqlEStartDate, sqlEEndDate);
//			} catch (BoException e) {
//				log.error(e.getMessage());
//			}
//		}
//
//		if (softwares.size() > 0) {
//			request.setAttribute("softwares", softwares);
//			request.setAttribute("swResult", true);
//		}
		request.getRequestDispatcher("/WEB-INF/flows/software/search.jsp").forward(request,
				response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		SoftwareBo softwareBo = SoftwareBo.getInstance();

		
		String name = (String) request.getParameter("name");
		System.out.println(name);
				String serialNo = (String) request.getParameter("serialNo");
		System.out.println(serialNo);
				String version = (String) request.getParameter("version");
		System.out.println(version);
				String licenseKey = (String) request.getParameter("licenseKey");
		System.out.println(licenseKey);
				String cost = (String) request.getParameter("cost");
		System.out.println(cost);
//				String purchaseDateStart = (String) request.getParameter("purchaseDateStart");
//				String purchaseDateEnd = (String) request.getParameter("purchaseDateEnd");
//				String expirationDateStart = (String) request.getParameter("expirationDateStart");
//				String expirationDateEnd = (String) request.getParameter("expirationDateEnd");
//				
//				SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
//			      java.sql.Date sqlPStartDate=null;
//			      java.sql.Date sqlPEndDate=null;
//			      java.sql.Date sqlEStartDate = null;
//			      java.sql.Date sqlEEndDate = null;
//			      
//			      try{
//			      sqlPStartDate= new java.sql.Date(dateFormat.parse(purchaseDateStart).getTime());
//			      //System.out.println("Date as String; "+response.getReturnedStartDate()+" SqlDate: "+sqlStartDate );
//			      sqlPEndDate= new java.sql.Date(dateFormat.parse(purchaseDateEnd).getTime());
//			      //System.out.println("Date as String; "+response.getNextPaymentDate()+" SqlDate: "+sqlNextDate );
//			      sqlEStartDate = new java.sql.Date(dateFormat.parse(expirationDateStart).getTime());
//			      sqlEEndDate = new java.sql.Date(dateFormat.parse(expirationDateEnd).getTime());
//			      } catch (ParseException e){
//			          log.error(e.getMessage());
//			      }

		      request.setAttribute("name", name);      
		      request.setAttribute("serialNo", serialNo);
		      request.setAttribute("version", version);
		      request.setAttribute("licenseKey", licenseKey);
		      request.setAttribute("cost", cost);
		      
		      
				ArrayList<Software> softwares = new ArrayList<Software>();
				if (name == null && serialNo == null && version == null && licenseKey == null && cost == null) {
					try {
						softwares = (ArrayList<Software>) softwareBo.getDefaultResults(10);
					} catch (BoException e) {
						log.error(e.getMessage());
					}
				} else {
					try {
						name = name.toUpperCase();
						serialNo = serialNo.toUpperCase();
						version = version.toUpperCase();
						licenseKey = licenseKey.toUpperCase();
						softwares = (ArrayList<Software>) softwareBo.advancedSearch(name, serialNo,
								version, licenseKey, cost);//, sqlPStartDate, sqlPEndDate, sqlEStartDate, sqlEEndDate);
					} catch (BoException e) {
						log.error(e.getMessage());
					}
				}

				if (softwares.size() > 0) {
					request.setAttribute("softwares", softwares);
					request.setAttribute("swResult", true);
				}
//		      request.setAttribute("purchaseDateStart", purchaseDateStart);
//		      request.setAttribute("purchaseDateEnd", purchaseDateEnd);
		      
//		      request.setAttribute("expirationDateStart", expirationDateStart);
//		      request.setAttribute("expirationDateEnd", expirationDateEnd);
		request.getRequestDispatcher("/WEB-INF/flows/software/swResults.jsp").forward(request,
				response);
	}
}
