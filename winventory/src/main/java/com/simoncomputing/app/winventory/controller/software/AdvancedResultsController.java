package com.simoncomputing.app.winventory.controller.software;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.simoncomputing.app.winventory.bo.SoftwareBo;
import com.simoncomputing.app.winventory.controller.BaseController;
import com.simoncomputing.app.winventory.domain.Software;
import com.simoncomputing.app.winventory.util.BoException;

@WebServlet("/software/advancedresults")
public class AdvancedResultsController extends BaseController {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
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
		String purchaseDateStart = (String) request.getParameter("purchaseDateStart");
		String purchaseDateEnd = (String) request.getParameter("purchaseDateEnd");
		String expirationDateStart = (String) request.getParameter("expirationDateStart");
		String expirationDateEnd = (String) request.getParameter("expirationDateEnd");

//		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
//		java.sql.Date sqlPStartDate = null;
//		java.sql.Date sqlPEndDate = null;
//		java.sql.Date sqlEStartDate = null;
//		java.sql.Date sqlEEndDate = null;
//
//		try {
//			sqlPStartDate = new java.sql.Date(dateFormat.parse(purchaseDateStart).getTime());
//			// System.out.println("Date as String; "+response.getReturnedStartDate()+" SqlDate: "+sqlStartDate
//			// );
//			sqlPEndDate = new java.sql.Date(dateFormat.parse(purchaseDateEnd).getTime());
//			// System.out.println("Date as String; "+response.getNextPaymentDate()+" SqlDate: "+sqlNextDate
//			// );
//			sqlEStartDate = new java.sql.Date(dateFormat.parse(expirationDateStart).getTime());
//			sqlEEndDate = new java.sql.Date(dateFormat.parse(expirationDateEnd).getTime());
//		} catch (ParseException e) {
//			System.out.println(e.getMessage());
//		}

		request.setAttribute("name", name);
		request.setAttribute("serialNo", serialNo);
		request.setAttribute("version", version);
		request.setAttribute("licenseKey", licenseKey);
		request.setAttribute("cost", cost);

		request.setAttribute("purchaseDateStart", purchaseDateStart);
		request.setAttribute("purchaseDateEnd", purchaseDateEnd);

		request.setAttribute("expirationDateStart", expirationDateStart);
		request.setAttribute("expirationDateEnd", expirationDateEnd);

		ArrayList<Software> softwares = new ArrayList<Software>();
//		if (name == null && serialNo == null && version == null && licenseKey == null
//				&& cost == null) {
//			try {
//				softwares = (ArrayList<Software>) softwareBo.getDefaultResults(10);
//			} catch (BoException e) {
//				e.printStackTrace();
//			}
//		} else {
			try {
				name = name.toUpperCase();
				serialNo = serialNo.toUpperCase();
				version = version.toUpperCase();
				licenseKey = licenseKey.toUpperCase();
				softwares = (ArrayList<Software>) softwareBo.advancedSearch(name, serialNo,
						version, licenseKey, cost);//, sqlPStartDate, sqlPEndDate, sqlEStartDate,
						//sqlEEndDate);
			} catch (BoException e) {
				e.printStackTrace();
			}
//		}

		if (softwares.size() > 0) {
			request.setAttribute("softwares", softwares);
			request.setAttribute("swResult", true);
		}

		request.getRequestDispatcher("/WEB-INF/flows/software/advanced-results.jsp").forward(
				request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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

		// TODO For now, the search results just returns to the default software
		// results page.

		request.getRequestDispatcher("/WEB-INF/flows/software/advanced-results.jsp").forward(
				request, response);

	}
}
