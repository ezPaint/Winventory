package com.simoncomputing.app.winventory.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.simoncomputing.app.winventory.formbean.UserInfoBean;

/**
 * A BaseController class that is to be the Parent Class for all other
 * Controllers. This class provides easier forward and send redirect methods as
 * well as a quick way to get the sessions current user. In the future, methods
 * for checking permissions could be placed in here to make authorization
 * easier.
 */
public class BaseController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(BaseController.class);

    public BaseController() {
        super();
    }

    /**
     * response.sendRedirect(request.getServletContext() + target);
     * 
     * @param request
     *            HttpServlerRequest.
     * @param response
     *            HttpServlerResponse.
     * @param target
     *            Desired Location.
     */
    public void sendRedirect(HttpServletRequest request, HttpServletResponse response, String target)
            throws IOException {
        response.sendRedirect(target);
    }

    /**
     * request.getRequestDispatcher(target).forward(request, response);
     * 
     * @param request
     *            HttpServlerRequest.
     * @param response
     *            HttpServlerResponse.
     * @param target
     *            Desired Location.
     */
    public void forward(HttpServletRequest request, HttpServletResponse response, String target)
            throws IOException, ServletException {
        request.getRequestDispatcher(target).forward(request, response);
    }

    /**
     * Equivalent to: request.getSession().getAttribute("userInfo");
     * 
     * @param request
     *            the HTTP request
     * @return the UserInfoBean on session, null if not set
     */
    public UserInfoBean getUserInfo(HttpServletRequest request) {
        return (UserInfoBean) request.getSession().getAttribute("userInfo");
    }

    /**
     * Equivalent to: request.getSession().setAttribute("userInfo", userBean);
     * 
     * @param request
     *            the HTTP request
     * @param userBean
     *            the userBean to set usetInfo attribute to
     */
    public void setUserInfo(HttpServletRequest request, UserInfoBean userBean) {
        request.getSession().setAttribute("userInfo", userBean);
    }

    /**
     * Checks if the current user has a permission.
     * 
     * @param permission
     *            Permission to check.
     * @return true if user has permission, false if not.
     */
    public boolean userHasPermission(HttpServletRequest request, String permission) {
        if (getUserInfo(request) == null) {
            return false;
        }
        
        //Get the map of permissions associated with the current user
        Map<String, Boolean> permissionMap = getUserInfo(request).getHasPermission();

        // Check that the map contains the permission to check and that the
        // value associated with that permission in the map is true
        return permissionMap.containsKey(permission) && (permissionMap.get(permission));
    }

    /**
     * Checks the user's permissions and immediately redirects them to the
     * permission-error page if necessary.
     * 
     * @param permission
     *            Permission to check.
     * @return false if user has permission, true if not.
     */
    public boolean requirePermission(HttpServletRequest request, HttpServletResponse response,
            String permission) throws IOException {
        
        //If statement is true when the current user does NOT have permission
        if (!this.userHasPermission(request, permission)) {
            request.getSession().setAttribute("errMsg", permission);
            response.sendRedirect(request.getContextPath() + "/permissionDenied");
            return true;
        }
        return false;
    }
    
    public String logError(Logger errorLog , Exception e) {
        String errorCode = "";
        
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
        Calendar cal = Calendar.getInstance();
        errorCode += dateFormat.format(cal.getTime());

        errorLog.error(errorCode, e);
        
        return errorCode;
    }
}