package com.simoncomputing.app.winventory.dao;

import java.util.Map;

import com.simoncomputing.app.winventory.domain.GoogleClient;
import com.simoncomputing.app.winventory.util.DaoException;

public interface GoogleClientDao { 

    public int create( GoogleClient value ) throws DaoException;

    public int update( GoogleClient value ) throws DaoException;

    public int delete( Map<String, Object> map ) throws DaoException;

    public GoogleClient read( Map<String, Object> map ) throws DaoException;


    // PROTECTED CODE -->

}