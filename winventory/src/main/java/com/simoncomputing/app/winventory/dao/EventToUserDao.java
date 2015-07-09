package com.simoncomputing.app.winventory.dao;

import java.util.List;
import java.util.Map;

import com.simoncomputing.app.winventory.domain.EventToUser;
import com.simoncomputing.app.winventory.util.DaoException;

public interface EventToUserDao { 

    public int create( EventToUser value ) throws DaoException;

    public int update( EventToUser value ) throws DaoException;

    public int delete( Map<String, Object> map ) throws DaoException;

    public EventToUser read( Map<String, Object> map ) throws DaoException;

    public List<EventToUser> getListByEventId( Long key ) throws DaoException;
    public List<EventToUser> getListByUserId( Long key ) throws DaoException;

    // PROTECTED CODE -->

}