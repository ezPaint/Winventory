package com.simoncomputing.app.winventory.dao;

import java.util.List;
import java.util.Map;

import com.simoncomputing.app.winventory.domain.Event;
import com.simoncomputing.app.winventory.util.DaoException;

public interface EventDao { 

    public int create( Event value ) throws DaoException;

    public int update( Event value ) throws DaoException;

    public int delete( Map<String, Object> map ) throws DaoException;

    public Event read( Map<String, Object> map ) throws DaoException;


    // PROTECTED CODE -->

}