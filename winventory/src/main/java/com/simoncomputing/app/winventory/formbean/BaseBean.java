package com.simoncomputing.app.winventory.formbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A BaseBean class that all other formBeans will extend. Has a ArrayList<String> that
 * allows for easy storing and access of errors. 
 */
public class BaseBean implements Serializable{
        
        private static final long serialVersionUID = 1L;

        private List<String> errors;
        
        public BaseBean() {
                errors = new ArrayList<String>(0);
        }
        
        /**
         * Adds an error to the list of error for bean.
         * @param report The error report.
         */
        public void addError(String report) {
                errors.add(report);
        }
        
        /**
         * Returns an ArrayList of error reports.
         * @return List of error reports.
         */
        public ArrayList<String> getErrors() {
                return (ArrayList<String>) errors; 
        }
        
        /**
         * Checks bean for error report.
         * @return true True if bean has error, false if no errors. 
         */
        public boolean hasError() {
                return !errors.isEmpty();
        }
        
        /**
         * Clears all error reports from bean. 
         */
        public void clearErrors() {
                errors = new ArrayList<String>(0);
        }

}