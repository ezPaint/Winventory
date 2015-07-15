package com.simoncomputing.app.winventory.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.simoncomputing.app.winventory.domain.Event;
import com.simoncomputing.app.winventory.domain.Software;
import com.simoncomputing.app.winventory.util.DaoException;

public interface EventDao { 

    public int create( Event value ) throws DaoException;

    public int update( Event value ) throws DaoException;

    public int delete( Map<String, Object> map ) throws DaoException;

    public Event read( Map<String, Object> map ) throws DaoException;

    public List<Event> getListByCreatorId( Long key ) throws DaoException;
    public List<Event> getListByUserId( Long key ) throws DaoException;
    public List<Event> getListByHardwareId( Long key ) throws DaoException;
    public List<Event> getListBySoftwareId( Long key ) throws DaoException;
    public List<Event> getListByLocationId( Long key ) throws DaoException;

    // PROTECTED CODE -->
    public List<Event> getListByDateRange(@Param("start") Date start,
            @Param("end") Date end) throws DaoException;
}