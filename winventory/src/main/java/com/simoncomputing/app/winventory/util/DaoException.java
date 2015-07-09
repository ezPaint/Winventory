package com.simoncomputing.app.winventory.util;

public class DaoException extends Exception {
    
    private static final long serialVersionUID = 1L;

    public DaoException(Throwable e) {
        super(e);
    }

    public DaoException(String msg) {
        super(msg);
    }

    public DaoException(String msg, Throwable e) {
        super(msg, e);
    }


    // PROTECTED CODE -->

}