package com.simoncomputing.app.winventory.dao;

import java.util.List;
import java.util.Map;

import com.simoncomputing.app.winventory.domain.EventToSoftware;
import com.simoncomputing.app.winventory.util.DaoException;

public interface EventToSoftwareDao { 

    public int create( EventToSoftware value ) throws DaoException;

    public int update( EventToSoftware value ) throws DaoException;

    public int delete( Map<String, Object> map ) throws DaoException;

    public EventToSoftware read( Map<String, Object> map ) throws DaoException;


    // PROTECTED CODE -->

}