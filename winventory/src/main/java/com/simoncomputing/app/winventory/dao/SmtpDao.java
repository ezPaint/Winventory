package com.simoncomputing.app.winventory.dao;

import java.util.Map;

import com.simoncomputing.app.winventory.domain.Smtp;
import com.simoncomputing.app.winventory.util.DaoException;

public interface SmtpDao { 

    public int create( Smtp value ) throws DaoException;

    public int update( Smtp value ) throws DaoException;

    public int delete( Map<String, Object> map ) throws DaoException;

    public Smtp read( Map<String, Object> map ) throws DaoException;


    // PROTECTED CODE -->

}