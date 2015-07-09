package com.simoncomputing.app.winventory.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter("/*")
public class LoginFilter implements Filter {
    
    final static Logger logger = Logger.getLogger(LoginFilter.class);

    /**
     * Default constructor. 
     */
    public LoginFilter() {

    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
        
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // log this in the debug log
        if (logger.isDebugEnabled()) {
            logger.debug("Login filter");
        }
        
        // the request and response objects
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        
        // the uri the user requested
        String uri = req.getRequestURI();
        String appName = req.getContextPath();
        
        // get the current session. do not create new one if null
        HttpSession session = req.getSession(false);
        
        // check that the user is either logged in or accessing publicly available locations
        if (!(uri.startsWith(appName + "/resources")) && (session == null || session.getAttribute("userInfo") == null) 
                && !(uri.startsWith(appName +"/login"))  && !(uri.startsWith(appName + "/logout"))) {
            
            //redirect the user if neither logged in nor visiting a public location
            res.sendRedirect(req.getContextPath() + "/login?next=" + uri);
            
        } else {
            // pass the request along the filter chain
            chain.doFilter(request, response);
        }
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {

    }

}
