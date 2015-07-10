package com.simoncomputing.app.winventory.dao;

import java.util.List;
import java.util.Map;

import com.simoncomputing.app.winventory.domain.EventToLocation;
import com.simoncomputing.app.winventory.util.DaoException;

public interface EventToLocationDao { 

    public int create( EventToLocation value ) throws DaoException;

    public int update( EventToLocation value ) throws DaoException;

    public int delete( Map<String, Object> map ) throws DaoException;

    public EventToLocation read( Map<String, Object> map ) throws DaoException;


    // PROTECTED CODE -->

}