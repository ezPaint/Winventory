package com.simoncomputing.app.winventory.dao;

import java.util.Map;

import com.simoncomputing.app.winventory.domain.AccessToken;
import com.simoncomputing.app.winventory.util.DaoException;

public interface AccessTokenDao { 

    public int create( AccessToken value ) throws DaoException;

    public int update( AccessToken value ) throws DaoException;

    public int delete( Map<String, Object> map ) throws DaoException;

    public AccessToken read( Map<String, Object> map ) throws DaoException;


    // PROTECTED CODE -->

}