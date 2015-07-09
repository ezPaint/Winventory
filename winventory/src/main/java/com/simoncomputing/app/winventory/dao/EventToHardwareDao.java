package com.simoncomputing.app.winventory.dao;

import java.util.List;
import java.util.Map;

import com.simoncomputing.app.winventory.domain.EventToHardware;
import com.simoncomputing.app.winventory.util.DaoException;

public interface EventToHardwareDao { 

    public int create( EventToHardware value ) throws DaoException;

    public int update( EventToHardware value ) throws DaoException;

    public int delete( Map<String, Object> map ) throws DaoException;

    public EventToHardware read( Map<String, Object> map ) throws DaoException;

    public List<EventToHardware> getListByEventId( Long key ) throws DaoException;
    public List<EventToHardware> getListByHardwareId( Long key ) throws DaoException;

    // PROTECTED CODE -->

}