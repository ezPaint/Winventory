package com.simoncomputing.app.winventory.dao;

import java.util.List;
import java.util.Map;

import com.simoncomputing.app.winventory.domain.TeamToUser;
import com.simoncomputing.app.winventory.util.DaoException;

public interface TeamToUserDao { 

    public int create( TeamToUser value ) throws DaoException;

    public int update( TeamToUser value ) throws DaoException;

    public int delete( Map<String, Object> map ) throws DaoException;

    public TeamToUser read( Map<String, Object> map ) throws DaoException;

    public List<TeamToUser> getListByUserId( Integer key ) throws DaoException;
    public List<TeamToUser> getListByTeamId( Integer key ) throws DaoException;

    // PROTECTED CODE -->

}