package com.simoncomputing.app.winventory.util;

public class BoException extends Exception {
    private static final long serialVersionUID = 1L;

    public BoException( Throwable e ) {
        super( e );
    }

    public BoException( String msg ) {
        super( msg );
    }

    public BoException( String msg, Throwable e ) {
        super( msg, e );
    }

    // PROTECTED CODE -->

}